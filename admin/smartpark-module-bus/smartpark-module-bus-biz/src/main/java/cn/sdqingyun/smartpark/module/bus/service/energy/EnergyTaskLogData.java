package cn.sdqingyun.smartpark.module.bus.service.energy;

import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyPlanDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyTaskDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyTaskLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolPlanEquipmentDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTaskEquipmentDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTaskEquipmentLogDO;
import cn.sdqingyun.smartpark.module.bus.service.patrol.PatrolComputingTime;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/9/23 11:58
 */
public class EnergyTaskLogData {

    /**
     * 处理抄表日志表数据
     *
     * @param energyPlanDO
     * @param energyTaskDO
     * @param flag
     * @return
     * @throws ParseException
     */
    public static List<EnergyTaskLogDO> getTaskLog(EnergyPlanDO energyPlanDO, EnergyTaskDO energyTaskDO, String flag) throws ParseException {
        List<EnergyTaskLogDO> energyTaskLogList = Lists.newArrayList();
        String planRule = energyPlanDO.getPlanRule();
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
            EnergyTaskLogDO energyTaskLogDO = new EnergyTaskLogDO();
            energyTaskLogDO.setOrgId(energyTaskDO.getOrgId());
            energyTaskLogDO.setPlanId(energyTaskDO.getPlanId());
            energyTaskLogDO.setPlanKey(energyTaskDO.getTaskKey());
            energyTaskLogDO.setCrontab(energyTaskDO.getTaskCycle());
            energyTaskLogDO.setRule(Integer.valueOf(ruleList.get(i)));
            energyTaskLogDO.setBuildTime(new Date());
            //计算下一次执行时间
            jsonObject.put("rule", energyTaskLogDO.getRule());
            energyPlanDO.setPlanRule(new Gson().toJson(jsonObject));
            //"{\"cycle1\":2,\"cycle2\":\"2\",\"rule\":\"1,3,7\",\"executeTime\":\"11:30\",\"cycleTxt\":\"每2个星期 周一周三周日 11:30点\"}"
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            Date nextTime = PatrolComputingTime.getNextTime(energyPlanDO.getPlanRule(), sim.parse(sim.format(new Date())), flag);
            energyPlanDO.setNextTime(nextTime);
            energyTaskLogDO.setTaskTime(energyPlanDO.getNextTime());
            energyTaskLogDO.setStatus("0");
            energyTaskLogDO.setSort(i + 1);
            energyTaskLogDO.setTotal(0);
            energyTaskLogList.add(energyTaskLogDO);
        }
        return energyTaskLogList;
    }
}
