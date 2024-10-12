package cn.sdqingyun.smartpark.framework.common.util.computingTime;

import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/9/23 11:59
 */
public class WorkOrderComputingTimeUtils {

    /**
     * @param planRule  计划周期
     * @param startDate 开始时间
     * @param flag      0=创建/修改1=定时任务
     * @return
     * @throws ParseException
     */
    public static Date getNextTime(String planRule, Date startDate, String flag) throws ParseException {
        //计算今天周几
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String planRule = patrolPlanEquipmentDO.getPlanRule();

        JSONObject jsonObject = JSON.parseObject(planRule);
        String executeTime = jsonObject.getString("executeTime");
        //cycle1:1=每1;2=每2;3=每3;4=每4
        String cycle1 = jsonObject.getString("cycle1");
        //cycle2 : 1=个日;2=个周;3=个月
        String cycle2 = jsonObject.getString("cycle2");

        if (StringUtils.equals(cycle2, "1")) {
            //Date startDate = patrolPlanEquipmentDO.getStartDate();
            //开始日期
            if (StringUtils.equals(flag, "1")) {
                int i = startDate.compareTo(new Date());
                if (i <= 0) {
                    //定时任务
                    startDate = date.parse(date.format(new Date()));
                }
            }

            //日
            String dateDay = DateUtils.getDateJiaDay(startDate, Integer.valueOf(cycle1)) + " " + executeTime + ":00";
            System.out.println("1下次任务执行时间:" + dateDay);
            return time.parse(dateDay);
            //patrolPlanEquipmentDO.setNextTime(time.parse(dateDay));
        }
        if (StringUtils.equals(cycle2, "2")) {
            //周
            List<String> weekList = Arrays.asList(jsonObject.getString("rule").split(","));
            List<String> weekDateList = Lists.newArrayList();
            for (String week : weekList) {
                Date startOfWeekAfter = DateUtils.getStartOfWeekAfter(Integer.valueOf(cycle1), Integer.valueOf(week));
                weekDateList.add(date.format(startOfWeekAfter));
            }
            String dateDay = weekDateList.stream().sorted() // 排序
                    .collect(Collectors.toList()).get(0) + " " + executeTime + ":00";
            System.out.println("2下次任务执行时间:" + dateDay);
            return time.parse(dateDay);
            //patrolPlanEquipmentDO.setNextTime(time.parse(dateDay));
        }
        if (StringUtils.equals(cycle2, "3")) {
            //月
            List<String> monthList = Arrays.asList(jsonObject.getString("rule").split(","));
            List<String> months = Lists.newArrayList();
            List<String> monthDateList = Arrays.asList(DateUtils.getMonthDate(new Date(), Integer.valueOf(cycle1)).split("-"));
            for (String month : monthList) {
                month = month.length() == 1 ? "0" + month : month;
                String monthDate = monthDateList.get(0) + "-" + monthDateList.get(1) + "-" + month;
                months.add(monthDate);
            }
            String dateDay = months.stream().sorted() // 排序
                    .collect(Collectors.toList()).get(0) + " " + executeTime + ":00";
            System.out.println("3下次任务执行时间:" + dateDay);
            return time.parse(dateDay);
            //patrolPlanEquipmentDO.setNextTime(time.parse(dateDay));
        }
        return null;
    }


    /**
     * 计算提醒时间
     *
     * @param remindJson
     * @param nextTime
     * @return
     * @throws Exception
     */
    public static Date getRemindTime(String remindJson, Date nextTime) throws Exception {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String remindJson = patrolPlanEquipmentDO.getRemindJson();
        //"{\"noticeTime\": 1,\"noticeType\":\"0,1\",\"noticeTimeUnit\": \"1\"}"
        //noticeType 提醒方式数据字典 REMIND
        //noticeTime 提前几个小时发送
        //noticeTimeUnit 单位1=小时2=分钟
        JSONObject remindJsonObject = JSON.parseObject(remindJson);
        String noticeTimeUnit = remindJsonObject.getString("noticeTimeUnit");
        String noticeTime = remindJsonObject.getString("noticeTime");
        String date = simpleDateFormat1.format(nextTime);
        if (StringUtils.equals(noticeTimeUnit, "1")) {
            //小时
            Date dateDay = DateUtils.reduceHourMillis(simpleDateFormat1.parse(date), Integer.valueOf(noticeTime));
            System.out.println("指定时间减去小时:" + simpleDateFormat1.format(dateDay));
            return dateDay;
            //patrolTaskEquipmentDO.setRemindTime(dateDay);
        }

        if (StringUtils.equals(noticeTimeUnit, "2")) {
            //分钟
            Date dateDay = DateUtils.reduceMinute(simpleDateFormat1.parse(date), Integer.valueOf(noticeTime));
            System.out.println("指定时间减去分钟:" + simpleDateFormat1.format(dateDay));
            return dateDay;
            // patrolTaskEquipmentDO.setRemindTime(dateDay);
        }
        return null;
    }

    /**
     * true需要重新计算时间false不需要重新计算时间
     *
     * @param planRuleOld 旧的执行规则
     * @param planRuleNew 新的执行规则
     * @return
     */
    //public static boolean getIsCreate(PatrolPlanEquipmentSaveReqVO updateReqVO, PatrolPlanEquipmentDO patrolPlanEquipmentDO) {
    public static boolean getIsCreate(String planRuleOld, String planRuleNew) {
        //String planRuleOld = patrolPlanEquipmentDO.getPlanRule();
        //String planRuleNew = updateReqVO.getPlanRule();
        JSONObject planRuleOldJson = JSONObject.parseObject(planRuleOld);
        JSONObject planRuleNewJson = JSONObject.parseObject(planRuleNew);
        //重复频率判断
        String cycle1Old = planRuleOldJson.getString("cycle1");
        String cycle1New = planRuleNewJson.getString("cycle1");
        if (!StringUtils.equals(cycle1Old, cycle1New)) {
            return true;
        }

        //重复频率单位判断
        String cycle2Old = planRuleOldJson.getString("cycle2");
        String cycle2New = planRuleNewJson.getString("cycle2");
        if (!StringUtils.equals(cycle2Old, cycle2New)) {
            return true;
        }
        //月/周

        String ruleOld = planRuleOldJson.getString("rule");
        String ruleNew = planRuleNewJson.getString("rule");
        if (StringUtils.isEmpty(ruleOld) && StringUtils.isNotEmpty(ruleNew)) {
            return true;
        }
        if (StringUtils.isNotEmpty(ruleOld) && StringUtils.isEmpty(ruleNew)) {
            return true;
        }
        if (StringUtils.isNotEmpty(ruleOld) && StringUtils.isNotEmpty(ruleNew)) {
            List<String> list = Lists.newArrayList();
            List<String> ruleOldList = List.of(ruleOld.split(","));
            list.addAll(ruleOldList);

            List<String> ruleNewList = List.of(ruleNew.split(","));
            list.addAll(ruleNewList);

            Map<String, List<String>> ruleMap = list.stream().collect(Collectors.groupingBy(rule -> rule));
            for (String key : ruleMap.keySet()) {
                int size = ruleMap.get(key).size();
                if (size != 2) {
                    return true;
                }
            }
        }
        return false;
    }
}
