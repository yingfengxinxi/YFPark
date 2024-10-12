package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.date.DateUtils;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.web.core.util.WebFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryFeeSetDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryLabelDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategorySubcatDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeLogDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeOrderDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategoryFeeSetMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategoryLabelMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategoryMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategorySubcatMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderProposeLogMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderProposeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderProposeOrderMapper;
import cn.sdqingyun.smartpark.module.system.api.user.AdminUserApi;
import cn.sdqingyun.smartpark.module.system.api.user.dto.AdminUserRespDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.google.gson.Gson;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 机构工单数据 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class WorkOrderProposeServiceImpl implements WorkOrderProposeService {

    @Resource
    private WorkOrderProposeMapper Mapper;

    @Resource
    private CategoryFeeSetMapper categoryFeeSetMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CategorySubcatMapper categorySubcatMapper;

    @Resource
    private WorkOrderProposeLogMapper workOrderProposeLogMapper;

    @Resource
    private WorkOrderProposeOrderMapper workOrderProposeOrderMapper;

    @Resource
    private BuildMapper buildMapper;

    @Resource
    private CategoryLabelMapper categoryLabelMapper;

    @Resource
    private AdminUserApi adminUserApi;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(WorkOrderProposeSaveReqVO createReqVO) throws Exception {
        createReqVO.setFrom("PC");
        createReqVO.setSource("PC");
        createReqVO.setNumber(UuidUtils.generateUuid().replaceAll("-", ""));

        // 插入
        WorkOrderProposeDO workOrderProposeDO = BeanUtils.toBean(createReqVO, WorkOrderProposeDO.class);
        LambdaQueryWrapperX<CategoryFeeSetDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(CategoryFeeSetDO::getSubcatId, workOrderProposeDO.getSubcatId());
        CategoryFeeSetDO categoryFeeSetDO = categoryFeeSetMapper.selectOne(queryWrapperX);
        if (categoryFeeSetDO != null) {
            String feeSet = categoryFeeSetDO.getFeeSet();
            if (StringUtils.isNotEmpty(feeSet)) {
                workOrderProposeDO.setOrderType("2");
                JSONObject jsonObject = JSON.parseObject(feeSet);
                String chargeType = jsonObject.getString("chargeType");
                if (StringUtils.equals(chargeType, "1")) {
                    //收费方式;1=免费服务;2=下单即收费;3=完成后收费;
                    workOrderProposeDO.setPaidType("3");
                }
                if (StringUtils.equals(chargeType, "2")) {
                    //收费方式;1=免费服务;2=下单即收费;3=完成后收费;
                    workOrderProposeDO.setPaidType("2");
                }
            }

        } else {
            workOrderProposeDO.setPaidType("1");
            workOrderProposeDO.setOrderType("1");
        }

        workOrderProposeDO.setAppearTime(new Date());
        Date visitTime = workOrderProposeDO.getVisitTime();
        CategoryDO categoryDO = categoryMapper.selectById(workOrderProposeDO.getCategoryId());
        if (categoryDO != null) {
            Integer overHour = categoryDO.getOverHour();
            Date timeoutTime = DateUtils.addHourMillis(visitTime, overHour);
            workOrderProposeDO.setTimeoutTime(timeoutTime);
        }

        Mapper.insert(workOrderProposeDO);
        String orderType = workOrderProposeDO.getOrderType();

        WorkOrderProposeLogDO workOrderProposeLogDO = new WorkOrderProposeLogDO();
        workOrderProposeLogDO.setWorkorderId(workOrderProposeDO.getId());
        workOrderProposeLogDO.setVillageId(workOrderProposeDO.getVillageId());
        workOrderProposeLogDO.setBuildId(workOrderProposeDO.getBuildId());
        workOrderProposeLogDO.setApplication(workOrderProposeDO.getApplication());
        workOrderProposeLogDO.setFrom(workOrderProposeDO.getFrom());
        workOrderProposeLogDO.setSource("2");
        workOrderProposeLogDO.setOperateType("21");
        workOrderProposeLogDO.setStatus("1");
        workOrderProposeLogDO.setOperateTime(new Date());
        workOrderProposeLogDO.setUid(Long.valueOf(workOrderProposeDO.getCreator()));
        workOrderProposeLogDO.setRemark("创建工单");
        workOrderProposeLogMapper.insert(workOrderProposeLogDO);
        // 返回
        return workOrderProposeDO.getId();
    }


    @Override
    public void update(WorkOrderProposeSaveReqVO updateReqVO) {
        // 更新
        WorkOrderProposeDO updateObj = BeanUtils.toBean(updateReqVO, WorkOrderProposeDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public WorkOrderDetailVO workDetail(Long id) {
        WorkOrderDetailVO workOrderDetailVO = new WorkOrderDetailVO();
        WorkOrderProposeDO workOrderProposeDO = Mapper.selectById(id);
        if (workOrderProposeDO != null) {
            workOrderDetailVO.setNumber(workOrderProposeDO.getNumber());
            CategoryDO categoryDO = categoryMapper.selectById(workOrderProposeDO.getCategoryId());
            CategorySubcatDO categorySubcatDO = categorySubcatMapper.selectById(workOrderProposeDO.getSubcatId());
            if (categoryDO != null && categorySubcatDO != null) {
                workOrderDetailVO.setOrderType(categoryDO.getName() + "/" + categorySubcatDO.getName());
            }

            String repairType = workOrderProposeDO.getRepairType();
            workOrderDetailVO.setRepairType(repairType);

            String repairTypeName = DictFrameworkUtils.getDictDataLabel("WORK_ORDER_REPAIR_TYPE", repairType);
            workOrderDetailVO.setRepairTypeName(repairTypeName);

            String workOrderStatusName = DictFrameworkUtils.getDictDataLabel("WORK_ORDER_STATUS", workOrderProposeDO.getStatus());
            workOrderDetailVO.setWorkOrderStatusName(workOrderStatusName);

            workOrderDetailVO.setName(workOrderProposeDO.getName());
            workOrderDetailVO.setPhone(workOrderProposeDO.getPhone());
            workOrderDetailVO.setBuildId(workOrderProposeDO.getBuildId());
            BuildDO buildDO = buildMapper.selectById(workOrderProposeDO.getBuildId());
            if (buildDO != null) {
                workOrderDetailVO.setBuildName(buildDO.getBuildName());
            }

            workOrderDetailVO.setPosition(workOrderProposeDO.getPosition());
            String from = workOrderProposeDO.getFrom();
            String fromName = DictFrameworkUtils.getDictDataLabel("WORK_ORDER_FROM", from);
            workOrderDetailVO.setFrom(fromName);

            workOrderDetailVO.setAppearTime(workOrderProposeDO.getAppearTime());

            String dayOfWeek = DateUtils.getDayOfWeek(workOrderProposeDO.getVisitTime());
            workOrderDetailVO.setDayOfWeek(dayOfWeek);
            workOrderDetailVO.setVisitTime(workOrderProposeDO.getVisitTime());

            workOrderDetailVO.setTimeoutTime(workOrderProposeDO.getTimeoutTime());
            workOrderDetailVO.setIsTimeout(workOrderProposeDO.getIsTimeout());

            workOrderDetailVO.setPaidPayer(workOrderProposeDO.getPaidPayer());
            String paidPayerName = DictFrameworkUtils.getDictDataLabel("WORK_ORDER_PAID_PAYER", workOrderProposeDO.getPaidPayer());
            workOrderDetailVO.setPaidPayerName(paidPayerName);

            LambdaQueryWrapperX<CategoryLabelDO> queryWrapperX = new LambdaQueryWrapperX<>();
            List<String> labelIdList = List.of(workOrderProposeDO.getLabelJson().split(","));
            queryWrapperX.in(CategoryLabelDO::getId, labelIdList);
            queryWrapperX.orderByDesc(CategoryLabelDO::getSort);
            List<CategoryLabelDO> categoryLabelList = categoryLabelMapper.selectList(queryWrapperX);
            if (CollectionUtils.isNotEmpty(categoryLabelList)) {
                StringBuilder sb = new StringBuilder();
                categoryLabelList.forEach(aa -> sb.append(aa.getName()).append(","));

                String labelName = sb.toString();
                if (StringUtils.isNotEmpty(labelName)) {
                    workOrderDetailVO.setLabelJson(labelName.substring(0, labelName.length() - 1));
                }
            }


            workOrderDetailVO.setRemark(workOrderProposeDO.getRemark());

            workOrderDetailVO.setImages(workOrderProposeDO.getImages());


            LambdaQueryWrapperX<WorkOrderProposeLogDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.eq(WorkOrderProposeLogDO::getApplication, workOrderProposeDO.getApplication());
            queryWrapperX1.eq(WorkOrderProposeLogDO::getWorkorderId, workOrderProposeDO.getId());
            queryWrapperX1.orderByDesc(WorkOrderProposeLogDO::getCreateTime);
            List<WorkOrderProposeLogDO> orderProposeLogDOList = workOrderProposeLogMapper.selectList(queryWrapperX1);

            if (CollectionUtils.isNotEmpty(orderProposeLogDOList)) {
                orderProposeLogDOList.forEach(workOrderProposeLogDO -> {
                    String operateType = workOrderProposeLogDO.getOperateType();
                    String dataLabel = DictFrameworkUtils.getDictDataLabel("WORK_ORDER_OPERATE_TYPE", operateType);
                    workOrderProposeLogDO.setOperateType(dataLabel);
                    CommonResult<AdminUserRespDTO> userApiUser = adminUserApi.getUser(workOrderProposeLogDO.getUid());
                    if (userApiUser.getCode() == 0) {
                        AdminUserRespDTO data = userApiUser.getData();
                        workOrderProposeLogDO.setName(data.getNickname());
                        workOrderProposeLogDO.setPhone(data.getMobile());
                    }
                });
                workOrderDetailVO.setOrderProposeLogList(orderProposeLogDOList);
            }

            JSONObject orderDataJson = new JSONObject();
            List<JSONObject> workOrderProposeOrderListJson = Lists.newArrayList();
            //已付金额
            BigDecimal actualPayment = new BigDecimal("0.00");
            //总金额
            BigDecimal totalAmount = new BigDecimal("0.00");
            LambdaQueryWrapperX<WorkOrderProposeOrderDO> queryWrapperX2 = new LambdaQueryWrapperX<>();
            queryWrapperX2.eq(WorkOrderProposeOrderDO::getWorkorderId, id);
            queryWrapperX2.orderByDesc(WorkOrderProposeOrderDO::getCreateTime);
            List<WorkOrderProposeOrderDO> workOrderProposeOrderList = workOrderProposeOrderMapper.selectList(queryWrapperX2);
            if (CollectionUtils.isNotEmpty(workOrderProposeOrderList)) {
                //已付金额
                actualPayment = workOrderProposeOrderList.stream().
                        map(aa -> aa.getPaymentAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);


                //总金额
                totalAmount = workOrderProposeOrderList.stream().
                        map(aa -> aa.getNeedAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                workOrderProposeOrderList.forEach(workOrderProposeOrderDO -> {
                    String orderTypeName = DictFrameworkUtils.getDictDataLabel("WORL_ORDER_TYPE", workOrderProposeOrderDO.getOrderType());
                    String orderStatusName = DictFrameworkUtils.getDictDataLabel("WORL_ORDER_PAY_STATUS", workOrderProposeOrderDO.getOrderStatus());

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("orderType", orderTypeName);
                    jsonObject.put("orderStatus", orderStatusName);
                    jsonObject.put("needAmount", workOrderProposeOrderDO.getNeedAmount());
                    workOrderProposeOrderListJson.add(jsonObject);
                });
            }

            orderDataJson.put("actualPayment", actualPayment);
            orderDataJson.put("totalAmount", totalAmount);
            orderDataJson.put("workOrderProposeOrderList", workOrderProposeOrderListJson);
            workOrderDetailVO.setOrderData(new Gson().toJson(orderDataJson));
        }
        return workOrderDetailVO;
    }

    @Override
    public PageResult<WorkOrderProposeRespVO> getPage(WorkOrderProposePageReqVO pageReqVO) {

        LambdaQueryWrapperX<WorkOrderProposeDO> queryWrapperX = getWorkOrderProposeDOLambdaQueryWrapperX(pageReqVO);
        PageResult<WorkOrderProposeDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<WorkOrderProposeRespVO> respVOPageResult = BeanUtils.toBean(pageResult, WorkOrderProposeRespVO.class);
        List<WorkOrderProposeRespVO> list = respVOPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(workOrderProposeRespVO -> {
                LambdaQueryWrapperX<WorkOrderProposeOrderDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(WorkOrderProposeOrderDO::getStatus, "1");
                queryWrapperX1.eq(WorkOrderProposeOrderDO::getWorkorderId, workOrderProposeRespVO.getId());
                List<WorkOrderProposeOrderDO> workOrderProposeOrderList = workOrderProposeOrderMapper.selectList(queryWrapperX1);
                List<JSONObject> workOrderProposeOrderListJson = Lists.newArrayList();
                if (CollectionUtils.isNotEmpty(workOrderProposeOrderList)) {

                    workOrderProposeOrderList.forEach(workOrderProposeOrderDO -> {
                        String orderStatus = workOrderProposeOrderDO.getOrderStatus();
                        String orderStatusName = DictFrameworkUtils.getDictDataLabel("WORL_ORDER_PAY_STATUS", orderStatus);

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("orderStatus", orderStatus);
                        jsonObject.put("orderStatusName", orderStatusName);

                        String orderType = workOrderProposeOrderDO.getOrderType();
                        String orderTypeName = DictFrameworkUtils.getDictDataLabel("WORL_ORDER_TYPE", orderType);
                        jsonObject.put("orderType", orderType);
                        jsonObject.put("orderTypeName", orderTypeName);
                        jsonObject.put("needAmount", workOrderProposeOrderDO.getNeedAmount());
                        jsonObject.put("id", workOrderProposeOrderDO.getId());

                        workOrderProposeOrderListJson.add(jsonObject);
                    });

                    workOrderProposeRespVO.setPayInfo(new Gson().toJson(workOrderProposeOrderListJson));
                }
            });
        }
        return respVOPageResult;
    }

    @Override
    public List<JSONObject> getStatic(WorkOrderProposePageReqVO pageReqVO) {
        List<JSONObject> jsonList = Lists.newArrayList();
        LambdaQueryWrapperX<WorkOrderProposeDO> queryWrapperX = getWorkOrderProposeDOLambdaQueryWrapperX(pageReqVO);

        List<WorkOrderProposeDO> list = Mapper.selectPage(pageReqVO, queryWrapperX).getList();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "工单总数");
        jsonObject.put("key", "allTotal");
        jsonObject.put("value", list.size());
        jsonList.add(jsonObject);

        jsonObject = new JSONObject();
        jsonObject.put("name", "待处理工单");
        jsonObject.put("key", "waitTotal");
        long waitTotal = list.stream().filter(aa -> aa.getStatus().equals("1")).count();
        jsonObject.put("value", waitTotal);
        jsonList.add(jsonObject);

        jsonObject = new JSONObject();
        jsonObject.put("name", "超时工单");
        jsonObject.put("key", "timeoutTotal");
        long timeoutTotal = list.stream().filter(aa -> aa.getIsTimeout().equals("1")).count();
        jsonObject.put("value", timeoutTotal);
        jsonList.add(jsonObject);

        jsonObject = new JSONObject();
        jsonObject.put("name", "处理中工单");
        jsonObject.put("key", "takeTotal");
        long takeTotal = list.stream().filter(aa -> aa.getStatus().equals("2") || aa.getStatus().equals("3")).count();
        jsonObject.put("value", takeTotal);
        jsonList.add(jsonObject);
        return jsonList;
    }

    /**
     * @param id
     * @param remark
     * @param images
     * @param uid
     * @param status
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handelWorkOrder(Long id, String remark, String images, Long uid, String status) {
        WorkOrderProposeDO workOrderProposeDO = Mapper.selectById(id);
        String msg = "当前工单状态不合符直接回复状态,请重新查询数据";
        String operateType = "5";
        Long userId = WebFrameworkUtils.getLoginUserId();
        if (StringUtils.equals(status, "2")) {
            msg = "当前工单状态不合符指派状态,请重新查询数据";
            operateType = "4";
            userId = uid;
        }
        if (!StringUtils.equals(workOrderProposeDO.getStatus(), "1")) {

            throw new ServiceException(406, msg);
        }
        //修改状态
        workOrderProposeDO.setStatus(status);
        Mapper.updateById(workOrderProposeDO);

        WorkOrderProposeLogDO workOrderProposeLogDO = new WorkOrderProposeLogDO();
        workOrderProposeLogDO.setWorkorderId(workOrderProposeDO.getId());
        workOrderProposeLogDO.setVillageId(workOrderProposeDO.getVillageId());
        workOrderProposeLogDO.setBuildId(workOrderProposeDO.getBuildId());
        workOrderProposeLogDO.setApplication(workOrderProposeDO.getApplication());
        workOrderProposeLogDO.setFrom(workOrderProposeDO.getFrom());
        workOrderProposeLogDO.setSource("2");
        workOrderProposeLogDO.setOperateType(operateType);
        workOrderProposeLogDO.setStatus("1");
        workOrderProposeLogDO.setOperateTime(new Date());

        workOrderProposeLogDO.setUid(userId);
        if (StringUtils.isNotEmpty(remark)) {
            workOrderProposeLogDO.setRemark(remark);
        }

        if (StringUtils.isNotEmpty(images)) {
            workOrderProposeLogDO.setImages(images);
        }
        workOrderProposeLogMapper.insert(workOrderProposeLogDO);
    }

    @NotNull
    private LambdaQueryWrapperX<WorkOrderProposeDO> getWorkOrderProposeDOLambdaQueryWrapperX(WorkOrderProposePageReqVO pageReqVO) {
        LambdaQueryWrapperX<WorkOrderProposeDO> queryWrapperX = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotEmpty(pageReqVO.getApplication())) {
            queryWrapperX.eq(WorkOrderProposeDO::getApplication, pageReqVO.getApplication());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getNumber())) {
            queryWrapperX.like(WorkOrderProposeDO::getNumber, pageReqVO.getNumber());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getOrderType())) {
            List<String> orderTypeList = List.of(pageReqVO.getOrderType().split(","));
            queryWrapperX.in(WorkOrderProposeDO::getOrderType, orderTypeList);
        }
        if (StringUtils.isNotEmpty(pageReqVO.getHasNotice())) {
            queryWrapperX.eq(WorkOrderProposeDO::getHasNotice, pageReqVO.getHasNotice());
        }
        if (StringUtils.isNotEmpty(pageReqVO.getPaidStatus())) {
            List<String> paidStatusList = List.of(pageReqVO.getPaidStatus().split(","));
            queryWrapperX.in(WorkOrderProposeDO::getPaidStatus, paidStatusList);
        }
        if (StringUtils.isNotEmpty(pageReqVO.getIsTimeout())) {
            List<String> isTimeOutList = List.of(pageReqVO.getIsTimeout().split(","));
            queryWrapperX.in(WorkOrderProposeDO::getIsTimeout, isTimeOutList);
        }
        if (StringUtils.isNotEmpty(pageReqVO.getStatus())) {
            List<String> statusList = List.of(pageReqVO.getStatus().split(","));
            queryWrapperX.in(WorkOrderProposeDO::getStatus, statusList);
        }

        if (StringUtils.isNotEmpty(pageReqVO.getPaidPayer())) {
            queryWrapperX.eq(WorkOrderProposeDO::getPaidPayer, pageReqVO.getPaidPayer());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getVillageIds())) {
            List<String> villageIds = List.of(pageReqVO.getVillageIds().split(","));
            queryWrapperX.in(WorkOrderProposeDO::getVillageId, villageIds);
        }

        if (StringUtils.isNotEmpty(pageReqVO.getName())) {
            queryWrapperX.like(WorkOrderProposeDO::getName, pageReqVO.getName());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getPhone())) {
            queryWrapperX.like(WorkOrderProposeDO::getPhone, pageReqVO.getPhone());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getSubcatName())) {
            LambdaQueryWrapperX<CategorySubcatDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.eq(CategorySubcatDO::getApplication, pageReqVO.getApplication());
            queryWrapperX1.in(CategorySubcatDO::getName, pageReqVO.getSubcatName());
            List<CategorySubcatDO> categorySubcatDOS = categorySubcatMapper.selectList(queryWrapperX1);
            List<Long> subcatIdList = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(categorySubcatDOS)) {
                subcatIdList = categorySubcatDOS.stream().map(aa -> aa.getId()).collect(Collectors.toList());
            } else {
                subcatIdList.add(0L);
            }
            queryWrapperX.in(WorkOrderProposeDO::getSubcatId, subcatIdList);
        }

        if (StringUtils.isNotEmpty(pageReqVO.getDepartmentId())) {
            List<String> deptIdList = List.of(pageReqVO.getDepartmentId().split(","));
            LambdaQueryWrapperX<CategorySubcatDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.eq(CategorySubcatDO::getApplication, pageReqVO.getApplication());
            queryWrapperX1.in(CategorySubcatDO::getDepartmentId, deptIdList);
            List<CategorySubcatDO> categorySubcatDOS = categorySubcatMapper.selectList(queryWrapperX1);
            List<Long> subcatIdList = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(categorySubcatDOS)) {
                subcatIdList = categorySubcatDOS.stream().map(aa -> aa.getId()).collect(Collectors.toList());

            } else {
                subcatIdList.add(0L);

            }
            queryWrapperX.in(WorkOrderProposeDO::getSubcatId, subcatIdList);
        }

        if (StringUtils.isNotEmpty(pageReqVO.getStartTime())) {
            queryWrapperX.apply("DATE_FORMAT(appear_time,'%Y-%m-%d') between DATE_FORMAT(" + pageReqVO.getStartTime() + ",'%Y-%m-%d') and DATE_FORMAT(" + pageReqVO.getEndTime() + ",'%Y-%m-%d') ");
        }
        return queryWrapperX;
    }

}