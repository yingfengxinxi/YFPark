package cn.sdqingyun.smartpark.module.bus.service.patrol;

import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolPlanEquipmentDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTaskEquipmentDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTaskEquipmentLogDO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/9/23 11:58
 */
public class PatrolTaskEquipmentLogData {

    /**
     * 处理巡检日志表数据
     *
     * @param patrolPlanEquipmentDO
     * @param patrolTaskEquipmentDO
     * @param flag                  0=创建/修改1=定时任务
     * @return
     * @throws ParseException
     */
    public static List<PatrolTaskEquipmentLogDO> getTaskEquipmentLog(PatrolPlanEquipmentDO patrolPlanEquipmentDO, PatrolTaskEquipmentDO patrolTaskEquipmentDO, String flag) throws ParseException {
        List<PatrolTaskEquipmentLogDO> patrolTaskEquipmentLogList = Lists.newArrayList();
        String planRule = patrolPlanEquipmentDO.getPlanRule();
        JSONObject jsonObject = JSON.parseObject(planRule);
        String rule = jsonObject.getString("rule");
        String cycle2 = jsonObject.getString("cycle2");
        List<String> ruleList = Lists.newArrayList();
        if (StringUtils.equals(cycle2, "1")) {
            //个日
            ruleList.add("1");
        } else {
            //周、月
            ruleList = Lists.newArrayList(rule.split(","));
        }

        ruleList = ruleList.stream().sorted().collect(Collectors.toList());
        for (int i = 0; i < ruleList.size(); i++) {
            PatrolTaskEquipmentLogDO patrolTaskEquipmentLogDO = new PatrolTaskEquipmentLogDO();
            patrolTaskEquipmentLogDO.setOrgId(patrolTaskEquipmentDO.getOrgId());
            patrolTaskEquipmentLogDO.setApplication(patrolTaskEquipmentDO.getApplication());
            patrolTaskEquipmentLogDO.setPlanId(patrolTaskEquipmentDO.getPlanId());
            patrolTaskEquipmentLogDO.setPlanKey(patrolTaskEquipmentDO.getTaskKey());
            patrolTaskEquipmentLogDO.setCrontab(patrolTaskEquipmentDO.getTaskCycle());
            patrolTaskEquipmentLogDO.setRule(Integer.valueOf(ruleList.get(i)));
            patrolTaskEquipmentLogDO.setBuildTime(new Date());
            //计算下一次执行时间
            jsonObject.put("rule", patrolTaskEquipmentLogDO.getRule());
            patrolPlanEquipmentDO.setPlanRule(new Gson().toJson(jsonObject));
            //"{\"cycle1\":2,\"cycle2\":\"2\",\"rule\":\"1,3,7\",\"executeTime\":\"11:30\",\"cycleTxt\":\"每2个星期 周一周三周日 11:30点\"}"
            Date nextTime = PatrolComputingTime.getNextTime(patrolPlanEquipmentDO.getPlanRule(), patrolPlanEquipmentDO.getStartDate(), flag);
            patrolPlanEquipmentDO.setNextTime(nextTime);
            patrolTaskEquipmentLogDO.setTaskTime(patrolPlanEquipmentDO.getNextTime());
            patrolTaskEquipmentLogDO.setStatus("0");
            patrolTaskEquipmentLogDO.setSort(i + 1);
            patrolTaskEquipmentLogDO.setTotal(0);
            patrolTaskEquipmentLogList.add(patrolTaskEquipmentLogDO);
        }
        return patrolTaskEquipmentLogList;
    }
}
