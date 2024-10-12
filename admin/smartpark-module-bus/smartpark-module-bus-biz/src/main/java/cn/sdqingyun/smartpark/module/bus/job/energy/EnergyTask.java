package cn.sdqingyun.smartpark.module.bus.job.energy;//package cn.sdqingyun.smartpark.module.bus.job.energy;
//
//import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
//import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
//import cn.sdqingyun.smartpark.framework.tenant.core.aop.TenantIgnore;
//import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyPlanDO;
//import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyRecordDO;
//import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyTaskDO;
//import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyTaskLogDO;
//import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.*;
//import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLocationDO;
//import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyPlanMapper;
//import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyTaskLogMapper;
//import cn.sdqingyun.smartpark.module.bus.dal.mysql.energy.EnergyTaskMapper;
//import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.*;
//import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyLocationMapper;
//import cn.sdqingyun.smartpark.module.bus.service.patrol.PatrolComputingTime;
//import cn.sdqingyun.smartpark.module.bus.service.patrol.PatrolTaskEquipmentLogData;
//import cn.sdqingyun.smartpark.module.system.api.notify.NotifyMessageSendApi;
//import cn.sdqingyun.smartpark.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.nacos.common.utils.CollectionUtils;
//import com.alibaba.nacos.common.utils.StringUtils;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.compress.utils.Lists;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @Author lvzy
// * @Date 2024/8/27 9:45
// * 抄表定时任务
// */
//@Component
//@Slf4j
//public class EnergyTask {
//
//    @Autowired
//    private PatrolRecordEquipmentMapper patrolRecordEquipmentMapper;
//
//    @Autowired
//    private EnergyTaskLogMapper energyTaskLogMapper;
//
//    @Autowired
//    private EnergyTaskMapper energyTaskMapper;
//
//    @Autowired
//    private EnergyPlanMapper energyPlanMapper;
//
//    @Autowired
//    private PatrolPositionMapper patrolPositionMapper;
//
//    @Autowired
//    private PropertyLocationMapper propertyLocationMapper;
//
//    @Resource
//    private NotifyMessageSendApi notifySendService;
//
//    /**
//     * 创建工单
//     */
//    @TenantIgnore
//    @XxlJob("bus-server:energyOrderCreate")
////    @Scheduled(cron = "0 * * * * ?")
//    public void patrolOrderCreate() {
//        try {
//            log.info("=============================START创建抄表工单=============================");
//            List<String> keyList = Lists.newArrayList();
//            //查询当前工单
//            LambdaQueryWrapperX<EnergyTaskDO> energyTaskDOWrapperX = new LambdaQueryWrapperX<>();
//            energyTaskDOWrapperX.eq(EnergyTaskDO::getHasLoop, "0");
//            List<EnergyTaskDO> energyTaskList = energyTaskMapper.selectList(energyTaskDOWrapperX);
//            if (CollectionUtils.isNotEmpty(energyTaskList)) {
//                energyTaskList.forEach(energyTaskDO -> {
//                    //EnergyPlanDO energyPlanDO = energyPlanMapper.selectById(energyTaskDO.getPlanId());
//                    //是否可以执行计划0=否1=是
//                    //SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
//                    //校验是否可以执行任务
//                    Integer startPlan = 1;
//                    if (startPlan == 1) {
//                        String key = energyTaskDO.getTaskKey();
//
//                        LambdaQueryWrapperX<EnergyTaskLogDO> taskLogWrapperX = new LambdaQueryWrapperX<>();
//                        taskLogWrapperX.eq(EnergyTaskLogDO::getPlanKey, key);
//                        taskLogWrapperX.eq(EnergyTaskLogDO::getStatus, "0");
//                        List<EnergyTaskLogDO> energyTaskLogList = energyTaskLogMapper.selectList(taskLogWrapperX);
//
//                        if (CollectionUtils.isNotEmpty(energyTaskLogList)) {
//                            energyTaskLogList.forEach(energyTaskLogDO -> {
//                                Date taskTime = energyTaskLogDO.getTaskTime();
//                                int i = taskTime.compareTo(new Date());
//                                if (i > 0) {
//                                    System.out.println("Date1 时间在 Date2 之后");
//                                    log.info("未到执行时间");
//                                }
//                                if (i <= 0) {
//                                    System.out.println("Date1 时间在 Date2 之前");
//                                    log.info("过了执行时间");
//                                    log.info("已到执行时间");
//                                    keyList.add(key);
//
//                                        //向抄表记录表中添加数据
//                                        EnergyRecordDO energyRecordDO = new EnergyRecordDO();
//                                    energyRecordDO.setType(energyTaskLogDO.getty());
//                                    energyRecordDO.setMeterType(planPositionDO.getPlanId());
//                                    energyRecordDO.setVillageId(patrolTaskEquipmentDO.getId());
//                                    energyRecordDO.setIsSigned("0");
//                                    energyRecordDO.setSignStatus("0");
//                                    energyRecordDO.setIsNfc("0");
//                                    energyRecordDO.setPatrolPositionId(planPositionDO.getPositionId());
//                                        PatrolPositionDO patrolPositionDO = patrolPositionMapper.selectById(planPositionDO.getPositionId());
//                                        PropertyLocationDO propertyLocationDO = propertyLocationMapper.selectById(patrolPositionDO.getPositionId());
//                                        if (propertyLocationDO != null) {
//                                            patrolRecordEquipmentDO.setAddress(propertyLocationDO.getName());
//                                        }
//                                        patrolRecordEquipmentDO.setUid(null);
//                                        patrolRecordEquipmentDO.setName(null);
//                                        patrolRecordEquipmentDO.setPosition(patrolPositionDO.getName());
//                                        patrolRecordEquipmentDO.setLaunchUid(null);
//                                        patrolRecordEquipmentDO.setLaunchName(null);
//                                        patrolRecordEquipmentDO.setTime(null);
//                                        patrolRecordEquipmentDO.setFormId(patrolPositionDO.getFormId());
//                                        patrolRecordEquipmentDO.setSort(patrolTaskEquipmentLogDO.getSort());
//                                        patrolRecordEquipmentDO.setWorkorderApp(patrolPlanEquipmentDO.getWorkOrderApp());
//                                        patrolRecordEquipmentDO.setPropertyList(patrolPositionDO.getPropertyId());
//                                        patrolRecordEquipmentDO.setStatus("1");
//                                        patrolRecordEquipmentDO.setHandleStatus("1");
//                                        patrolRecordEquipmentDO.setTenantId(patrolPlanEquipmentDO.getTenantId());
//                                        patrolRecordEquipmentMapper.insert(patrolRecordEquipmentDO);
//
//
//                                    patrolTaskEquipmentLogDO.setStatus("1");
//                                    patrolTaskEquipmentLogMapper.updateById(patrolTaskEquipmentLogDO);
//
//                                }
//                            });
//                        }
//                    }
//                });
//            }
//
//            log.info("============================START更新下次执行时间和最近一次执行时间============================");
//            if (CollectionUtils.isNotEmpty(keyList)) {
//                keyList.forEach(taskKey -> {
//                    List<PatrolTaskEquipmentLogDO> patrolTaskEquipmentLogList1 = patrolTaskEquipmentLogMapper.selectList(new LambdaQueryWrapperX<PatrolTaskEquipmentLogDO>().eq(PatrolTaskEquipmentLogDO::getPlanKey, taskKey).orderByAsc(PatrolTaskEquipmentLogDO::getSort));
//                    if (CollectionUtils.isNotEmpty(patrolTaskEquipmentLogList1)) {
//                        //更新抄表计划的最近执行时间和下次执行时间
//                        LambdaQueryWrapperX<PatrolTaskEquipmentDO> queryWrapperX = new LambdaQueryWrapperX<>();
//                        queryWrapperX.eq(PatrolTaskEquipmentDO::getTaskKey, taskKey);
//                        PatrolTaskEquipmentDO patrolTaskEquipmentDO = patrolTaskEquipmentMapper.selectOne(queryWrapperX);
//                        PatrolPlanEquipmentDO patrolPlanEquipmentDO = patrolPlanEquipmentMapper.selectById(patrolTaskEquipmentDO.getPlanId());
//                        patrolPlanEquipmentDO.setLastTime(patrolPlanEquipmentDO.getNextTime());
//                        List<PatrolTaskEquipmentLogDO> collect = patrolTaskEquipmentLogList1.stream().filter(aa -> aa.getStatus().equals("0")).collect(Collectors.toList());
//                        if (CollectionUtils.isNotEmpty(collect)) {
//                            patrolPlanEquipmentDO.setNextTime(collect.get(0).getTaskTime());
//                            patrolPlanEquipmentDO.setUpdateTime(LocalDateTime.now());
//                            patrolPlanEquipmentMapper.updateById(patrolPlanEquipmentDO);
//                            //更新提醒时间
//                            try {
//                                Date remindTime = PatrolComputingTime.getRemindTime(patrolPlanEquipmentDO.getRemindJson(), patrolPlanEquipmentDO.getNextTime());
//                                patrolTaskEquipmentDO.setRemindTime(remindTime);
//                            } catch (Exception e) {
//                                throw new RuntimeException(e);
//                            }
//                            patrolTaskEquipmentDO.setUpdateTime(LocalDateTime.now());
//                            patrolTaskEquipmentDO.setIsRemind("0");
//                            patrolTaskEquipmentMapper.updateById(patrolTaskEquipmentDO);
//                        }
//
//                        log.info("============================START重新向抄表日志表中添加数据============================");
//                        PatrolTaskEquipmentLogDO patrolTaskEquipmentLogDO = patrolTaskEquipmentLogList1.get(patrolTaskEquipmentLogList1.size() - 1);
//                        Date taskTime = patrolTaskEquipmentLogDO.getTaskTime();
//                        int i = taskTime.compareTo(new Date());
//                        if (i <= 0) {
//                            log.info("重新向抄表日志表中添加数据");
//                            try {
//                                List<PatrolTaskEquipmentLogDO> taskEquipmentLog = PatrolTaskEquipmentLogData.getTaskEquipmentLog(patrolPlanEquipmentDO, patrolTaskEquipmentDO, "1");
//                                patrolTaskEquipmentLogMapper.insertBatch(taskEquipmentLog);
//                                patrolPlanEquipmentDO.setNextTime(taskEquipmentLog.get(0).getTaskTime());
//                                patrolPlanEquipmentDO.setUpdateTime(LocalDateTime.now());
//                                patrolPlanEquipmentMapper.updateById(patrolPlanEquipmentDO);
//
//                                //更新提醒时间
//                                try {
//                                    Date remindTime = PatrolComputingTime.getRemindTime(patrolPlanEquipmentDO.getRemindJson(), patrolPlanEquipmentDO.getNextTime());
//                                    patrolTaskEquipmentDO.setRemindTime(remindTime);
//                                } catch (Exception e) {
//                                    throw new RuntimeException(e);
//                                }
//                                patrolTaskEquipmentDO.setUpdateTime(LocalDateTime.now());
//                                patrolTaskEquipmentDO.setIsRemind("0");
//                                patrolTaskEquipmentMapper.updateById(patrolTaskEquipmentDO);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        log.info("============================END重新向抄表日志表中添加数据============================");
//                    }
//                });
//            }
//            log.info("============================END更新下次执行时间和最近一次执行时间============================");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        log.info("=============================END创建抄表工单=============================");
//    }
//
//
//    /**
//     * 工单到期
//     */
//    @TenantIgnore
//    @XxlJob("bus-server:patrolOrderExpire")
//    //@Scheduled(cron = "0 * * * * ?")
//    public void patrolOrderExpire() {
//        log.info("=============================START抄表工单到期处理=============================");
//        List<PatrolTaskEquipmentDO> patrolTaskEquipmentList = patrolTaskEquipmentMapper.selectList();
//        if (CollectionUtils.isNotEmpty(patrolTaskEquipmentList)) {
//            patrolTaskEquipmentList.forEach(patrolTaskEquipmentDO -> {
//                PatrolPlanEquipmentDO patrolPlanEquipmentDO = patrolPlanEquipmentMapper.selectById(patrolTaskEquipmentDO.getPlanId());
//                Date timeoutTime = patrolTaskEquipmentDO.getTimeoutTime();
//                int i = timeoutTime.compareTo(new Date());
//                if (i <= 0) {
//                    String timeOutType = patrolPlanEquipmentDO.getTimeOutType();
//                    //超时处理规则类型;1=不处理;2=自动完成;3=挂起
//                    if (StringUtils.equals(timeOutType, "1")) {
//                        patrolPlanEquipmentDO.setPlanStatus("5");
//                    }
//
//                    if (StringUtils.equals(timeOutType, "2")) {
//                        patrolPlanEquipmentDO.setPlanStatus("4");
//                    }
//
//                    if (StringUtils.equals(timeOutType, "3")) {
//                        patrolPlanEquipmentDO.setPlanStatus("6");
//                    }
//                }
//
//                patrolPlanEquipmentMapper.updateById(patrolPlanEquipmentDO);
//
//                patrolTaskEquipmentDO.setStatus(patrolPlanEquipmentDO.getPlanStatus());
//                patrolTaskEquipmentDO.setIsTimeout("1");
//                patrolTaskEquipmentMapper.updateById(patrolTaskEquipmentDO);
//
//            });
//        }
//        log.info("=============================END抄表工单到期处理=============================");
//    }
//
//
//    /**
//     * 工单提醒
//     */
//    @TenantIgnore
//    @XxlJob("bus-server:patrolOrderRemind")
//    //@Scheduled(cron = "0 * * * * ?")
//    public void patrolOrderRemind() {
//        log.info("=============================START抄表工单提醒=============================");
//        LambdaQueryWrapperX<PatrolTaskEquipmentDO> patrolTaskEquipmentWrapperX = new LambdaQueryWrapperX<>();
//        patrolTaskEquipmentWrapperX.eq(PatrolTaskEquipmentDO::getHasLoop, "0");
//        patrolTaskEquipmentWrapperX.eq(PatrolTaskEquipmentDO::getIsRemind, "0");
//        List<PatrolTaskEquipmentDO> patrolTaskEquipmentList = patrolTaskEquipmentMapper.selectList(patrolTaskEquipmentWrapperX);
//        if (CollectionUtils.isNotEmpty(patrolTaskEquipmentList)) {
//            patrolTaskEquipmentList.forEach(patrolTaskEquipmentDO -> {
//                PatrolPlanEquipmentDO patrolPlanEquipmentDO = patrolPlanEquipmentMapper.selectById(patrolTaskEquipmentDO.getPlanId());
//                Date remindTime = patrolTaskEquipmentDO.getRemindTime();
//                int i = remindTime.compareTo(new Date());
//                System.out.println(remindTime);
//                System.out.println(new Date());
//                if (i <= 0) {
//                    String remindJson = patrolPlanEquipmentDO.getRemindJson();
//                    //提醒方式
//                    String noticeType = JSON.parseObject(remindJson).getString("noticeType");
//                    List<String> userIdList = List.of(patrolTaskEquipmentDO.getPostUids().split(","));
//                    //站内信
//                    if (StringUtils.equals(noticeType, "0")) {
//                        log.info("用户数量:" + userIdList.size());
//                        userIdList.forEach(userId -> {
//                            log.info("用户id:" + userId);
//                            NotifySendSingleToUserReqDTO notifySendSingleToUserReqDTO = new NotifySendSingleToUserReqDTO();
//                            notifySendSingleToUserReqDTO.setTemplateCode("PATROL_REMIND");
//                            notifySendSingleToUserReqDTO.setUserId(Long.valueOf(userId));
//                            Map<String, Object> templateParams = new HashMap<>();
//                            templateParams.put("taskName", patrolTaskEquipmentDO.getTaskName());
//                            templateParams.put("taskNumber", patrolTaskEquipmentDO.getTaskNumber());
//                            System.out.println("应交时间:" + patrolTaskEquipmentDO.getShouldTime());
//                            // 创建一个SimpleDateFormat对象，定义目标日期格式
//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            // 使用SimpleDateFormat对象将Date对象格式化为字符串
//                            String shouldTime = sdf.format(patrolTaskEquipmentDO.getShouldTime());
//                            // 打印转换后的日期时间字符串
//                            System.out.println("Formatted Date: " + shouldTime);
//                            templateParams.put("shouldTime", shouldTime);
//                            notifySendSingleToUserReqDTO.setTemplateParams(templateParams);
//                            CommonResult<Long> longCommonResult = notifySendService.sendSingleMessageToOwner(notifySendSingleToUserReqDTO);
//                            log.info("站内消息发送结果:" + longCommonResult.toString());
//
//                        });
//                        patrolTaskEquipmentDO.setIsRemind("1");
//                        patrolTaskEquipmentMapper.updateById(patrolTaskEquipmentDO);
//                    }
//
//                }
//            });
//        }
//        log.info("=============================END抄表工单提醒=============================");
//    }
//}
