package cn.sdqingyun.smartpark.module.bus.job.patrol;

import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.tenant.core.aop.TenantIgnore;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLocationDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.patrol.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.property.PropertyLocationMapper;
import cn.sdqingyun.smartpark.module.bus.service.patrol.PatrolComputingTime;
import cn.sdqingyun.smartpark.module.bus.service.patrol.PatrolTaskEquipmentLogData;
import cn.sdqingyun.smartpark.module.system.api.notify.NotifyMessageSendApi;
import cn.sdqingyun.smartpark.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.xxl.job.core.handler.annotation.XxlJob;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/8/27 9:45
 * 巡检定时任务
 */
@Component
@Slf4j
public class PatrolTask {

    @Autowired
    private PatrolRecordEquipmentMapper patrolRecordEquipmentMapper;

    @Autowired
    private PatrolTaskEquipmentLogMapper patrolTaskEquipmentLogMapper;

    @Autowired
    private PatrolTaskEquipmentMapper patrolTaskEquipmentMapper;

    @Autowired
    private PatrolPlanPositionMapper patrolPlanPositionMapper;

    @Autowired
    private PatrolPlanEquipmentMapper patrolPlanEquipmentMapper;

    @Autowired
    private PatrolPositionMapper patrolPositionMapper;

    @Autowired
    private PropertyLocationMapper propertyLocationMapper;

    @Resource
    private NotifyMessageSendApi notifySendService;

    /**
     * 创建工单
     */
    @TenantIgnore
    @XxlJob("bus-server:patrolOrderCreate")
//    @Scheduled(cron = "0 * * * * ?")
    public void patrolOrderCreate() {
        try {
            log.info("=============================START创建巡检工单=============================");
            List<String> keyList = Lists.newArrayList();
            //查询当前工单
            LambdaQueryWrapperX<PatrolTaskEquipmentDO> patrolTaskEquipmentWrapperX = new LambdaQueryWrapperX<>();
            patrolTaskEquipmentWrapperX.eq(PatrolTaskEquipmentDO::getHasLoop, "0");
            List<PatrolTaskEquipmentDO> patrolTaskEquipmentList = patrolTaskEquipmentMapper.selectList(patrolTaskEquipmentWrapperX);
            if (CollectionUtils.isNotEmpty(patrolTaskEquipmentList)) {
                patrolTaskEquipmentList.forEach(patrolTaskEquipmentDO -> {
                    PatrolPlanEquipmentDO patrolPlanEquipmentDO = patrolPlanEquipmentMapper.selectById(patrolTaskEquipmentDO.getPlanId());
                    //是否可以执行计划0=否1=是
                    SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                    //校验是否可以执行任务
                    Integer startPlan = 0;
                    try {
                        startPlan = getStartPlan(patrolPlanEquipmentDO, sim);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    if (startPlan == 1) {
                        String key = patrolTaskEquipmentDO.getTaskKey();
                        //查询巡检点
                        List<PatrolPlanPositionDO> planPositionDOList = patrolPlanPositionMapper.selectList(new LambdaQueryWrapperX<PatrolPlanPositionDO>().eq(PatrolPlanPositionDO::getPlanId, patrolPlanEquipmentDO.getId()));

                        LambdaQueryWrapperX<PatrolTaskEquipmentLogDO> taskLogWrapperX = new LambdaQueryWrapperX<>();
                        taskLogWrapperX.eq(PatrolTaskEquipmentLogDO::getPlanKey, key);
                        taskLogWrapperX.eq(PatrolTaskEquipmentLogDO::getStatus, "0");
                        List<PatrolTaskEquipmentLogDO> patrolTaskEquipmentLogList = patrolTaskEquipmentLogMapper.selectList(taskLogWrapperX);

                        if (CollectionUtils.isNotEmpty(patrolTaskEquipmentLogList)) {
                            patrolTaskEquipmentLogList.forEach(patrolTaskEquipmentLogDO -> {
                                Date taskTime = patrolTaskEquipmentLogDO.getTaskTime();
                                int i = taskTime.compareTo(new Date());
                                if (i > 0) {
                                    System.out.println("Date1 时间在 Date2 之后");
                                    log.info("未到执行时间");
                                }
                                if (i <= 0) {
                                    System.out.println("Date1 时间在 Date2 之前");
                                    log.info("过了执行时间");
                                    log.info("已到执行时间");
                                    keyList.add(key);
                                    planPositionDOList.forEach(planPositionDO -> {
                                        //List<String> userIdList = List.of(patrolTaskEquipmentDO.getPostUids().split(","));
//                                        if (CollectionUtils.isNotEmpty(userIdList)) {
//                                            userIdList.forEach(userId -> {
                                        //向巡检记录表中添加数据
                                        PatrolRecordEquipmentDO patrolRecordEquipmentDO = new PatrolRecordEquipmentDO();
                                        patrolRecordEquipmentDO.setApplication(patrolTaskEquipmentLogDO.getApplication());
                                        patrolRecordEquipmentDO.setPlanId(planPositionDO.getPlanId());
                                        patrolRecordEquipmentDO.setTaskId(patrolTaskEquipmentDO.getId());
                                        patrolRecordEquipmentDO.setIsSigned("0");
                                        patrolRecordEquipmentDO.setSignStatus("0");
                                        patrolRecordEquipmentDO.setIsNfc("0");
                                        patrolRecordEquipmentDO.setPatrolPositionId(planPositionDO.getPositionId());
                                        PatrolPositionDO patrolPositionDO = patrolPositionMapper.selectById(planPositionDO.getPositionId());
                                        PropertyLocationDO propertyLocationDO = propertyLocationMapper.selectById(patrolPositionDO.getPositionId());
                                        if (propertyLocationDO != null) {
                                            patrolRecordEquipmentDO.setAddress(propertyLocationDO.getName());
                                        }
                                        patrolRecordEquipmentDO.setUid(null);
                                        patrolRecordEquipmentDO.setName(null);
                                        patrolRecordEquipmentDO.setPosition(patrolPositionDO.getName());
                                        patrolRecordEquipmentDO.setLaunchUid(null);
                                        patrolRecordEquipmentDO.setLaunchName(null);
                                        patrolRecordEquipmentDO.setTime(null);
                                        patrolRecordEquipmentDO.setFormId(patrolPositionDO.getFormId());
                                        patrolRecordEquipmentDO.setSort(patrolTaskEquipmentLogDO.getSort());
                                        patrolRecordEquipmentDO.setWorkorderApp(patrolPlanEquipmentDO.getWorkOrderApp());
                                        patrolRecordEquipmentDO.setPropertyList(patrolPositionDO.getPropertyId());
                                        patrolRecordEquipmentDO.setStatus("1");
                                        patrolRecordEquipmentDO.setHandleStatus("1");
                                        patrolRecordEquipmentDO.setTenantId(patrolPlanEquipmentDO.getTenantId());
                                        patrolRecordEquipmentMapper.insert(patrolRecordEquipmentDO);
//                                            });
//                                        }
                                    });
                                    patrolTaskEquipmentLogDO.setStatus("1");
                                    patrolTaskEquipmentLogMapper.updateById(patrolTaskEquipmentLogDO);

                                }
                            });
                        }
                    }
                });
            }

            log.info("============================START更新下次执行时间和最近一次执行时间============================");
            if (CollectionUtils.isNotEmpty(keyList)) {
                keyList.forEach(taskKey -> {
                    List<PatrolTaskEquipmentLogDO> patrolTaskEquipmentLogList1 = patrolTaskEquipmentLogMapper.selectList(new LambdaQueryWrapperX<PatrolTaskEquipmentLogDO>().eq(PatrolTaskEquipmentLogDO::getPlanKey, taskKey).orderByAsc(PatrolTaskEquipmentLogDO::getSort));
                    if (CollectionUtils.isNotEmpty(patrolTaskEquipmentLogList1)) {
                        //更新巡检计划的最近执行时间和下次执行时间
                        LambdaQueryWrapperX<PatrolTaskEquipmentDO> queryWrapperX = new LambdaQueryWrapperX<>();
                        queryWrapperX.eq(PatrolTaskEquipmentDO::getTaskKey, taskKey);
                        PatrolTaskEquipmentDO patrolTaskEquipmentDO = patrolTaskEquipmentMapper.selectOne(queryWrapperX);
                        PatrolPlanEquipmentDO patrolPlanEquipmentDO = patrolPlanEquipmentMapper.selectById(patrolTaskEquipmentDO.getPlanId());
                        patrolPlanEquipmentDO.setLastTime(patrolPlanEquipmentDO.getNextTime());
                        List<PatrolTaskEquipmentLogDO> collect = patrolTaskEquipmentLogList1.stream().filter(aa -> aa.getStatus().equals("0")).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(collect)) {
                            patrolPlanEquipmentDO.setNextTime(collect.get(0).getTaskTime());
                            patrolPlanEquipmentDO.setUpdateTime(LocalDateTime.now());
                            patrolPlanEquipmentMapper.updateById(patrolPlanEquipmentDO);
                            //更新提醒时间
                            try {
                                Date remindTime = PatrolComputingTime.getRemindTime(patrolPlanEquipmentDO.getRemindJson(), patrolPlanEquipmentDO.getNextTime());
                                patrolTaskEquipmentDO.setRemindTime(remindTime);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            patrolTaskEquipmentDO.setUpdateTime(LocalDateTime.now());
                            patrolTaskEquipmentDO.setIsRemind("0");
                            patrolTaskEquipmentMapper.updateById(patrolTaskEquipmentDO);
                        }

                        log.info("============================START重新向巡检日志表中添加数据============================");
                        PatrolTaskEquipmentLogDO patrolTaskEquipmentLogDO = patrolTaskEquipmentLogList1.get(patrolTaskEquipmentLogList1.size() - 1);
                        Date taskTime = patrolTaskEquipmentLogDO.getTaskTime();
                        int i = taskTime.compareTo(new Date());
                        if (i <= 0) {
                            log.info("重新向巡检日志表中添加数据");
                            try {
                                List<PatrolTaskEquipmentLogDO> taskEquipmentLog = PatrolTaskEquipmentLogData.getTaskEquipmentLog(patrolPlanEquipmentDO, patrolTaskEquipmentDO, "1");
                                patrolTaskEquipmentLogMapper.insertBatch(taskEquipmentLog);
                                patrolPlanEquipmentDO.setNextTime(taskEquipmentLog.get(0).getTaskTime());
                                patrolTaskEquipmentDO.setShouldTime(patrolPlanEquipmentDO.getNextTime());
                                patrolPlanEquipmentDO.setUpdateTime(LocalDateTime.now());
                                patrolPlanEquipmentMapper.updateById(patrolPlanEquipmentDO);

                                //更新提醒时间
                                try {
                                    Date remindTime = PatrolComputingTime.getRemindTime(patrolPlanEquipmentDO.getRemindJson(), patrolPlanEquipmentDO.getNextTime());
                                    patrolTaskEquipmentDO.setRemindTime(remindTime);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                patrolTaskEquipmentDO.setUpdateTime(LocalDateTime.now());
                                patrolTaskEquipmentDO.setIsRemind("0");
                                patrolTaskEquipmentMapper.updateById(patrolTaskEquipmentDO);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        log.info("============================END重新向巡检日志表中添加数据============================");
                    }
                });
            }
            log.info("============================END更新下次执行时间和最近一次执行时间============================");
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("=============================END创建巡检工单=============================");
    }

    /**
     * 计算巡检工单是否开始和结束
     *
     * @param patrolPlanEquipmentDO
     * @param sim
     * @return
     * @throws Exception
     */
    private static Integer getStartPlan(PatrolPlanEquipmentDO patrolPlanEquipmentDO, SimpleDateFormat sim) throws Exception {
        Integer startPlan = 0;
        //查询下次任务开始时间
        Date startDate = patrolPlanEquipmentDO.getStartDate();
        int start = startDate.compareTo(sim.parse(sim.format(new Date())));
        //开始时间小于等于当前时间
        if (start <= 0) {
            //是否永久标识等于0
            if (StringUtils.equals(patrolPlanEquipmentDO.getDateFlag(), "1")) {
                //永久不过期
                startPlan = 1;
            } else {
                Date endDate = patrolPlanEquipmentDO.getEndDate();
                int end = endDate.compareTo(sim.parse(sim.format(new Date())));
                //结束时间大于等于当前
                if (end >= 0) {
                    startPlan = 1;
                }
            }
        }
        return startPlan;
    }

    /**
     * 工单到期
     */
    @TenantIgnore
    @XxlJob("bus-server:patrolOrderExpire")
    //@Scheduled(cron = "0 * * * * ?")
    public void patrolOrderExpire() {
        log.info("=============================START巡检工单到期处理=============================");
        List<PatrolTaskEquipmentDO> patrolTaskEquipmentList = patrolTaskEquipmentMapper.selectList();
        if (CollectionUtils.isNotEmpty(patrolTaskEquipmentList)) {
            patrolTaskEquipmentList.forEach(patrolTaskEquipmentDO -> {
                PatrolPlanEquipmentDO patrolPlanEquipmentDO = patrolPlanEquipmentMapper.selectById(patrolTaskEquipmentDO.getPlanId());
                Date timeoutTime = patrolTaskEquipmentDO.getTimeoutTime();
                int i = timeoutTime.compareTo(new Date());
                if (i <= 0) {
                    String timeOutType = patrolPlanEquipmentDO.getTimeOutType();
                    //超时处理规则类型;1=不处理;2=自动完成;3=挂起
                    if (StringUtils.equals(timeOutType, "1")) {
                        patrolPlanEquipmentDO.setPlanStatus("5");
                    }

                    if (StringUtils.equals(timeOutType, "2")) {
                        patrolPlanEquipmentDO.setPlanStatus("4");
                    }

                    if (StringUtils.equals(timeOutType, "3")) {
                        patrolPlanEquipmentDO.setPlanStatus("6");
                    }
                }

                patrolPlanEquipmentMapper.updateById(patrolPlanEquipmentDO);

                patrolTaskEquipmentDO.setStatus(patrolPlanEquipmentDO.getPlanStatus());
                patrolTaskEquipmentDO.setIsTimeout("1");
                patrolTaskEquipmentMapper.updateById(patrolTaskEquipmentDO);

            });
        }
        log.info("=============================END巡检工单到期处理=============================");
    }


    /**
     * 工单提醒
     */
    @TenantIgnore
    @XxlJob("bus-server:patrolOrderRemind")
    //@Scheduled(cron = "0 * * * * ?")
    public void patrolOrderRemind() {
        log.info("=============================START巡检工单提醒=============================");
        LambdaQueryWrapperX<PatrolTaskEquipmentDO> patrolTaskEquipmentWrapperX = new LambdaQueryWrapperX<>();
        patrolTaskEquipmentWrapperX.eq(PatrolTaskEquipmentDO::getHasLoop, "0");
        patrolTaskEquipmentWrapperX.eq(PatrolTaskEquipmentDO::getIsRemind, "0");
        List<PatrolTaskEquipmentDO> patrolTaskEquipmentList = patrolTaskEquipmentMapper.selectList(patrolTaskEquipmentWrapperX);
        if (CollectionUtils.isNotEmpty(patrolTaskEquipmentList)) {
            patrolTaskEquipmentList.forEach(patrolTaskEquipmentDO -> {
                PatrolPlanEquipmentDO patrolPlanEquipmentDO = patrolPlanEquipmentMapper.selectById(patrolTaskEquipmentDO.getPlanId());
                Date remindTime = patrolTaskEquipmentDO.getRemindTime();
                int i = remindTime.compareTo(new Date());
                System.out.println(remindTime);
                System.out.println(new Date());
                if (i <= 0) {
                    String remindJson = patrolPlanEquipmentDO.getRemindJson();
                    //提醒方式
                    String noticeType = JSON.parseObject(remindJson).getString("noticeType");
                    List<String> userIdList = List.of(patrolTaskEquipmentDO.getPostUids().split(","));
                    //站内信
                    if (StringUtils.equals(noticeType, "0")) {
                        log.info("用户数量:" + userIdList.size());
                        userIdList.forEach(userId -> {
                            log.info("用户id:" + userId);
                            NotifySendSingleToUserReqDTO notifySendSingleToUserReqDTO = new NotifySendSingleToUserReqDTO();
                            notifySendSingleToUserReqDTO.setTemplateCode("PATROL_REMIND");
                            notifySendSingleToUserReqDTO.setUserId(Long.valueOf(userId));
                            Map<String, Object> templateParams = new HashMap<>();
                            templateParams.put("taskName", patrolTaskEquipmentDO.getTaskName());
                            templateParams.put("taskNumber", patrolTaskEquipmentDO.getTaskNumber());
                            System.out.println("应交时间:" + patrolTaskEquipmentDO.getShouldTime());
                            // 创建一个SimpleDateFormat对象，定义目标日期格式
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            // 使用SimpleDateFormat对象将Date对象格式化为字符串
                            String shouldTime = sdf.format(patrolTaskEquipmentDO.getShouldTime());
                            // 打印转换后的日期时间字符串
                            System.out.println("Formatted Date: " + shouldTime);
                            templateParams.put("shouldTime", shouldTime);
                            notifySendSingleToUserReqDTO.setTemplateParams(templateParams);
                            CommonResult<Long> longCommonResult = notifySendService.sendSingleMessageToOwner(notifySendSingleToUserReqDTO);
                            log.info("站内消息发送结果:" + longCommonResult.toString());

                        });
                        patrolTaskEquipmentDO.setIsRemind("1");
                        patrolTaskEquipmentMapper.updateById(patrolTaskEquipmentDO);
                    }

                }
            });
        }
        log.info("=============================END巡检工单提醒=============================");
    }
}
