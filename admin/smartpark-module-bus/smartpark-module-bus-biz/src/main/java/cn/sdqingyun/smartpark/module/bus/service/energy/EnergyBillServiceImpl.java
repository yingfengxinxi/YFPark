package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.hutool.json.JSONObject;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyBillPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyBillSaveReqVO;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 自定义账单 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyBillServiceImpl implements EnergyBillService {

    @Resource
    private EnergyBillMapper Mapper;

    @Resource
    private EnergyMapper energyMapper;

    @Resource
    private EnergyRecordMapper energyRecordMapper;

    @Resource
    private EnergyBindMapper energyBindMapper;

    @Resource
    private EnergyPublicRecordMapper energyPublicRecordMapper;

    @Resource
    private EnergyPriceMapper energyPriceMapper;

    @Resource
    private EnergyRoomPriceMapper energyRoomPriceMapper;

    @Resource
    private RoomMapper roomMapper;

    @Override
    public Long create(EnergyBillSaveReqVO createReqVO) {
        // 插入
        EnergyBillDO energyBillDO = BeanUtils.toBean(createReqVO, EnergyBillDO.class);
        Mapper.insert(energyBillDO);
        // 返回
        return energyBillDO.getId();
    }

    @Override
    public void update(EnergyBillSaveReqVO updateReqVO) {

        // 更新
        EnergyBillDO updateObj = BeanUtils.toBean(updateReqVO, EnergyBillDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public EnergyBillDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyBillDO> getPage(EnergyBillPageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
        return Mapper.selectPage(pageReqVO, queryWrapperX);
    }

    /**
     * @param energyIds
     */
    @Override
    public JSONObject check(String energyIds) {

        LambdaQueryWrapperX<EnergyDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.in(EnergyDO::getId, Arrays.asList(energyIds.split(",")));
        queryWrapperX1.eq(EnergyDO::getStatus, "1");
        List<EnergyDO> energyDOList = energyMapper.selectList(queryWrapperX1);
        if (CollectionUtils.isEmpty(energyDOList)) {
            throw new ServiceException(406, "表具数据不存在或状态不正常");
        }
        final Integer[] recordTolCount = {0};
        final Integer[] uploadImgCount = {0};
        final Integer[] notConfirmCount = {0};
        //第一个弹框使用
        List<String> listRowArr = Lists.newArrayList();
        //
        energyDOList.forEach(energyDO -> {
            String purpose = energyDO.getPurpose();
            String publicType = energyDO.getPublicType();
            if (StringUtils.equals(purpose, "2")) {
                if (!StringUtils.equals(publicType, "5")) {
                    //总表
                    throw new ServiceException(406, "表名称【" + energyDO.getName() + "】为总表,公摊方式为【" + DictFrameworkUtils.getDictDataLabel("PUBLIC_TYPE", publicType) + "】不能直接生成账单");
                }
            }

            if (StringUtils.equals(purpose, "3")) {
                if (!StringUtils.equals(publicType, "5")) {
                    //公摊表
                    throw new ServiceException(406, "表名称【" + energyDO.getName() + "】为公摊表,公摊方式为【" + DictFrameworkUtils.getDictDataLabel("PUBLIC_TYPE", publicType) + "】不能直接生成账单");
                }
            }


            LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(EnergyRecordDO::getEnergyId, energyDO.getId());
            queryWrapperX.eq(EnergyRecordDO::getStatus, "1");
            List<EnergyRecordDO> energyRecordDOList = energyRecordMapper.selectList(queryWrapperX);
            recordTolCount[0] = recordTolCount[0] + energyRecordDOList.size();
            uploadImgCount[0] = uploadImgCount[0] + energyRecordDOList.stream().filter(energyRecordDO -> StringUtils.isNotEmpty(energyRecordDO.getImage())).collect(Collectors.toList()).size();
            notConfirmCount[0] = notConfirmCount[0] + energyRecordDOList.stream().filter(energyRecordDO -> StringUtils.isNotEmpty(energyRecordDO.getImage()) && StringUtils.equals(energyRecordDO.getIsConfirm(), "0")).collect(Collectors.toList()).size();

            //// 一键生成账单时，校验所选账单的分摊记录里是否有未匹配的分摊记录，如有则如下展示，如无则跳转批量生成账单弹层
            //            // 分表存在待生成账单

            //查询账单待生成记录
            LambdaQueryWrapperX<EnergyBillDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
            queryWrapperX2.eq(EnergyBillDO::getEnergyId, energyDO.getId());
            queryWrapperX2.eq(EnergyBillDO::getStatus, "2");
            List<EnergyBillDO> energyBillDOList = Mapper.selectList(queryWrapperX2);
            if (CollectionUtils.isNotEmpty(energyBillDOList)) {
                energyBillDOList.forEach(energyBillDO -> {
                    Date startTime = energyBillDO.getStartTime();
                    Date endTime = energyBillDO.getEndTime();

                    LambdaQueryWrapperX<EnergyBindDO> queryWrapperX3 = new LambdaQueryWrapperX<>();
                    queryWrapperX3.eq(EnergyBindDO::getMeterType, energyDO.getType());
                    queryWrapperX3.in(EnergyBindDO::getPurposeType, Arrays.asList("2,3".split(",")));
                    queryWrapperX3.in(EnergyBindDO::getEnergyId, energyDO.getId());
                    List<Long> parentEnergyIds = energyBindMapper.selectList(queryWrapperX3).stream().map(energyBindDO -> energyBindDO.getParentEnergyId()).collect(Collectors.toList());
                    parentEnergyIds.forEach(parentEnergyId -> {
                        LambdaQueryWrapperX<EnergyPublicRecordDO> queryWrapperX4 = new LambdaQueryWrapperX<>();
                        queryWrapperX4.eq(EnergyPublicRecordDO::getParentEnergyId, parentEnergyId);
                        queryWrapperX4.eq(EnergyPublicRecordDO::getType, "1");
                        queryWrapperX4.eq(EnergyPublicRecordDO::getStartTime, startTime);
                        queryWrapperX4.eq(EnergyPublicRecordDO::getEndTime, endTime);
                        queryWrapperX4.orderByDesc(EnergyPublicRecordDO::getCreateTime);

                        List<EnergyPublicRecordDO> energyPublicRecordDOS = energyPublicRecordMapper.selectList(queryWrapperX4);
                        if (CollectionUtils.isEmpty(energyPublicRecordDOS)) {
                            //// 分摊记录未匹配 弹窗展示
                            //                    if (empty($energyPublicRecordData)) {
                            //                        $listRowArr[] = $name;
                            //                    }
                            listRowArr.add(energyDO.getName());
                            //EnergyPublicRecordDO energyPublicRecordDO = energyPublicRecordDOS.get(0);
                        }
                    });
                });
            }
        });

        //第一个弹框
        if (listRowArr.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 2001);
            jsonObject.put("infoStart", "当前所选分表中含有未分配分摊记录的分表有:");
            jsonObject.put("'msg'", listRowArr);
            jsonObject.put("infoEnd", "继续生成的话无法产生分摊金额是否仍要继续生成?");
            jsonObject.put("errorLevel", "warning");
            jsonObject.put("flag", "1");
            jsonObject.put("hasImage", false);
            return jsonObject;
        }

        //第二个弹框
        if (notConfirmCount[0] > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 2001);
            jsonObject.put("'msg'", "本次操作共有" + recordTolCount[0] + "条抄表记录, 共上传" + uploadImgCount[0] + "张照片, 有" + notConfirmCount[0] + "张还未确认，是否继续生成？");
            jsonObject.put("errorLevel", "warning");
            jsonObject.put("flag", "1");
            jsonObject.put("recordTolCount", recordTolCount[0]);
            jsonObject.put("uploadImgCount", uploadImgCount[0]);
            jsonObject.put("notConfirmCount", notConfirmCount[0]);
            return jsonObject;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 200);
        jsonObject.put("ids", energyIds);

        return jsonObject;
    }

    @Override
    public JSONObject preview(String energyIds, String receivablePaymentTime) {

        LambdaQueryWrapperX<EnergyDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.in(EnergyDO::getId, Arrays.asList(energyIds.split(",")));
        queryWrapperX.eq(EnergyDO::getStatus, "1");
        List<EnergyDO> energyList = energyMapper.selectList(queryWrapperX);
        //未绑定租客数量
        Integer notBindOwner = 0;
        //配置价格数量
        final Integer[] setNum = {0};
        energyList.forEach(energyDO -> {

            LambdaQueryWrapperX<EnergyBillDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.eq(EnergyBillDO::getEnergyId, energyDO.getId());
            queryWrapperX1.eq(EnergyBillDO::getStatus, "2");
            List<EnergyBillDO> energyBillList = Mapper.selectList(queryWrapperX1);
            energyBillList.forEach(energyBillDO -> {
                // 已选择2项/共2项配置了自定义价格标准
                // 根据房间价格表读取阶梯价格
                getEnergyPriceNumber(energyDO, setNum);
                AtomicReference<String> roomName = new AtomicReference<>("");
                List<String> energyRoomIds = List.of(energyDO.getRoomIds().split(","));
                List<String> energyBillRoomIds = List.of(energyBillDO.getRoomIds().split(","));
                energyRoomIds.forEach(energyRoomId -> {
                    energyBillRoomIds.forEach(energyBillRoomId -> {
                        if (StringUtils.equals(energyRoomId, energyBillRoomId)) {
                            RoomDO roomDO = roomMapper.selectById(energyRoomId);
                            roomName.set(roomDO.getRoomName());
                        }
                    });
                });

               // energyBillDO
            });
        });
        return null;
    }

    private void getEnergyPriceNumber(EnergyDO energyDO, Integer[] setNum) {
        LambdaQueryWrapperX<EnergyRoomPriceDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
        queryWrapperX2.in(EnergyRoomPriceDO::getRoomId, Arrays.asList(energyDO.getRoomIds().split(",")));
        List<EnergyRoomPriceDO> energyRoomPriceList = energyRoomPriceMapper.selectList(queryWrapperX2);
        if (CollectionUtils.isNotEmpty(energyRoomPriceList)) {
            LambdaQueryWrapperX<EnergyPriceDO> queryWrapperX3 = new LambdaQueryWrapperX<>();
            queryWrapperX3.eq(EnergyPriceDO::getType, energyDO.getType());
            queryWrapperX3.eq(EnergyPriceDO::getStatus, "1");
            queryWrapperX3.in(EnergyPriceDO::getId, energyRoomPriceList.stream().map(energyRoomPriceDO -> energyRoomPriceDO.getEnergyPriceId()).collect(Collectors.toList()));
            queryWrapperX3.apply("DATE_FORMAT(effect_date,'%Y-%m-%d') <= DATE_FORMAT(NOW(),'%Y-%m-%d') ");
            queryWrapperX3.orderByDesc(EnergyPriceDO::getEffectDate);
            List<EnergyPriceDO> energyPriceList = energyPriceMapper.selectList(queryWrapperX3);
            if (CollectionUtils.isNotEmpty(energyPriceList)) {
                //EnergyPriceDO energyPriceDO = energyPriceList.get(0);
                setNum[0] = setNum[0] + 1;
            }
        }
    }

}