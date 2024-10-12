package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.hutool.json.JSONObject;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.web.core.util.WebFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 自定义抄表记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EnergyRecordServiceImpl implements EnergyRecordService {

    @Resource
    private EnergyRecordMapper Mapper;

    @Resource
    private EnergyTaskRecordMapper energyTaskRecordMapper;

    @Resource
    private EnergyMapper energyMapper;

    @Resource
    private EnergyBillMapper energyBillMapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private EnergyTypeMapper energyTypeMapper;

    @Resource
    private EnergyTaskMapper energyTaskMapper;

    @Resource
    private EnergyNotCompleteTaskListService energyNotCompleteTaskListService;

    @Resource
    private EnergyPlanMapper energyPlanMapper;

    @Resource
    private EnergyBindMapper energyBindMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(EnergyRecordSaveReqVO createReqVO) {

        EnergyDO energyDO = energyMapper.selectById(createReqVO.getEnergyId());
        if (StringUtils.equals(energyDO.getPaymentType(), "1")) {
            throw new ServiceException(406, "付费类型未预付费、不支持手动抄表");
        }
        //检测分表用量之和是否大于总表用量
        String msg = checkTotalPartUse(
                energyDO.getId(),
                energyDO.getVillageId(),
                energyDO.getBuildId(),
                energyDO.getPurpose(),
                createReqVO);
        if (StringUtils.isNotEmpty(msg)) {
            throw new ServiceException(406, msg);
        }
        // 插入
        EnergyRecordDO energyRecordDO = BeanUtils.toBean(createReqVO, EnergyRecordDO.class);
        energyRecordDO.setType(energyDO.getPurpose());
        energyRecordDO.setMeterType(energyDO.getType());
        energyRecordDO.setVillageId(energyDO.getVillageId());
        energyRecordDO.setBuildId(energyDO.getBuildId());
        energyRecordDO.setEnergyPriceId(0L);
        Long userId = WebFrameworkUtils.getLoginUserId();
        energyRecordDO.setLeadUid(userId);
        energyRecordDO.setStatus("1");
        energyRecordDO.setIsConfirm("0");
        Mapper.insert(energyRecordDO);

        meterReading(energyDO, energyRecordDO.getLastTime(), energyRecordDO.getThisTime());

        if (createReqVO.getPlanId() != null) {

            EnergyTaskRecordDO energyTaskRecord = new EnergyTaskRecordDO();
            energyTaskRecord.setPlanId(createReqVO.getPlanId());
            energyTaskRecord.setTaskId(createReqVO.getEnergyTaskId());
            energyTaskRecord.setEnergyId(energyRecordDO.getEnergyId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lastUse", energyRecordDO.getLastUse());
            jsonObject.put("thisUse", energyRecordDO.getThisUse());
            jsonObject.put("lastTime", energyRecordDO.getLastTime());
            jsonObject.put("recordId", energyRecordDO.getId());
            jsonObject.put("thisTime", energyRecordDO.getThisTime());
            jsonObject.put("thisImage", energyRecordDO.getImage());
            jsonObject.put("lastNumber", energyRecordDO.getLastNumber());
            jsonObject.put("thisNumber", energyRecordDO.getThisNumber());
            energyTaskRecord.setExtraData(new Gson().toJson(jsonObject));
            energyTaskRecordMapper.insert(energyTaskRecord);
        }
        // 返回
        return energyRecordDO.getId();
    }

    /**
     * 计算抄表后续.
     */
    private void meterReading(EnergyDO energyDO, Date lastTime, Date thisTime) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        List<Long> parentEnergyIds = Lists.newArrayList();
        if (StringUtils.equals(energyDO.getPurpose(), "1")) {
            //分表
            LambdaQueryWrapperX<EnergyBindDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(EnergyBindDO::getEnergyId, energyDO.getId());
            queryWrapperX.eq(EnergyBindDO::getVillageId, energyDO.getVillageId());
            queryWrapperX.eq(EnergyBindDO::getBuildId, energyDO.getBuildId());
            List<EnergyBindDO> energyBindList = energyBindMapper.selectList(queryWrapperX);
            if (CollectionUtils.isEmpty(energyBindList)) {
                LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(EnergyRecordDO::getVillageId, energyDO.getVillageId());
                queryWrapperX1.eq(EnergyRecordDO::getBuildId, energyDO.getBuildId());
                queryWrapperX1.eq(EnergyRecordDO::getEnergyId, energyDO.getId());
                queryWrapperX1.eq(EnergyRecordDO::getLastTime, lastTime);
                queryWrapperX1.eq(EnergyRecordDO::getThisTime, thisTime);
                List<EnergyRecordDO> energyRecordDOList = Mapper.selectList(queryWrapperX1);
                if (CollectionUtils.isNotEmpty(energyRecordDOList)) {
                    BigDecimal thisUse = energyRecordDOList.stream().map(energyRecordDO -> energyRecordDO.getThisUse()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    if (thisUse == null || thisUse.compareTo(BigDecimal.ZERO) == 0) {
                        //删除账单
                        LambdaQueryWrapperX<EnergyBillDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
                        queryWrapperX2.eq(EnergyBillDO::getVillageId, energyDO.getVillageId());
                        queryWrapperX2.eq(EnergyBillDO::getBuildId, energyDO.getBuildId());
                        queryWrapperX2.eq(EnergyBillDO::getType, energyDO.getType());
                        queryWrapperX2.eq(EnergyBillDO::getStatus, "2");
                        queryWrapperX2.apply("DATE_FORMAT(start_time,'%Y-%m-%d') = DATE_FORMAT(" + sim.format(lastTime) + ",'%Y-%m-%d') and DATE_FORMAT(end_time,'%Y-%m-%d')=DATE_FORMAT(" + sim.format(thisTime) + ",'%Y-%m-%d') ");
                        energyBillMapper.delete(queryWrapperX2);
                    } else {
                        //分表抄录初始化账单
                        initEnergyBill(energyDO, thisUse, lastTime, thisTime);
                    }
                }
            } else {
                parentEnergyIds.addAll(energyBindList.stream().distinct().map(energyBindDO -> energyBindDO.getParentEnergyId()).collect(Collectors.toList()));
            }

        } else {
            LambdaQueryWrapperX<EnergyBindDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(EnergyBindDO::getParentEnergyId, energyDO.getId());
            List<EnergyBindDO> energyBindList = energyBindMapper.selectList(queryWrapperX);
            LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.eq(EnergyRecordDO::getVillageId, energyDO.getVillageId());
            queryWrapperX1.eq(EnergyRecordDO::getBuildId, energyDO.getBuildId());
            queryWrapperX1.eq(EnergyRecordDO::getEnergyId, energyDO.getId());
            queryWrapperX1.eq(EnergyRecordDO::getLastTime, lastTime);
            queryWrapperX1.eq(EnergyRecordDO::getThisTime, thisTime);
            List<EnergyRecordDO> energyRecordDOList = Mapper.selectList(queryWrapperX1);
            BigDecimal thisUse = new BigDecimal("0.00");
            if (CollectionUtils.isNotEmpty(energyRecordDOList)) {
                thisUse = energyRecordDOList.stream().map(energyRecordDO -> energyRecordDO.getThisUse()).reduce(BigDecimal.ZERO, BigDecimal::add);
            }

            if (StringUtils.equals(energyDO.getPublicType(), "5")) {
                //按固定比例分配--公摊表\总表  没有关联分表
                if (StringUtils.equals(energyDO.getPurpose(), "3") ||
                        StringUtils.equals(energyDO.getPurpose(), "2")) {
                    if (CollectionUtils.isEmpty(energyRecordDOList)) {
                        if (thisUse.compareTo(BigDecimal.ZERO) > 0) {
                            //生成公摊表的分摊记录
                            generatePublicRecord(energyDO.getId(), thisUse, lastTime, thisTime, thisUse, Lists.newArrayList());
                        }
                    }
                }

                //按照固定比例分摊且关联了下级表
                if (StringUtils.equals(energyDO.getPurpose(), "2")) {
                    if (CollectionUtils.isNotEmpty(energyRecordDOList)) {
                        List<Long> energyIds = energyBindList.stream().distinct().map(energyBindDO -> energyBindDO.getEnergyId()).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(energyIds)) {
                            LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
                            queryWrapperX2.in(EnergyRecordDO::getEnergyId, energyIds);
                            queryWrapperX2.eq(EnergyRecordDO::getLastTime, lastTime);
                            queryWrapperX2.eq(EnergyRecordDO::getThisTime, thisTime);
                            List<EnergyRecordDO> energyRecordDOList1 = Mapper.selectList(queryWrapperX2);
                            BigDecimal thisUseNumber = new BigDecimal("0.00");
                            if (CollectionUtils.isNotEmpty(energyRecordDOList1)) {
                                thisUseNumber = energyRecordDOList.stream().map(energyRecordDO -> energyRecordDO.getThisUse()).reduce(BigDecimal.ZERO, BigDecimal::add);
                            }
                            BigDecimal surplusUse = thisUse.subtract(thisUseNumber);
                            if (surplusUse.compareTo(BigDecimal.ZERO) > 0) {
                                generatePublicRecord(energyDO.getId(), surplusUse, lastTime, thisTime, thisUse, energyIds);
                            }
                        }

                    }
                }
            }

            LambdaQueryWrapperX<EnergyBindDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
            queryWrapperX2.eq(EnergyBindDO::getParentEnergyId, energyDO.getId());
            queryWrapperX2.eq(EnergyBindDO::getVillageId, energyDO.getVillageId());
            queryWrapperX2.eq(EnergyBindDO::getBuildId, energyDO.getBuildId());
            List<EnergyBindDO> energyBindList1 = energyBindMapper.selectList(queryWrapperX2);
            if (CollectionUtils.isNotEmpty(energyBindList1)) {
                List<Long> partEnergyIds = energyBindList1.stream().distinct().map(energyBindDO -> energyBindDO.getEnergyId()).collect(Collectors.toList());
                LambdaQueryWrapperX<EnergyBindDO> queryWrapperX3 = new LambdaQueryWrapperX<>();
                queryWrapperX3.eq(EnergyBindDO::getEnergyId, partEnergyIds);
                queryWrapperX3.eq(EnergyBindDO::getVillageId, energyDO.getVillageId());
                queryWrapperX3.eq(EnergyBindDO::getBuildId, energyDO.getBuildId());
                List<EnergyBindDO> energyBindList2 = energyBindMapper.selectList(queryWrapperX3);
                if (CollectionUtils.isNotEmpty(energyBindList2)) {
                    parentEnergyIds.addAll(energyBindList2.stream().distinct().map(energyBindDO -> energyBindDO.getEnergyId()).collect(Collectors.toList()));
                }
            }
        }


        if (CollectionUtils.isNotEmpty(parentEnergyIds)) {
            parentEnergyIds = parentEnergyIds.stream().distinct().collect(Collectors.toList());
            // 找出所有总表/公摊表下级分表
            LambdaQueryWrapperX<EnergyBindDO> queryWrapperX3 = new LambdaQueryWrapperX<>();
            queryWrapperX3.eq(EnergyBindDO::getVillageId, energyDO.getVillageId());
            queryWrapperX3.eq(EnergyBindDO::getBuildId, energyDO.getBuildId());
            queryWrapperX3.eq(EnergyBindDO::getParentEnergyId, parentEnergyIds);
            List<EnergyBindDO> energyBindList2 = energyBindMapper.selectList(queryWrapperX3);
            if (CollectionUtils.isNotEmpty(energyBindList2)) {
                List<Long> totalPartEnergyIds = energyBindList2.stream().distinct().map(energyBindDO -> energyBindDO.getEnergyId()).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(totalPartEnergyIds)) {
                    totalPartEnergyIds.forEach(parentEnergyId -> {
                        // 清空本期所有下级分表账单公摊用量、金额
                        LambdaQueryWrapperX<EnergyBillDO> queryWrapperX = new LambdaQueryWrapperX<>();
                        queryWrapperX.eq(EnergyBillDO::getType, energyDO.getType());
                        queryWrapperX.eq(EnergyBillDO::getVillageId, energyDO.getVillageId());
                        queryWrapperX.eq(EnergyBillDO::getStatus, "2");
                        queryWrapperX.eq(EnergyBillDO::getEnergyId, parentEnergyId);
                        queryWrapperX.apply("DATE_FORMAT(start_time,'%Y-%m-%d') = DATE_FORMAT(" + sim.format(lastTime) + ",'%Y-%m-%d') and DATE_FORMAT(end_time,'%Y-%m-%d')=DATE_FORMAT(" + sim.format(thisTime) + ",'%Y-%m-%d') ");
                        List<EnergyBillDO> energyBillList = energyBillMapper.selectList(queryWrapperX);
                        if (CollectionUtils.isNotEmpty(energyBillList)) {
                            energyBillList.forEach(energyBillDO -> {
                                energyBillDO.setPartAmount(new BigDecimal("0.00"));
                                energyBillDO.setPublicUse(new BigDecimal("0.00"));
                                energyBillDO.setPublicAmount(new BigDecimal("0.00"));
                                energyBillDO.setTotalAmount(new BigDecimal("0.00"));
                                energyBillMapper.updateById(energyBillDO);
                            });
                        }
                    });

                    //初始化分表账单和校验同周期抄表
                    initBillAndCheckSameDate(parentEnergyIds, lastTime, thisTime);
                }
            }
        }
    }

    /**
     * 初始化分表账单和校验同周期抄表.
     */
    private void initBillAndCheckSameDate(List<Long> parentEnergyIds, Date lastTime, Date thisTime) {

        if (CollectionUtils.isNotEmpty(parentEnergyIds)) {
            List<Long> completeEnergyIds = Lists.newArrayList();
            parentEnergyIds.forEach(parentEnergyId -> {
                EnergyDO energyDO = energyMapper.selectById(parentEnergyId);
                // 本期内自定义总表/公摊表下级分表有没有抄表完成
                LambdaQueryWrapperX<EnergyBindDO> queryWrapperX = new LambdaQueryWrapperX<>();
                queryWrapperX.eq(EnergyBindDO::getMeterType, energyDO.getType());
                queryWrapperX.eq(EnergyBindDO::getVillageId, energyDO.getVillageId());
                queryWrapperX.eq(EnergyBindDO::getBuildId, energyDO.getBuildId());
                queryWrapperX.eq(EnergyBindDO::getParentEnergyId, energyDO.getId());
                List<EnergyBindDO> energyBindList = energyBindMapper.selectList(queryWrapperX);
                if (CollectionUtils.isNotEmpty(energyBindList)) {
                    List<Long> partEnergyIds = energyBindList.stream().distinct().map(energyBindDO -> energyBindDO.getEnergyId()).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(partEnergyIds)) {
                        partEnergyIds.forEach(energyId -> {
                            LambdaQueryWrapperX<EnergyDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                            queryWrapperX1.eq(EnergyDO::getId, energyId);
                            queryWrapperX1.eq(EnergyDO::getStatus, "1");
                            EnergyDO partEnergy = energyMapper.selectOne(queryWrapperX1);
                            if (partEnergy != null) {
                                LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
                                queryWrapperX2.eq(EnergyRecordDO::getMeterType, energyDO.getType());
                                queryWrapperX2.eq(EnergyRecordDO::getVillageId, energyDO.getVillageId());
                                queryWrapperX2.eq(EnergyRecordDO::getBuildId, energyDO.getBuildId());
                                queryWrapperX2.eq(EnergyRecordDO::getEnergyId, energyId);
                                queryWrapperX2.eq(EnergyRecordDO::getLastTime, lastTime);
                                queryWrapperX2.eq(EnergyRecordDO::getThisTime, thisTime);
                                List<EnergyRecordDO> energyRecordDOList = Mapper.selectList(queryWrapperX2);
                                if (CollectionUtils.isNotEmpty(energyRecordDOList)) {
                                    completeEnergyIds.add(energyId);
                                    if (!StringUtils.equals(partEnergy.getPublicType(), "5")) {
                                        initEnergyBill(partEnergy, new BigDecimal("0.00"), lastTime, thisTime);
                                    }

                                } else {
                                    System.out.println("本期未完成抄表的自定义分表:主表id" + energyDO.getId() + " 主表名称:" + energyDO.getName() + " 分表id:" + partEnergy.getId() + " 分表名称:" + partEnergy.getName());
                                }

                            } else {
                                System.out.println("当前公摊下分表不存在或者状态异常");
                            }
                        });
                    }
                }


                if (parentEnergyIds.size() == completeEnergyIds.size()) {
                    if (!StringUtils.equals(energyDO.getPublicType(), "1")) {
                        LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                        queryWrapperX1.in(EnergyRecordDO::getType, Arrays.asList("2,3".split(",")));
                        queryWrapperX1.eq(EnergyRecordDO::getMeterType, energyDO.getType());
                        queryWrapperX1.eq(EnergyRecordDO::getVillageId, energyDO.getVillageId());
                        queryWrapperX1.eq(EnergyRecordDO::getEnergyId, energyDO.getId());
                        queryWrapperX1.eq(EnergyRecordDO::getLastTime, lastTime);
                        queryWrapperX1.eq(EnergyRecordDO::getThisTime, thisTime);
                        queryWrapperX1.eq(EnergyRecordDO::getBuildId, energyDO.getBuildId());
                        List<EnergyRecordDO> energyRecordDOList = Mapper.selectList(queryWrapperX1);
                        if (CollectionUtils.isNotEmpty(energyRecordDOList)) {
                            BigDecimal thisUse = energyRecordDOList.stream().map(energyRecordDO -> energyRecordDO.getThisUse()).reduce(BigDecimal.ZERO, BigDecimal::add);
                            //计算分摊用量
                            initPublicUse(parentEnergyId, thisUse, lastTime, thisTime, completeEnergyIds);
                        } else {
                            System.out.println("存在本期没抄表完成的自定义总表/公摊表:主表id:" + energyDO.getId() + " 主表名称:" + energyDO.getName());
                            //stdLog()->info('存在本期没抄表完成的自定义总表/公摊表:', ['主表id' => $energy->id, '主表名称' => $energy->name]);
                        }
                    } else {
                        System.out.println("自定义总表/公摊表公摊方式为不分摊:主表id:" + energyDO.getId() + " 主表名称:" + energyDO.getName());
                        //    stdLog()->info('自定义总表/公摊表公摊方式为不分摊:', ['主表id' => $energy->id, '主表名称' => $energy->name]);
                    }
                } else {
                    System.out.println("存在本期没抄表完成的自定义分表:主表id:" + energyDO.getId() + " 主表名称:" + energyDO.getName() + " 主表下分表数组:" + parentEnergyIds.toString() + " 已完成抄表分表数组:" + completeEnergyIds.toString());
                    //stdLog()->info('存在本期没抄表完成的自定义分表:', ['主表id' => $energy->id, '主表名称' => $energy->name, '主表下分表数组' => $partEnergyIds, '已完成抄表分表数组' => $completeEnergyIds]);
                }
            });

        }
    }

    /**
     * 计算分摊用量
     *
     * @param parentEnergyId
     * @param useNum
     * @param lastTime
     * @param meterTime
     * @param completeEnergyIds
     */
    private void initPublicUse(Long parentEnergyId, BigDecimal useNum, Date lastTime, Date meterTime, List<Long> completeEnergyIds) {

    }

    /**
     * 生成公摊表的分摊记录
     *
     * @param energyId
     * @param use
     * @param startTime
     * @param endTime
     * @param thisUse
     * @param energyIds
     */
    private void generatePublicRecord(Long energyId, BigDecimal use, Date startTime, Date endTime, BigDecimal
            thisUse, List<Long> energyIds) {

    }

    /**
     * 分表抄录生成初始化账单.
     *
     * @param energyDO
     * @param thisUse
     * @param lastTime //上次抄表时间
     * @param thisTime //本次抄表时间
     */
    private void initEnergyBill(EnergyDO energyDO, BigDecimal thisUse, Date lastTime, Date thisTime) {

    }

    /**
     * 检测分表用量之和是否大于总表用量
     *
     * @param energyId
     * @param villageId
     * @param buildId
     * @param purpose
     * @param createReqVO
     * @return
     */
    private String checkTotalPartUse(
            Long energyId,
            Long villageId,
            Long buildId,
            String purpose,
            EnergyRecordSaveReqVO createReqVO
    ) {
        AtomicReference<String> msg = new AtomicReference<>("");
        AtomicReference<String> partEnergyName = new AtomicReference<>("");
        List<Long> parentEnergyIds = Lists.newArrayList();
        if (StringUtils.equals(purpose, "1")) {
            LambdaQueryWrapperX<EnergyBindDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(EnergyBindDO::getVillageId, villageId);
            queryWrapperX.eq(EnergyBindDO::getEnergyId, energyId);
            queryWrapperX.eq(EnergyBindDO::getPurposeType, "2");
            queryWrapperX.eq(EnergyBindDO::getBuildId, buildId);
            List<EnergyBindDO> energyBindList = energyBindMapper.selectList(queryWrapperX);
            if (CollectionUtils.isNotEmpty(energyBindList)) {
                parentEnergyIds = energyBindList.stream().map(energyBindDO -> energyBindDO.getParentEnergyId()).collect(Collectors.toList());
            }
        } else if (StringUtils.equals(purpose, "2")) {
            parentEnergyIds.add(energyId);
        }

        // 检测总表下分表用量之和
        if (CollectionUtils.isNotEmpty(parentEnergyIds)) {
            AtomicReference<BigDecimal> totalUse = new AtomicReference<>(new BigDecimal("0.00"));
            parentEnergyIds.forEach(parentEnergyId -> {
                EnergyDO energyDO = energyMapper.selectById(parentEnergyId);
                if (StringUtils.equals(purpose, "1")) {
                    LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX = new LambdaQueryWrapperX<>();
                    queryWrapperX.eq(EnergyRecordDO::getVillageId, villageId);
                    queryWrapperX.eq(EnergyRecordDO::getBuildId, buildId);
                    queryWrapperX.eq(EnergyRecordDO::getEnergyId, energyDO.getId());
                    queryWrapperX.eq(EnergyRecordDO::getLastTime, createReqVO.getLastTime());
                    queryWrapperX.eq(EnergyRecordDO::getThisTime, createReqVO.getThisTime());
                    List<EnergyRecordDO> energyRecordDOList = Mapper.selectList(queryWrapperX);
                    if (CollectionUtils.isNotEmpty(energyRecordDOList)) {
                        totalUse.set(energyRecordDOList.stream().map(energyRecordDO -> energyRecordDO.getThisUse()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    }
                }
                if (StringUtils.equals(purpose, "2")) {
                    totalUse.set(createReqVO.getThisUse());
                }

                LambdaQueryWrapperX<EnergyBindDO> queryWrapperX = new LambdaQueryWrapperX<>();
                queryWrapperX.eq(EnergyBindDO::getParentEnergyId, parentEnergyId);
                queryWrapperX.eq(EnergyBindDO::getVillageId, villageId);
                queryWrapperX.eq(EnergyBindDO::getBuildId, buildId);
                List<EnergyBindDO> energyBindList = energyBindMapper.selectList(queryWrapperX);
                if (CollectionUtils.isNotEmpty(energyBindList)) {
                    AtomicReference<BigDecimal> partTotalUse = new AtomicReference<>(new BigDecimal("0.00"));
                    List<Long> partEnergyIds = energyBindList.stream().map(energyBindDO -> energyBindDO.getEnergyId()).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(partEnergyIds)) {
                        partEnergyIds.forEach(parentEnergyId1 -> {
                            EnergyDO energyDO1 = energyMapper.selectById(parentEnergyId1);
                            partEnergyName.set(energyDO1.getName());
                            if (parentEnergyId1 != energyId) {
                                LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
                                queryWrapperX2.eq(EnergyRecordDO::getVillageId, villageId);
                                queryWrapperX2.eq(EnergyRecordDO::getBuildId, buildId);
                                queryWrapperX2.eq(EnergyRecordDO::getEnergyId, energyDO1.getId());
                                queryWrapperX2.eq(EnergyRecordDO::getLastTime, createReqVO.getLastTime());
                                queryWrapperX2.eq(EnergyRecordDO::getThisTime, createReqVO.getThisTime());
                                List<EnergyRecordDO> energyRecordDOList = Mapper.selectList(queryWrapperX2);
                                if (CollectionUtils.isNotEmpty(energyRecordDOList)) {
                                    partTotalUse.set(energyRecordDOList.stream().map(energyRecordDO -> energyRecordDO.getThisUse()).reduce(BigDecimal.ZERO, BigDecimal::add));
                                }
                            }
                        });
                    }

                    if (StringUtils.equals(purpose, "1")) {
                        BigDecimal sum = partTotalUse.get().add(createReqVO.getThisUse());
                        if (sum.compareTo(totalUse.get()) > 0) {
                            msg.set("分表[" + partEnergyName.get() + "]用量之和大于总表[" + energyDO.getName() + "]");
                        }
                    } else if (StringUtils.equals(purpose, "2")) {
                        if (totalUse.get().compareTo(partTotalUse.get()) < 0) {
                            msg.set("总表用量小于分表[" + partEnergyName.get() + "]用量之和");
                        }
                    }
                }
            });
        }
        return msg.get();
    }

    @Override
    public void update(EnergyRecordSaveReqVO updateReqVO) {

        // 更新
        EnergyRecordDO updateObj = BeanUtils.toBean(updateReqVO, EnergyRecordDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public EnergyRecordDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<EnergyRecordRespVO> getPage(EnergyRecordPageReqVO pageReqVO) {
        EnergyDO energyDO = energyMapper.selectById(pageReqVO.getEnergyId());
        if (energyDO.getPaymentType().equals("1")) {
            throw new ServiceException(406, "此表为预付费，不支持抄表。");
        }
        LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyRecordDO::getEnergyId, pageReqVO.getEnergyId());
        queryWrapperX.orderByDesc(EnergyRecordDO::getCreateTime);
        List<EnergyRecordDO> energyRecordDOList = Mapper.selectList(queryWrapperX);
        PageResult<EnergyRecordDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<EnergyRecordRespVO> energyRespVOPageResult = BeanUtils.toBean(pageResult, EnergyRecordRespVO.class);
        List<EnergyRecordRespVO> list = energyRespVOPageResult.getList();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(energyRespVO -> {
                String creator = energyRespVO.getCreator();
                String userName = systemUserMapper.getByOperatorIdUserName(Long.valueOf(creator));
                energyRespVO.setCreator(userName);

                //判断当前数据是否是最新的数据【最新的数据可以修改和删除】
                Long id = energyRecordDOList.get(0).getId();
                if (energyRespVO.getId() == id) {
                    if (energyDO.getStatus().equals("1")) {
                        LambdaQueryWrapperX<EnergyBillDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                        queryWrapperX1.eq(EnergyBillDO::getVillageId, energyRespVO.getVillageId());
                        queryWrapperX1.eq(EnergyBillDO::getBuildId, energyRespVO.getBuildId());
                        queryWrapperX1.eq(EnergyBillDO::getEnergyId, energyRespVO.getEnergyId());
                        queryWrapperX1.eq(EnergyBillDO::getStatus, "1");
                        queryWrapperX1.apply("DATE_FORMAT(start_time,'%Y-%m-%d') = DATE_FORMAT(" + sim.format(energyRespVO.getLastTime()) + ",'%Y-%m-%d') and DATE_FORMAT(end_time,'%Y-%m-%d')=DATE_FORMAT(" + sim.format(energyRespVO.getThisTime()) + ",'%Y-%m-%d') ");
                        List<EnergyBillDO> energyBillList = energyBillMapper.selectList(queryWrapperX1);
                        if (CollectionUtils.isEmpty(energyBillList)) {
                            energyRespVO.setIsEdit(true);
                            energyRespVO.setIsDel(true);
                        } else {
                            energyRespVO.setIsEdit(false);
                            energyRespVO.setIsDel(false);
                        }
                    } else {
                        energyRespVO.setIsEdit(false);
                        energyRespVO.setIsDel(false);
                    }
                } else {
                    energyRespVO.setIsEdit(false);
                    energyRespVO.setIsDel(false);
                }
            });
        }
        return energyRespVOPageResult;
    }

    @Override
    public EnergyRecordDO last(Long energyId) {

        LambdaQueryWrapperX<EnergyRecordDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(EnergyRecordDO::getEnergyId, energyId);
        queryWrapperX.orderByDesc(EnergyRecordDO::getCreateTime);
        List<EnergyRecordDO> energyRecordList = Mapper.selectList(queryWrapperX);
        if (CollectionUtils.isNotEmpty(energyRecordList)) {
            return energyRecordList.get(0);
        } else {
            EnergyDO energyDO = energyMapper.selectById(energyId);
            EnergyRecordDO energyRecordDO = new EnergyRecordDO();
            energyRecordDO.setLastTime(energyDO.getMeterTime());
            energyRecordDO.setLastNumber(energyDO.getOriginalReading());
            energyRecordDO.setLastUse(new BigDecimal("0.00"));
            return energyRecordDO;
        }
    }

    @Override
    public List<EnergyRecordImportExcelVO> importTemplate() {
        List<EnergyTypeDO> energyTypeList = energyTypeMapper.selectList();
        if (CollectionUtils.isEmpty(energyTypeList)) {
            throw new ServiceException(406, "表类型不存在，请添加表类型后下载模板!");
        }
        StringBuilder sb = new StringBuilder();
        energyTypeList.forEach(energyTypeDO -> {
            sb.append(energyTypeDO.getName()).append("/");
        });
        String typeName = sb.toString();
        if (StringUtils.isNotEmpty(typeName)) {
            typeName = typeName.substring(0, typeName.length() - 1);
        }
        // 手动创建导出 demo
        return Arrays.asList(
                EnergyRecordImportExcelVO.builder()
                        .name("表名称（必填）")
                        .number("表具编号（必填）")
                        .type(typeName + "（必填）")
                        .thisTime("格式yyyy-mm-dd")
                        .thisNumber("填入读数数字")
                        .importResult("非填项，如上传失败请下载反馈文档，该列会予以问题说明").build()
        );
    }

    /**
     * @param list
     * @param response
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importExcel(List<EnergyRecordImportExcelVO> list, HttpServletResponse response) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        list.remove(0);
        AtomicReference<Boolean> flag = new AtomicReference<>(true);
        list.forEach(energyRecordImportExcelVO -> {
            StringBuilder builder = new StringBuilder();

            if (StringUtils.isEmpty(energyRecordImportExcelVO.getName())) {
                flag.set(false);
                builder.append("表名称不能为空/");
                throw new ServiceException(406, builder.toString());
            }

            if (StringUtils.isEmpty(energyRecordImportExcelVO.getNumber())) {
                flag.set(false);
                builder.append("表具编号不能为空/");
                throw new ServiceException(406, builder.toString());
            }

            if (StringUtils.isEmpty(energyRecordImportExcelVO.getType())) {
                flag.set(false);
                builder.append("表种类不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            LambdaQueryWrapperX<EnergyTypeDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(EnergyTypeDO::getName, energyRecordImportExcelVO.getType());
            EnergyTypeDO energyTypeDO = energyTypeMapper.selectOne(queryWrapperX);
            if (energyTypeDO == null) {
                flag.set(false);
                builder.append("表种类名称输入错误，请核对后重新导入/");
                throw new ServiceException(406, builder.toString());
            }

            LambdaQueryWrapperX<EnergyDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.eq(EnergyDO::getName, energyRecordImportExcelVO.getName());
            queryWrapperX1.eq(EnergyDO::getNumber, energyRecordImportExcelVO.getNumber());
            queryWrapperX1.eq(EnergyDO::getType, energyTypeDO.getId());
            EnergyDO energyDO = energyMapper.selectOne(queryWrapperX1);

            if (energyDO == null) {
                flag.set(false);
                builder.append(energyRecordImportExcelVO.getType() + "下查无此表编号或表名称，请核对后重新导入/");
                throw new ServiceException(406, builder.toString());
            }
            if (energyDO.getPaymentType().equals("1")) {
                flag.set(false);
                builder.append("预付费类型不支持手动抄表/");
                throw new ServiceException(406, builder.toString());
            }
            if (StringUtils.isEmpty(energyRecordImportExcelVO.getThisTime())) {
                flag.set(false);
                builder.append("本次抄表时间不能为空/");
                throw new ServiceException(406, builder.toString());
            }

            if (!energyRecordImportExcelVO.getThisTime().contains("-")) {
                flag.set(false);
                builder.append("本次抄表时间格式不正确/");
                throw new ServiceException(406, builder.toString());
            }

            if (StringUtils.isEmpty(energyRecordImportExcelVO.getThisNumber())) {
                flag.set(false);
                builder.append("本次读数不能为空/");
                throw new ServiceException(406, builder.toString());
            }
            //校验是否可以抄表
            LambdaQueryWrapperX<EnergyPlanDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
            queryWrapperX2.eq(EnergyPlanDO::getVillageId, energyDO.getVillageId());
            queryWrapperX2.eq(EnergyPlanDO::getBuildId, energyDO.getBuildId());
            queryWrapperX2.apply("REGEXP_LIKE(room_ids, '(^|,)" + energyDO.getRoomIds().split(",")[0] + "($|,)')");
            EnergyPlanDO energyPlanDO = energyPlanMapper.selectOne(queryWrapperX2);

            LambdaQueryWrapperX<EnergyTaskDO> queryWrapperX3 = new LambdaQueryWrapperX<>();
            queryWrapperX3.eq(EnergyTaskDO::getPlanId, energyPlanDO.getId());
            queryWrapperX3.apply("status!='3'");
            queryWrapperX3.orderByDesc(EnergyTaskDO::getId);
            List<EnergyTaskDO> energyTaskList = energyTaskMapper.selectList(queryWrapperX3);
            if (CollectionUtils.isEmpty(energyTaskList)) {
                flag.set(false);
                builder.append("当前房间抄表任务不存在,请核对后重新导入/");
                throw new ServiceException(406, builder.toString());
            }
            EnergyTaskDO energyTaskDO = energyTaskList.get(0);
            int i = energyTaskDO.getShouldTime().compareTo(DateUtils.getDayTime());
            if (i != 0) {
                flag.set(false);
                builder.append("当前房间不符合抄表时间,抄表时间为:" + energyTaskDO.getShouldTime() + "/");
                throw new ServiceException(406, builder.toString());
            }

            List<EnergyNotCompleteTaskListVO> energyNotCompleteTaskListList = energyNotCompleteTaskListService.notCompleteTaskList(energyPlanDO.getId());
            if (CollectionUtils.isEmpty(energyNotCompleteTaskListList)) {
                flag.set(false);
                builder.append("当前房间已经抄表请勿重复导入数据/");
                throw new ServiceException(406, builder.toString());
            }

            //校验本次抄表时间是否大于上次抄表时间
            EnergyRecordDO energyRecordLast = this.last(energyDO.getId());
            try {
                //上次抄表时间
                Date lastTime = energyRecordLast.getLastTime();
                //本次抄表时间
                Date thisTime = sim.parse(energyRecordImportExcelVO.getThisTime());
                int ii = lastTime.compareTo(thisTime);
                if (ii <= 0) {
                    flag.set(false);
                    builder.append("本次抄表时间需要大于上次抄表时间,请重新输入后导入/");
                    throw new ServiceException(406, builder.toString());
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


            energyRecordImportExcelVO.setImportResult(String.valueOf(builder));
        });
        if (flag.get()) {

            list.forEach(energyRecordImportExcelVO -> {
                EnergyRecordSaveReqVO energySaveReqVO = new EnergyRecordSaveReqVO();

                LambdaQueryWrapperX<EnergyTypeDO> queryWrapperX3 = new LambdaQueryWrapperX<>();
                queryWrapperX3.eq(EnergyTypeDO::getName, energyRecordImportExcelVO.getType());
                EnergyTypeDO energyTypeDO = energyTypeMapper.selectOne(queryWrapperX3);

                LambdaQueryWrapperX<EnergyDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(EnergyDO::getName, energyRecordImportExcelVO.getName());
                queryWrapperX1.eq(EnergyDO::getNumber, energyRecordImportExcelVO.getNumber());
                queryWrapperX1.eq(EnergyDO::getType, energyTypeDO.getId());
                EnergyDO energyDO = energyMapper.selectOne(queryWrapperX1);

                energySaveReqVO.setEnergyId(energyDO.getId());


                LambdaQueryWrapperX<EnergyPlanDO> queryWrapperX = new LambdaQueryWrapperX<>();
                queryWrapperX.eq(EnergyPlanDO::getVillageId, energyDO.getVillageId());
                queryWrapperX.eq(EnergyPlanDO::getBuildId, energyDO.getBuildId());
                queryWrapperX.apply("REGEXP_LIKE(room_ids, '(^|,)" + energyDO.getRoomIds().split(",")[0] + "($|,)')");
                EnergyPlanDO energyPlanDO = energyPlanMapper.selectOne(queryWrapperX);


                LambdaQueryWrapperX<EnergyTaskDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
                queryWrapperX2.eq(EnergyTaskDO::getPlanId, energyPlanDO.getId());
                EnergyTaskDO energyTaskDO = energyTaskMapper.selectOne(queryWrapperX2);
                energySaveReqVO.setEnergyTaskId(energyTaskDO.getId());

                try {

                    EnergyRecordDO energyRecordLast = this.last(energyDO.getId());
                    energySaveReqVO.setLastTime(energyRecordLast.getLastTime());
                    energySaveReqVO.setLastNumber(energyRecordLast.getLastNumber());
                    energySaveReqVO.setLastUse(energyRecordLast.getLastUse());

                    //计算公式(本次读数-上次读数=本次用量)

                    Date thisTime = sim.parse(energyRecordImportExcelVO.getThisTime());
                    energySaveReqVO.setThisTime(thisTime);

                    energySaveReqVO.setThisNumber(new BigDecimal(energyRecordImportExcelVO.getThisNumber()));

                    BigDecimal thisUse = energySaveReqVO.getThisNumber().subtract(energySaveReqVO.getLastNumber());
                    energySaveReqVO.setThisUse(thisUse);
                    energySaveReqVO.setIsAuto("0");
                    energySaveReqVO.setPlanId(energyPlanDO.getId());


                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new ServiceException(406, e.getMessage());
                }

                try {
                    create(energySaveReqVO);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException(406, "水电分表导入错误" + e.getMessage());
                }
            });
            return true;
        } else {
            List<EnergyRecordImportExcelVO> energyRecordImportExcelList = importTemplate();
            energyRecordImportExcelList.addAll(list);
            // 输出
            try {
                ExcelUtils.write(response, "水电抄表记录导入错误.xls", "水电抄表记录列表", EnergyRecordImportExcelVO.class, energyRecordImportExcelList);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException(406, "水电抄表记录导入错误" + e.getMessage());
            }
            return false;
        }

    }
}