package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import cn.sdqingyun.smartpark.framework.common.exception.ServerException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.bill.Tools;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategorySubcatDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategoryMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategorySubcatMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderProposeLogMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderProposeMapper;
import cn.sdqingyun.smartpark.module.system.api.dict.DictDataApi;
import cn.sdqingyun.smartpark.module.system.api.dict.dto.DictDataRespDTO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.annotation.Resource;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/9/13 17:00
 */
@Service
public class WorkOrderMyBoardServiceImpl implements WorkOrderMyBoardService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CategorySubcatMapper categorySubcatMapper;

    @Resource
    private WorkOrderProposeMapper workOrderProposeMapper;

    @Resource
    private WorkOrderProposeLogMapper workOrderProposeLogMapper;

    @Resource
    private DictDataApi dictDataApi;

    @Override
    public List<JSONObject> topStatic(String departmentIds, String application) {
        List<JSONObject> topStaticList = Lists.newArrayList();

        LambdaQueryWrapperX<WorkOrderProposeDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotEmpty(departmentIds)) {
            List<String> deptIdList = List.of(departmentIds.split(","));
            LambdaQueryWrapperX<CategorySubcatDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
            queryWrapperX2.eq(CategorySubcatDO::getApplication, application);
            queryWrapperX2.eq(CategorySubcatDO::getDepartmentId, deptIdList);
            List<CategorySubcatDO> categorySubcatList = categorySubcatMapper.selectList(queryWrapperX2);
            List<Long> subcatIdList=Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(categorySubcatList)) {
                subcatIdList  = categorySubcatList.stream().map(aa -> aa.getId()).collect(Collectors.toList());

            }else{
                subcatIdList.add(0L);
            }
            queryWrapperX1.in(WorkOrderProposeDO::getSubcatId, subcatIdList);

        }

        queryWrapperX1.eq(WorkOrderProposeDO::getApplication, application);
        List<WorkOrderProposeDO> workOrderProposeList = workOrderProposeMapper.selectList(queryWrapperX1);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "工单总数");
        jsonObject.put("key", "totalOrderNums");
        jsonObject.put("value", workOrderProposeList.size());
        topStaticList.add(jsonObject);

        jsonObject = new JSONObject();
        jsonObject.put("name", "待派工单数");
        jsonObject.put("key", "waitOrderHandleNums");
        long waitOrderHandleNums = workOrderProposeList.stream().filter(aa -> aa.getStatus().equals("1")).count();
        jsonObject.put("value", waitOrderHandleNums);
        topStaticList.add(jsonObject);

        jsonObject = new JSONObject();
        jsonObject.put("name", "未办结超时工单");
        jsonObject.put("key", "timeoutOrderNums");
        long timeoutOrderNums = workOrderProposeList.stream().filter(aa -> aa.getIsTimeout().equals("1") && (aa.getStatus().equals("1") || aa.getStatus().equals("2") || aa.getStatus().equals("3") || aa.getStatus().equals("9"))).count();
        jsonObject.put("value", timeoutOrderNums);
        topStaticList.add(jsonObject);

        jsonObject = new JSONObject();
        jsonObject.put("name", "处理中工单数");
        jsonObject.put("key", "handelOrderNums");
        long handelOrderNums = workOrderProposeList.stream().filter(aa -> aa.getStatus().equals("3")).count();
        jsonObject.put("value", handelOrderNums);
        topStaticList.add(jsonObject);

        jsonObject = new JSONObject();
        jsonObject.put("name", "超时工单数");
        jsonObject.put("key", "timeoutOrderNums");
        List<WorkOrderProposeDO> timeoutMonthNumsList = workOrderProposeList.stream().filter(aa -> aa.getIsTimeout().equals("1")).collect(Collectors.toList());
        jsonObject.put("value", timeoutMonthNumsList.size());
        topStaticList.add(jsonObject);
        AtomicReference<Integer> timeoutMonthNums = getIntegerAtomicReference(timeoutMonthNumsList);
        jsonObject = new JSONObject();
        jsonObject.put("name", "当月工单超时率");
        jsonObject.put("key", "thisMonthTimeoutRate");
        String thisMonthTimeoutRate = "0.00%";
        if (timeoutMonthNums.get() > 0) {
            thisMonthTimeoutRate = Tools.DecimalFormat(((double) timeoutMonthNumsList.size() / (double) timeoutMonthNums.get()) * 100) + "%";
        }
        jsonObject.put("value", thisMonthTimeoutRate);
        topStaticList.add(jsonObject);

        jsonObject = new JSONObject();
        jsonObject.put("name", "当月满意度");
        jsonObject.put("key", "thisMonthSatisfaction");
        Double thisMonthSatisfaction = getThisMonthSatisfaction(application, workOrderProposeList, "0");
        jsonObject.put("value", Tools.DecimalFormat(thisMonthSatisfaction) + "%");
        topStaticList.add(jsonObject);

        jsonObject = new JSONObject();
        jsonObject.put("name", "月环比");
        jsonObject.put("key", "chainMonthRatio");
        Double lastMonthSatisfaction = getThisMonthSatisfaction(application, workOrderProposeList, "1");
        String chainMonthRatio = "0.00";
        if (lastMonthSatisfaction > 0) {
            chainMonthRatio = Tools.DecimalFormat(((double) (thisMonthSatisfaction - lastMonthSatisfaction) / lastMonthSatisfaction));
        }
        jsonObject.put("value", chainMonthRatio);
        topStaticList.add(jsonObject);
        return topStaticList;
    }

    @Override
    public JSONObject workOrderNumsTread(String application) throws ParseException {
        JSONObject jsonObject = new JSONObject();

        //累计回复数
        LambdaQueryWrapperX<WorkOrderProposeLogDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkOrderProposeLogDO::getApplication, application);
        queryWrapperX.eq(WorkOrderProposeLogDO::getOperateType, "5");
        List<WorkOrderProposeLogDO> orderProposeLogDOList = workOrderProposeLogMapper.selectList(queryWrapperX);
        jsonObject.put("replyOrderTotal", orderProposeLogDOList.size());

        LambdaQueryWrapperX<WorkOrderProposeDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(WorkOrderProposeDO::getApplication, application);
        queryWrapperX1.eq(WorkOrderProposeDO::getStatus, "4");
        List<WorkOrderProposeDO> workOrderProposeDOList = workOrderProposeMapper.selectList(queryWrapperX1);
        //累计处理工单数
        jsonObject.put("workOrderTotal", orderProposeLogDOList.size() + workOrderProposeDOList.size());

        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        //一周之前日期
        String dateJDay = DateUtils.getDateJDay(new Date(), 6);
        List<String> dateBetweenDateList = DateUtils.getDays(sim.parse(dateJDay), DateUtils.getDayTime());
        List<JSONObject> jsonObjectList = Lists.newArrayList();
        for (String date : dateBetweenDateList) {
            JSONObject dateJson = new JSONObject();
            dateJson.put("date", date);
            String dayOfWeek = DateUtils.getDayOfWeek(sim.parse(date));
            dateJson.put("name", date.substring(5, date.length()) + dayOfWeek);
            LambdaQueryWrapperX<WorkOrderProposeDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
            queryWrapperX2.eq(WorkOrderProposeDO::getApplication, application);
            queryWrapperX2.apply(" DATE_FORMAT(create_time,'%Y-%m-%d')= DATE_FORMAT('" + date + "','%Y-%m-%d')");
            Long total = workOrderProposeMapper.selectCount(queryWrapperX2);
            dateJson.put("total", total);
            jsonObjectList.add(dateJson);
        }
        jsonObject.put("list", jsonObjectList);
        return jsonObject;
    }

    /**
     * //能评价无非是已回复/已办结  如果是有已办结的记录那就是给物业的评价  如果没有已办结的记录那就是给客服的评价
     *
     * @param application
     * @return
     */
    @Override
    public JSONObject satisfactionStatic(String application) {
        JSONObject jsonObject = new JSONObject();

        LambdaQueryWrapperX<WorkOrderProposeLogDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkOrderProposeLogDO::getApplication, application);
        List<String> operateTypeList = List.of("5,7".split(","));
        queryWrapperX.in(WorkOrderProposeLogDO::getOperateType, operateTypeList);
        List<WorkOrderProposeLogDO> orderProposeLogList = workOrderProposeLogMapper.selectList(queryWrapperX);
        //客服
        List<Long> kefuWorkOrderIds = orderProposeLogList.stream().filter(aa -> aa.getOperateType().equals("5")).map(aa -> aa.getWorkorderId()).collect(Collectors.toList());
        //物业
        List<Long> wuyeWorkOrderIds = orderProposeLogList.stream().filter(aa -> aa.getOperateType().equals("7")).map(aa -> aa.getWorkorderId()).collect(Collectors.toList());

        List<JSONObject> list = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            JSONObject listJsonObject = new JSONObject();
            int index = (i + 1);
            listJsonObject.put("index", index + "星");
            listJsonObject.put("kefu", 0L);
            if (CollectionUtils.isNotEmpty(kefuWorkOrderIds)) {
                LambdaQueryWrapperX<WorkOrderProposeLogDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.in(WorkOrderProposeLogDO::getWorkorderId, kefuWorkOrderIds);
                queryWrapperX1.in(WorkOrderProposeLogDO::getAppraiseLevel, index);
                Integer count = workOrderProposeLogMapper.selectList(queryWrapperX1).size();
                //Random random = new Random();
                //count = random.nextInt(5) + 1; // 生成1到5的随机数
                listJsonObject.put("kefu", count);
            }

            //Random random = new Random();
            //Integer wuye=random.nextInt(5) + 1;
            listJsonObject.put("wuye", 0);
            if (CollectionUtils.isNotEmpty(wuyeWorkOrderIds)) {
                LambdaQueryWrapperX<WorkOrderProposeLogDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.in(WorkOrderProposeLogDO::getWorkorderId, wuyeWorkOrderIds);
                queryWrapperX1.in(WorkOrderProposeLogDO::getAppraiseLevel, index);
                Integer count = workOrderProposeLogMapper.selectList(queryWrapperX1).size();
                listJsonObject.put("wuye", count);
            }

            list.add(listJsonObject);

        }
        jsonObject.put("list", list);

        List<JSONObject> total = Lists.newArrayList();
        JSONObject totalJsonObject = new JSONObject();

        int kefuTotals = list.stream().mapToInt(aa -> Integer.valueOf(aa.getString("kefu"))).sum();
        totalJsonObject.put("name", "客服");
        totalJsonObject.put("totals", kefuTotals);
        total.add(totalJsonObject);

        totalJsonObject = new JSONObject();
        int wuyeTotals = list.stream().mapToInt(aa -> Integer.valueOf(aa.getString("wuye"))).sum();
        totalJsonObject.put("name", "物业");
        totalJsonObject.put("totals", wuyeTotals);
        total.add(totalJsonObject);

        jsonObject.put("total", total);
        return jsonObject;
    }

    @Override
    public List<JSONObject> halfYearTread(String application) throws Exception {


        CommonResult<List<DictDataRespDTO>> workOrderSource = dictDataApi.getDictDataList("WORK_ORDER_SOURCE");
        List<DictDataRespDTO> dictList = workOrderSource.getData();
        List<JSONObject> list = Lists.newArrayList();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        String monthDate = DateUtils.getMonthDate(new Date(), 1);
        String dateJDay = DateUtils.getDateJDay(sim.parse(monthDate), 180);
        List<String> dateList = DateUtils.getMonthBetweenDate(sim.parse(dateJDay), sim.parse(monthDate));
        List<JSONObject> dateProposeList = workOrderProposeMapper.getDateProposeList(application, dateList);
        for (DictDataRespDTO dictDataRespDTO : dictList) {
            for (String date : dateList) {
                List<JSONObject> objectList = dateProposeList.stream().filter(aa -> aa.getString("date").equals(date) && aa.getString("name").equals(dictDataRespDTO.getValue())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(objectList)) {
                    JSONObject jsonObject = objectList.get(0);
                    jsonObject.put("name", dictDataRespDTO.getLabel());
                    list.add(jsonObject);
                } else {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", dictDataRespDTO.getLabel());
                    jsonObject.put("total", 0);
                    jsonObject.put("date", date);
                    list.add(jsonObject);
                }
            }
        }
        return list;
    }

    @Override
    public JSONObject workOrderTypeRatio(String application) {
        JSONObject jsonObject = new JSONObject();

        LambdaQueryWrapperX<WorkOrderProposeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkOrderProposeDO::getApplication, application);
        List<WorkOrderProposeDO> workOrderProposeList = workOrderProposeMapper.selectList(queryWrapperX);

        List<JSONObject> list = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(workOrderProposeList)) {

            Map<Long, List<WorkOrderProposeDO>> listMap = workOrderProposeList.stream().collect(Collectors.groupingBy(aa -> aa.getSubcatId()));
            for (Long subcatId : listMap.keySet()) {
                JSONObject jsonObject1 = new JSONObject();
                List<WorkOrderProposeDO> workOrderProposeDOList = listMap.get(subcatId);
                CategorySubcatDO categorySubcatDO = categorySubcatMapper.selectById(subcatId);
                if (categorySubcatDO != null) {
                    jsonObject1.put("subcatName", categorySubcatDO.getName());
                }
                jsonObject1.put("total", workOrderProposeDOList.size());
                Double percent = 0.00;
                if (workOrderProposeDOList.size() > 0) {
                    percent = ((double) workOrderProposeDOList.size() / (double) workOrderProposeList.size()) * 100;
                }
                jsonObject1.put("percent", Tools.DecimalFormat(percent));
                list.add(jsonObject1);
            }
        }

        jsonObject.put("list", list);
        jsonObject.put("total", workOrderProposeList.size());
        return jsonObject;
    }


    @Override
    public JSONObject orderTypeRatio(String application) {
        JSONObject jsonObject = new JSONObject();

        LambdaQueryWrapperX<WorkOrderProposeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkOrderProposeDO::getApplication, application);
        List<WorkOrderProposeDO> workOrderProposeList = workOrderProposeMapper.selectList(queryWrapperX);

        List<JSONObject> list = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(workOrderProposeList)) {

            Map<Long, List<WorkOrderProposeDO>> listMap = workOrderProposeList.stream().collect(Collectors.groupingBy(aa -> aa.getCategoryId()));
            for (Long categoryId : listMap.keySet()) {
                JSONObject jsonObject1 = new JSONObject();
                List<WorkOrderProposeDO> workOrderProposeDOList = listMap.get(categoryId);
                CategoryDO categoryDO = categoryMapper.selectById(categoryId);
                if (categoryDO != null) {
                    jsonObject1.put("categoryName", categoryDO.getName());
                }
                jsonObject1.put("total", workOrderProposeDOList.size());
                Double percent = 0.00;
                if (workOrderProposeDOList.size() > 0) {
                    percent = ((double) workOrderProposeDOList.size() / (double) workOrderProposeList.size()) * 100;
                }
                jsonObject1.put("percent", Tools.DecimalFormat(percent));
                list.add(jsonObject1);
            }
        }

        jsonObject.put("list", list);
        jsonObject.put("total", workOrderProposeList.size());
        return jsonObject;
    }

    /**
     * @param application
     * @param workOrderProposeList
     * @param type                 0=当月数据 1=上个月数据
     * @return
     */
    @NotNull
    private Double getThisMonthSatisfaction(String application, List<WorkOrderProposeDO> workOrderProposeList, String type) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM");
        String month = sim.format(new Date());
        if (StringUtils.equals(type, "1")) {
            month = DateUtils.getLastMonth();
        }
        List<Integer> thisMonthLevelNumsList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(workOrderProposeList)) {
            String finalMonth = month;
            workOrderProposeList.forEach(workOrderProposeDO -> {
                LocalDateTime createTime = workOrderProposeDO.getCreateTime();
                // 创建一个DateTimeFormatter对象，定义日期时间的格式
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                // 将LocalDateTime对象转换为字符串
                String formattedDateTime = createTime.format(formatter);


                if (formattedDateTime.contains(finalMonth)) {
                    thisMonthLevelNumsList.add(workOrderProposeDO.getAppraiseLevel());
                }
            });
        }
        //当月满意度
        Integer thisMonthLevelNums = 0;
        if (CollectionUtils.isNotEmpty(thisMonthLevelNumsList)) {
            for (Integer levelNums : thisMonthLevelNumsList) {
                if (levelNums != null) {
                    thisMonthLevelNums = thisMonthLevelNums + levelNums;
                }
            }
        }

        //当月工单评价总数
        Integer orderPjMonthNums = 0;
        if (CollectionUtils.isNotEmpty(workOrderProposeList)) {
            LambdaQueryWrapperX<WorkOrderProposeLogDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(WorkOrderProposeLogDO::getApplication, application);
            queryWrapperX.eq(WorkOrderProposeLogDO::getOperateType, "1");
            List<Long> workorderIdList = workOrderProposeList.stream().map(aa -> aa.getId()).collect(Collectors.toList());
            queryWrapperX.eq(WorkOrderProposeLogDO::getWorkorderId, workorderIdList);
            queryWrapperX.apply(" DATE_FORMAT(create_time,'%Y-%m')= DATE_FORMAT('" + month + "','%Y-%m')");
            orderPjMonthNums = workOrderProposeLogMapper.selectList(queryWrapperX).size();
        }

        Double thisMonthSatisfaction = 0.00;
        if (orderPjMonthNums > 0) {
            thisMonthSatisfaction = ((double) thisMonthLevelNums / (double) (orderPjMonthNums * 5)) * 100;
        }
        return thisMonthSatisfaction;
    }

    @NotNull
    private static AtomicReference<Integer> getIntegerAtomicReference(List<WorkOrderProposeDO> timeoutMonthNumsList) {
        AtomicReference<Integer> timeoutMonthNums = new AtomicReference<>(0);
        if (CollectionUtils.isNotEmpty(timeoutMonthNumsList)) {

            timeoutMonthNumsList.forEach(workOrderProposeDO -> {
                LocalDateTime createTime = workOrderProposeDO.getCreateTime();
                // 创建一个DateTimeFormatter对象，定义日期时间的格式
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                // 将LocalDateTime对象转换为字符串
                String formattedDateTime = createTime.format(formatter);
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM");
                String month = sim.format(new Date());
                if (formattedDateTime.contains(month)) {
                    //当月数据
                    timeoutMonthNums.set(timeoutMonthNums.get() + 1);
                }
            });
        }
        return timeoutMonthNums;
    }

}
