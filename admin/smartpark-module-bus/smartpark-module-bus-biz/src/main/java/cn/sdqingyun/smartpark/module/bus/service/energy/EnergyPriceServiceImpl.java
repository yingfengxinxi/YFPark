package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPriceRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillNoticeTemplateSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildRespVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill.BillStreamDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyRoomPriceDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyUnitPriceDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillNoticeTemplateDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyPriceMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyRoomPriceMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyUnitPriceMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.gson.Gson;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.error;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPricePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyPriceSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyPriceDO;

import java.util.List;

/**
 * 自定义价格标准 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyPriceServiceImpl implements EnergyPriceService {

    @Resource
    private EnergyPriceMapper Mapper;

    @Resource
    private VillageMapper villageMapper;

    @Resource
    private BuildMapper buildMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private EnergyRoomPriceMapper energyRoomPriceMapper;

    @Resource
    private EnergyUnitPriceMapper energyUnitPriceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(EnergyPriceSaveReqVO createReqVO) {
        isCheckNameRoomId(createReqVO);
        // 插入
        EnergyPriceDO energyPriceDO = BeanUtils.toBean(createReqVO, EnergyPriceDO.class);
        Mapper.insert(energyPriceDO);

        addEnergyRoomPrice(energyPriceDO);


        addEnergyUnitPrice(energyPriceDO);


        // 返回
        return energyPriceDO.getId();
    }

    private void addEnergyUnitPrice(EnergyPriceDO energyPriceDO) {
        //电存储
        if (StringUtils.equals(energyPriceDO.getType(), "6")) {
            LambdaQueryWrapperX<EnergyUnitPriceDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(EnergyUnitPriceDO::getEnergyPriceId, energyPriceDO.getId());
            energyUnitPriceMapper.delete(queryWrapperX);
            if (StringUtils.equals(energyPriceDO.getIsStagePrice(), "1")) {
                String unitPrice = energyPriceDO.getUnitPrice();
                if (StringUtils.isNotEmpty(unitPrice)) {
                    List<EnergyUnitPriceDO> energyUnitPriceList = JSONObject.parseArray(unitPrice, EnergyUnitPriceDO.class);
                    energyUnitPriceList.forEach(aa -> aa.setEnergyPriceId(energyPriceDO.getId()).setType(energyPriceDO.getType()));
                    energyUnitPriceMapper.insertBatch(energyUnitPriceList);
                }

            }
        }
    }

    private void addEnergyRoomPrice(EnergyPriceDO energyPriceDO) {
        String roomIds = energyPriceDO.getRoomIds();
        LambdaQueryWrapperX<EnergyRoomPriceDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyRoomPriceDO::getEnergyPriceId, energyPriceDO.getId());
        energyRoomPriceMapper.delete(queryWrapperX);
        List<EnergyRoomPriceDO> energyRoomPriceList = Lists.newArrayList();
        if (StringUtils.isNotEmpty(roomIds)) {
            List<String> roomIdList = List.of(roomIds.split(","));
            roomIdList.forEach(roomId -> {
                EnergyRoomPriceDO energyRoomPriceDO = new EnergyRoomPriceDO();
                energyRoomPriceDO.setEnergyPriceId(energyPriceDO.getId());
                energyRoomPriceDO.setRoomId(Long.valueOf(roomId));
                energyRoomPriceDO.setType(energyPriceDO.getType());
                energyRoomPriceDO.setStatus("1");
                energyRoomPriceList.add(energyRoomPriceDO);
            });
        }
        if (CollectionUtils.isNotEmpty(energyRoomPriceList)) {
            energyRoomPriceMapper.insertBatch(energyRoomPriceList);
        }
    }


    private void isCheckNameRoomId(EnergyPriceSaveReqVO energyPriceSaveReqVO) {
        //楼宇配置规则后不允许再次配置
        String[] roomIds = energyPriceSaveReqVO.getRoomIds().split(",");
        for (String roomId : roomIds) {
            LambdaQueryWrapperX<EnergyPriceDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.apply("REGEXP_LIKE(room_ids, '(^|,)" + roomId + "($|,)')");
            queryWrapperX.eq(EnergyPriceDO::getType, energyPriceSaveReqVO.getType());
            if (energyPriceSaveReqVO.getId() != null) {
                queryWrapperX.apply("id!=" + energyPriceSaveReqVO.getId() + "");
            }
            int size = Mapper.selectList(queryWrapperX).size();
            if (size >= 1) {

                RoomDO roomDO = roomMapper.selectById(Long.valueOf(roomId));
                String roomName = "";
                if (roomDO != null) {
                    roomName = roomDO.getRoomName();
                }
                throw new ServiceException(406, "当前房号【" + roomName + "】已存在使用范围中,请勿重复配置");
            }
        }

        //模板名称不允许重复
        LambdaQueryWrapperX<EnergyPriceDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyPriceDO::getName, energyPriceSaveReqVO.getName());
        queryWrapperX.eq(EnergyPriceDO::getType, energyPriceSaveReqVO.getType());
        if (energyPriceSaveReqVO.getId() != null) {
            queryWrapperX.apply("id!=" + energyPriceSaveReqVO.getId() + "");
        }
        int size = Mapper.selectList(queryWrapperX).size();
        if (size >= 1) {
            throw new ServiceException(406, "价格标准名称已存在，请勿重复操作");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(EnergyPriceSaveReqVO updateReqVO) {
        isCheckNameRoomId(updateReqVO);
        // 更新
        EnergyPriceDO updateObj = BeanUtils.toBean(updateReqVO, EnergyPriceDO.class);
        Mapper.updateById(updateObj);

        addEnergyRoomPrice(updateObj);

        addEnergyUnitPrice(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {

        LambdaQueryWrapperX<EnergyUnitPriceDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyUnitPriceDO::getEnergyPriceId, id);
        energyUnitPriceMapper.delete(queryWrapperX);

        LambdaQueryWrapperX<EnergyRoomPriceDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(EnergyRoomPriceDO::getEnergyPriceId, id);
        energyRoomPriceMapper.delete(queryWrapperX1);
        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public EnergyPriceDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyPriceRespVO> getPage(EnergyPricePageReqVO pageReqVO) {
        LambdaQueryWrapperX<EnergyPriceDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyPriceDO::getType, pageReqVO.getType());
        PageResult<EnergyPriceDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<EnergyPriceRespVO> respVOPageResult = BeanUtils.toBean(pageResult, EnergyPriceRespVO.class);
        List<EnergyPriceRespVO> list = respVOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(energyPriceRespVO -> {
                VillageDO villageDO = villageMapper.selectById(energyPriceRespVO.getVillageId());
                if (villageDO != null) {
                    energyPriceRespVO.setVillageName(villageDO.getName());
                }

                BuildDO buildDO = buildMapper.selectById(energyPriceRespVO.getBuildId());
                if (villageDO != null) {
                    energyPriceRespVO.setBuildName(buildDO.getBuildName());
                }

                getRoomName(energyPriceRespVO);
            });
        }
        return respVOPageResult;

    }

    private void getRoomName(EnergyPriceRespVO energyPriceRespVO) {
        String roomIds = energyPriceRespVO.getRoomIds();
        if (StringUtils.isNotEmpty(roomIds)) {
            StringBuilder sb = new StringBuilder();
            String[] split = roomIds.split(",");
            for (String roomId : split) {
                RoomDO roomDO = roomMapper.selectById(roomId);
                if (roomDO != null) {
                    String roomName = roomDO.getRoomName();
                    String lc = roomName.substring(0, roomName.length() - 2);
                    sb.append(lc + "-" + roomName).append(",");
                }
            }

            String roomName = sb.toString();
            if (StringUtils.isNotEmpty(roomName)) {
                roomName = roomName.substring(0, roomName.length() - 1);
                energyPriceRespVO.setRoomName(roomName);
            }
        }
    }

}