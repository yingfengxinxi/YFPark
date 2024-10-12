package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import cn.sdqingyun.smartpark.framework.common.enums.order.PayOrderDisplayModeEnum;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.bill.Tools;
import cn.sdqingyun.smartpark.framework.common.util.currency.CurrencyConverter;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.tenant.core.aop.TenantIgnore;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.OrderRecordDetailVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderProposeOrderPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderProposeOrderRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderProposeOrderSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategorySubcatDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.owner.OwnerDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.pay.PayOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.pay.PayOrderWorkOrderDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.RoomDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeOrderDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategoryMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.category.CategorySubcatMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.owner.OwnerMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.pay.PayOrderWorkOrderMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.systemUser.SystemUserMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.BuildMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.RoomMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.village.VillageMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderProposeMapper;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.workOrder.WorkOrderProposeOrderMapper;
import cn.sdqingyun.smartpark.module.pay.api.client.ClientApi;
import cn.sdqingyun.smartpark.module.pay.api.order.PayOrderApi;
import cn.sdqingyun.smartpark.module.pay.api.order.dto.PayOrderCreateReqDTO;
import cn.sdqingyun.smartpark.module.pay.api.order.vo.PayOrderSubmitReqVO;
import cn.sdqingyun.smartpark.module.pay.api.payapp.PayAppApi;
import cn.sdqingyun.smartpark.module.pay.api.refund.PayRefundApi;
import cn.sdqingyun.smartpark.module.pay.api.refund.dto.PayRefundCreateReqDTO;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.framework.common.util.servlet.ServletUtils.getClientIP;

/**
 * 付费工单订单 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class WorkOrderProposeOrderServiceImpl implements WorkOrderProposeOrderService {

    @Resource
    private WorkOrderProposeOrderMapper Mapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private WorkOrderProposeMapper workOrderProposeMapper;

    @Resource
    private VillageMapper villageMapper;

    @Resource
    private BuildMapper buildMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private OwnerMapper ownerMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CategorySubcatMapper categorySubcatMapper;

    @Resource
    private PayOrderWorkOrderMapper payOrderWorkOrderMapper;

    @Resource
    private ClientApi clientApi;

    @Resource
    private PayOrderApi payOrderApi;

    @Resource
    private PayAppApi payAppApi;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private PayRefundApi payRefundApi;

    @Override
    public Long create(WorkOrderProposeOrderSaveReqVO createReqVO) {
        // 插入
        WorkOrderProposeOrderDO workOrderProposeOrderDO = BeanUtils.toBean(createReqVO, WorkOrderProposeOrderDO.class);
        Mapper.insert(workOrderProposeOrderDO);
        // 返回
        return workOrderProposeOrderDO.getId();
    }

    @Override
    public void update(WorkOrderProposeOrderSaveReqVO updateReqVO) {

        // 更新
        WorkOrderProposeOrderDO updateObj = BeanUtils.toBean(updateReqVO, WorkOrderProposeOrderDO.class);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {

        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public WorkOrderProposeOrderDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public PageResult<WorkOrderProposeOrderRespVO> getPage(WorkOrderProposeOrderPageReqVO pageReqVO) {
        LambdaQueryWrapperX<WorkOrderProposeOrderDO> queryWrapperX = getWorkOrderProposeOrderDOLambdaQueryWrapperX(pageReqVO);

        queryWrapperX.orderByDesc(WorkOrderProposeOrderDO::getCreateTime);
        PageResult<WorkOrderProposeOrderDO> pageResult = Mapper.selectPage(pageReqVO, queryWrapperX);
        PageResult<WorkOrderProposeOrderRespVO> voPageResult = BeanUtils.toBean(pageResult, WorkOrderProposeOrderRespVO.class);
        List<WorkOrderProposeOrderRespVO> list = voPageResult.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(workOrderProposeOrderRespVO -> {
                Long workorderId = workOrderProposeOrderRespVO.getWorkorderId();
                WorkOrderProposeDO workOrderProposeDO = workOrderProposeMapper.selectById(workorderId);
                workOrderProposeOrderRespVO.setWorkOrderNumber(workOrderProposeDO.getNumber());

                VillageDO villageDO = villageMapper.selectById(workOrderProposeOrderRespVO.getVillageId());
                if (villageDO != null) {
                    workOrderProposeOrderRespVO.setVillageName(villageDO.getName());
                }

                BuildDO buildDO = buildMapper.selectById(workOrderProposeOrderRespVO.getBuildId());
                if (buildDO != null) {
                    workOrderProposeOrderRespVO.setBuildName(buildDO.getBuildName());
                }
                if (workOrderProposeOrderRespVO.getOwnerId() != null) {
                    OwnerDO ownerDO = ownerMapper.selectById(workOrderProposeOrderRespVO.getOwnerId());
                    if (ownerDO != null) {
                        workOrderProposeOrderRespVO.setOwnerName(ownerDO.getName());
                    }
                }
                if (StringUtils.isNotEmpty(workOrderProposeOrderRespVO.getRoomId())) {
                    StringBuilder sb = new StringBuilder();
                    List<String> roomIds = List.of(workOrderProposeOrderRespVO.getRoomId().split(","));
                    if (CollectionUtils.isNotEmpty(roomIds)) {
                        LambdaQueryWrapperX<RoomDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                        queryWrapperX1.in(RoomDO::getId, roomIds);
                        List<RoomDO> roomList = roomMapper.selectList(queryWrapperX1);
                        if (CollectionUtils.isNotEmpty(roomList)) {
                            roomList.forEach(roomDO -> {
                                if (roomDO != null) {
                                    String roomName = roomDO.getRoomName();
                                    String lc = roomName.substring(0, roomName.length() - 2);
                                    sb.append(lc + "-" + roomName).append(",");
                                }
                            });

                        }
                    }
                    String roomName = sb.toString();
                    if (StringUtils.isNotEmpty(roomName)) {
                        roomName = roomName.substring(0, roomName.length() - 1);
                        workOrderProposeOrderRespVO.setRoomName(roomName);
                    }
                }

            });
        }
        return voPageResult;
    }

    @Override
    public List<JSONObject> getOrderStatistics(WorkOrderProposeOrderPageReqVO pageReqVO) {
        List<JSONObject> jsonObjectList = Lists.newArrayList();
        LambdaQueryWrapperX<WorkOrderProposeOrderDO> queryWrapperX = getWorkOrderProposeOrderDOLambdaQueryWrapperX(pageReqVO);

        queryWrapperX.orderByDesc(WorkOrderProposeOrderDO::getCreateTime);
        List<WorkOrderProposeOrderDO> list = Mapper.selectPage(pageReqVO, queryWrapperX).getList();
        //工单订单数
        Integer orderNum = 0;
        //工单费用
        BigDecimal orderPrice = new BigDecimal("0.00");
        //工单退款订单数
        Integer orderRefundNum = 0;
        //工单退款费用
        BigDecimal orderRefundPrice = new BigDecimal("0.00");
        if (CollectionUtils.isNotEmpty(list)) {
            //工单订单数
            orderNum = list.size();
            //工单费用
            orderPrice = list.stream().map(aa -> aa.getPaymentAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
            //工单退款订单数
            orderRefundNum = list.stream().filter(aa -> aa.getOrderStatus().equals("3")).collect(Collectors.toList()).size();
            //工单退款费用
            orderRefundPrice = list.stream().filter(aa -> aa.getOrderStatus().equals("3")).map(aa -> aa.getRefundAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", "orderNum");
        jsonObject.put("name", "工单订单数");
        jsonObject.put("value", orderNum);
        jsonObjectList.add(jsonObject);

        jsonObject = new JSONObject();
        jsonObject.put("key", "orderPrice");
        jsonObject.put("name", "工单费用");
        jsonObject.put("value", Tools.DecimalFormat(orderPrice));
        jsonObjectList.add(jsonObject);

        jsonObject = new JSONObject();
        jsonObject.put("key", "orderRefundNum");
        jsonObject.put("name", "工单退款订单数");
        jsonObject.put("value", orderRefundNum);
        jsonObjectList.add(jsonObject);

        jsonObject = new JSONObject();
        jsonObject.put("key", "orderRefundPrice");
        jsonObject.put("name", "工单退款费用");
        jsonObject.put("value", Tools.DecimalFormat(orderRefundPrice));
        jsonObjectList.add(jsonObject);
        return jsonObjectList;
    }

    @NotNull
    private LambdaQueryWrapperX<WorkOrderProposeOrderDO> getWorkOrderProposeOrderDOLambdaQueryWrapperX(WorkOrderProposeOrderPageReqVO pageReqVO) {
        LambdaQueryWrapperX<WorkOrderProposeOrderDO> queryWrapperX = new LambdaQueryWrapperX<>();

        queryWrapperX.eq(WorkOrderProposeOrderDO::getApplication, pageReqVO.getApplication());
        if (pageReqVO.getVillageId() != null) {
            queryWrapperX.eq(WorkOrderProposeOrderDO::getVillageId, pageReqVO.getVillageId());
        }

        if (pageReqVO.getBuildId() != null) {
            queryWrapperX.eq(WorkOrderProposeOrderDO::getBuildId, pageReqVO.getBuildId());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getOwnerName())) {
            LambdaQueryWrapperX<OwnerDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.like(OwnerDO::getName, pageReqVO.getOwnerName());
            List<OwnerDO> ownerList = ownerMapper.selectList(queryWrapperX1);
            List<Long> ownerIdList = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(ownerList)) {
                ownerIdList = ownerList.stream().map(aa -> aa.getId()).collect(Collectors.toList());
            } else {
                ownerIdList.add(0L);
            }
            queryWrapperX.in(WorkOrderProposeOrderDO::getOwnerId, ownerIdList);
        }
        if (StringUtils.isNotEmpty(pageReqVO.getOrderNumber())) {
            queryWrapperX.like(WorkOrderProposeOrderDO::getOrderNumber, pageReqVO.getOrderNumber());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getOrderStatus())) {
            queryWrapperX.eq(WorkOrderProposeOrderDO::getOrderStatus, pageReqVO.getOrderStatus());
        }

        if (pageReqVO.getCategoryId() != null) {
            queryWrapperX.eq(WorkOrderProposeOrderDO::getCategoryId, pageReqVO.getCategoryId());
        }

        if (pageReqVO.getSubcatId() != null) {
            queryWrapperX.eq(WorkOrderProposeOrderDO::getSubcatId, pageReqVO.getSubcatId());
        }

        if (StringUtils.isNotEmpty(pageReqVO.getStartPaymentTime())) {
            queryWrapperX.apply("DATE_FORMAT(payment_time,'%Y-%m-%d') between DATE_FORMAT(" + pageReqVO.getStartPaymentTime() + ",'%Y-%m-%d') and DATE_FORMAT(" + pageReqVO.getEndPaymentTime() + ",'%Y-%m-%d') ");
        }
        if (StringUtils.isNotEmpty(pageReqVO.getOrderType())) {
            queryWrapperX.eq(WorkOrderProposeOrderDO::getOrderType, pageReqVO.getOrderType());
        }

        if (pageReqVO.getDepartmentId() != null) {
            LambdaQueryWrapperX<CategorySubcatDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
            queryWrapperX1.eq(CategorySubcatDO::getDepartmentId, pageReqVO.getDepartmentId());
            List<CategorySubcatDO> categorySubcatDOList = categorySubcatMapper.selectList(queryWrapperX1);
            List<Long> subcatId = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(categorySubcatDOList)) {
                subcatId = categorySubcatDOList.stream().map(aa -> aa.getId()).collect(Collectors.toList());

            } else {
                subcatId.add(0L);
            }
            queryWrapperX.in(WorkOrderProposeOrderDO::getSubcatId, subcatId);

        }

        if (StringUtils.isNotEmpty(pageReqVO.getGenerateStatus())) {
            queryWrapperX.eq(WorkOrderProposeOrderDO::getGenerateStatus, pageReqVO.getGenerateStatus());
        }
        return queryWrapperX;
    }

    @Override
    public OrderRecordDetailVO orderRecordDetail(Long id) {
        WorkOrderProposeOrderDO workOrderProposeOrderDO = Mapper.selectById(id);
        OrderRecordDetailVO orderRecordDetailVO = new OrderRecordDetailVO();
        if (workOrderProposeOrderDO != null) {
            String orderTypeName = DictFrameworkUtils.getDictDataLabel("WORL_ORDER_TYPE", workOrderProposeOrderDO.getOrderType());
            orderRecordDetailVO.setOrderTypeName(orderTypeName);
            orderRecordDetailVO.setOrderNumber(workOrderProposeOrderDO.getOrderNumber());
            orderRecordDetailVO.setPaymentAmount(workOrderProposeOrderDO.getPaymentAmount());
            orderRecordDetailVO.setPaymentTime(workOrderProposeOrderDO.getPaymentTime());
            orderRecordDetailVO.setPayMethod(workOrderProposeOrderDO.getPayMethod());
            orderRecordDetailVO.setCreateTime(workOrderProposeOrderDO.getCreateTime());

            String userName = systemUserMapper.getByOperatorIdUserName(Long.valueOf(workOrderProposeOrderDO.getCreator()));
            if (StringUtils.isNotEmpty(userName)) {
                orderRecordDetailVO.setCreator(userName);
            }

            String orderStatusName = DictFrameworkUtils.getDictDataLabel("WORL_ORDER_PAY_STATUS", workOrderProposeOrderDO.getOrderStatus());
            orderRecordDetailVO.setOrderStatusName(orderStatusName);

            String refundStatusName = DictFrameworkUtils.getDictDataLabel("WORK_ORDER_REFUND_STATUS", workOrderProposeOrderDO.getRefundStatus());
            orderRecordDetailVO.setRefundStatusName(refundStatusName);

            WorkOrderProposeDO workOrderProposeDO = workOrderProposeMapper.selectById(workOrderProposeOrderDO.getWorkorderId());
            if (workOrderProposeDO != null) {
                String workOrderStatusName = DictFrameworkUtils.getDictDataLabel("WORK_ORDER_STATUS", workOrderProposeDO.getStatus());
                orderRecordDetailVO.setWorkOrderStatusName(workOrderStatusName);
                orderRecordDetailVO.setWorkOrderNumber(workOrderProposeDO.getNumber());

                getWarrantyAddress(workOrderProposeDO, orderRecordDetailVO);

                Long ownerId = workOrderProposeDO.getOwnerId();
                if (ownerId != null) {
                    OwnerDO ownerDO = ownerMapper.selectById(ownerId);
                    if (ownerDO != null) {
                        orderRecordDetailVO.setOwnerName(ownerDO.getName());
                    }

                }

                CategoryDO categoryDO = categoryMapper.selectById(workOrderProposeDO.getCategoryId());
                CategorySubcatDO categorySubcatDO = categorySubcatMapper.selectById(workOrderProposeDO.getSubcatId());
                orderRecordDetailVO.setSubcatName(categoryDO.getName() + "/" + categorySubcatDO.getName());
            }

        }
        return orderRecordDetailVO;
    }

    private void getWarrantyAddress(WorkOrderProposeDO workOrderProposeDO, OrderRecordDetailVO orderRecordDetailVO) {
        VillageDO villageDO = villageMapper.selectById(workOrderProposeDO.getVillageId());
        BuildDO buildDO = buildMapper.selectById(workOrderProposeDO.getBuildId());
        String warrantyAddress = villageDO.getName() + "/" + buildDO.getBuildName();
        if (StringUtils.isNotEmpty(workOrderProposeDO.getRoomIds())) {
            StringBuilder sb = new StringBuilder();
            List<String> roomIds = List.of(workOrderProposeDO.getRoomIds().split(","));
            if (CollectionUtils.isNotEmpty(roomIds)) {
                LambdaQueryWrapperX<RoomDO> queryWrapperX = new LambdaQueryWrapperX<>();
                queryWrapperX.in(RoomDO::getId, roomIds);
                List<RoomDO> roomList = roomMapper.selectList(queryWrapperX);
                if (CollectionUtils.isNotEmpty(roomList)) {
                    roomList.forEach(roomDO -> {
                        if (roomDO != null) {
                            String roomName = roomDO.getRoomName();
                            String lc = roomName.substring(0, roomName.length() - 2);
                            sb.append(lc + "-" + roomName).append(",");
                        }
                    });

                }
            }
            String roomName = sb.toString();
            if (StringUtils.isNotEmpty(roomName)) {
                roomName = roomName.substring(0, roomName.length() - 1);
                warrantyAddress = warrantyAddress + "/" + roomName;
            }
        }
        orderRecordDetailVO.setWarrantyAddress(warrantyAddress);
    }

    /**
     * 扫码支付
     *
     * @param ids
     * @return
     */
    @Override
    public String scanCodeBillPay(String ids) {
        try {
            List<String> idList = List.of(ids.split(","));

            String merchantOrderIdNew = UuidUtils.generateUuid().replace("-", "");

            LambdaQueryWrapperX<WorkOrderProposeOrderDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.in(WorkOrderProposeOrderDO::getId, idList);
            List<WorkOrderProposeOrderDO> workOrderProposeOrderList = Mapper.selectList(queryWrapperX);
            workOrderProposeOrderList.forEach(workOrderProposeOrderDO -> {

                String orderStatus = workOrderProposeOrderDO.getOrderStatus();
                if (StringUtils.equals(orderStatus, "2")) {
                    throw new ServiceException(406, "当前支付订单中存在已支付订单,请核对后重新支付");
                }

                PayOrderWorkOrderDO payOrderWorkOrderDO = new PayOrderWorkOrderDO();
                payOrderWorkOrderDO.setApplication(workOrderProposeOrderDO.getApplication());
                payOrderWorkOrderDO.setMerchantOrderId(merchantOrderIdNew);
                payOrderWorkOrderDO.setWorkorderProposeOrderId(workOrderProposeOrderDO.getId());
                BigDecimal needAmount = workOrderProposeOrderDO.getNeedAmount();
                int price = CurrencyConverter.convertYuanToFen(needAmount);
                payOrderWorkOrderDO.setPrice(price);
                payOrderWorkOrderDO.setOrderType("0");

                //校验支付订单中是否存在已支付或者已支付正在处理的订单
                LambdaQueryWrapperX<PayOrderWorkOrderDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
                queryWrapperX1.eq(PayOrderWorkOrderDO::getWorkorderProposeOrderId, payOrderWorkOrderDO.getWorkorderProposeOrderId());
                queryWrapperX1.eq(PayOrderWorkOrderDO::getOrderType, "0");
                PayOrderWorkOrderDO payOrderWorkOrderOld = payOrderWorkOrderMapper.selectOne(queryWrapperX1);
                if (payOrderWorkOrderOld != null) {
                    System.out.println("PayOrderWorkOrderDO中存在数据:" + payOrderWorkOrderOld.toString());
                    //0=等待通知10=通知成功20=通知失败21=请求成功，但是结果失败22=请求失败
                    Integer status = payOrderWorkOrderMapper.getByMerchantOrderIdStatus(payOrderWorkOrderOld.getMerchantOrderId());
                    System.out.println("回调接口中存在数据:" + status);
                    if (status != null) {
                        if (status == 0) {
                            throw new ServiceException(406, "当前支付订单中存在已支付或者支付成功正在处理订单,请核对后重新支付");
                        }
                        if (status == 10 || status == 20 || status == 21 || status == 22) {
                            throw new ServiceException(406, "当前订单中存在已支付订单,请核对后重新支付");
                        }
                    }
                    payOrderWorkOrderDO.setId(payOrderWorkOrderOld.getId());
                    payOrderWorkOrderMapper.updateById(payOrderWorkOrderDO);
                    System.out.println("更新payOrderWorkOrderDO表数据:" + payOrderWorkOrderDO.toString());
                } else {
                    payOrderWorkOrderMapper.insert(payOrderWorkOrderDO);
                    System.out.println("插入payOrderWorkOrderDO表数据:" + payOrderWorkOrderDO.toString());
                }
            });
            String channelCode = "wx_native";
            Long appId = 11L;
            CommonResult<Long> channelCodeResult = clientApi.getByChannelCodeId(appId, channelCode);
            PayOrderCreateReqDTO payOrderCreateReqDTO = new PayOrderCreateReqDTO();
            payOrderCreateReqDTO.setAppId(appId);
            payOrderCreateReqDTO.setUserIp(getClientIP());
            payOrderCreateReqDTO.setMerchantOrderId(merchantOrderIdNew);
            payOrderCreateReqDTO.setChannelId(channelCodeResult.getData());
            //支付总金额
            BigDecimal needAmount = workOrderProposeOrderList.stream().
                    map(aa -> aa.getNeedAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
            String msg = "支付费用" + needAmount + "元";
            payOrderCreateReqDTO.setSubject(msg);
            payOrderCreateReqDTO.setBody(msg);
            int price = CurrencyConverter.convertYuanToFen(needAmount);
            payOrderCreateReqDTO.setPrice(price);
            LocalDateTime localDateTime = LocalDateTime.now();
            LocalDateTime expireTime = localDateTime.plusMinutes(10);
            payOrderCreateReqDTO.setExpireTime(expireTime);
            CommonResult<Long> order = payOrderApi.createOrder(payOrderCreateReqDTO);
            if (order.getCode() != 0) {
                throw new ServiceException(406, order.getMsg());
            }
            Long payOrderId = order.getData();
            PayOrderSubmitReqVO payOrderSubmitReqVO = new PayOrderSubmitReqVO();
            payOrderSubmitReqVO.setId(payOrderId);
            payOrderSubmitReqVO.setChannelCode(channelCode);
            Map<String, String> channelExtras = new HashMap<>();
            payOrderSubmitReqVO.setChannelExtras(channelExtras);
            payOrderSubmitReqVO.setDisplayMode(PayOrderDisplayModeEnum.QR_CODE_URL.getMode());

            CommonResult<?> app = payAppApi.getApp(payOrderCreateReqDTO.getAppId());
            if (app.getData() == null) {
                throw new RuntimeException("当前渠道APPID无效");
            }
            CommonResult<?> commonResult = payOrderApi.submitOrder(payOrderSubmitReqVO);
            if (commonResult.getCode().equals(0)) {
                redisTemplate.opsForValue().set(payOrderCreateReqDTO.getMerchantOrderId(), ids, 60, TimeUnit.MINUTES);
                Object data = commonResult.getData();
                JSONObject jsonObject = new JSONObject((Map<String, Object>) data);
                System.out.println("支付地址:" + jsonObject.toString());
                String displayContent = jsonObject.getString("displayContent");
                return displayContent;
            }
            throw new ServiceException(406, commonResult.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(406, e.getMessage());
        }
    }

    @Override
    public void orderRefund(String id, String refundStatus, BigDecimal refundAmount) {

        WorkOrderProposeOrderDO workOrderProposeOrderDO = Mapper.selectById(id);
        String orderStatus = workOrderProposeOrderDO.getOrderStatus();
        if (StringUtils.equals(orderStatus, "1")) {
            throw new ServiceException(406, "退款失败！当前订单未付款,请支付后再做退款操作");
        }

        if (StringUtils.equals(orderStatus, "3")) {
            throw new ServiceException(406, "退款失败！当前订单已退款成功,请核对订单");
        }
        String merchantOrderIdNew = UuidUtils.generateUuid().replace("-", "");
        PayOrderWorkOrderDO payOrderWorkOrderDO = new PayOrderWorkOrderDO();
        payOrderWorkOrderDO.setApplication(workOrderProposeOrderDO.getApplication());
        payOrderWorkOrderDO.setMerchantOrderId(merchantOrderIdNew);
        payOrderWorkOrderDO.setWorkorderProposeOrderId(workOrderProposeOrderDO.getId());
        int price = CurrencyConverter.convertYuanToFen(refundAmount);
        payOrderWorkOrderDO.setPrice(price);
        payOrderWorkOrderDO.setOrderType("1");

        //校验支付订单中是否存在已支付或者已支付正在处理的订单
        LambdaQueryWrapperX<PayOrderWorkOrderDO> queryWrapperX1 = new LambdaQueryWrapperX<>();
        queryWrapperX1.eq(PayOrderWorkOrderDO::getWorkorderProposeOrderId, payOrderWorkOrderDO.getWorkorderProposeOrderId());
        queryWrapperX1.eq(PayOrderWorkOrderDO::getOrderType, "1");
        PayOrderWorkOrderDO payOrderWorkOrderOld = payOrderWorkOrderMapper.selectOne(queryWrapperX1);
        if (payOrderWorkOrderOld != null) {
            System.out.println("PayOrderWorkOrderDO中存在数据:" + payOrderWorkOrderOld.toString());
            //0=等待通知10=通知成功20=通知失败21=请求成功，但是结果失败22=请求失败
            Integer status = payOrderWorkOrderMapper.getByMerchantOrderIdStatus(payOrderWorkOrderOld.getMerchantOrderId());
            System.out.println("回调接口中存在数据:" + status);
            if (status != null) {
                if (status == 0) {
                    throw new ServiceException(406, "当前订单存在已退款或者退款成功正在处理订单,请稍后刷新订单列表");
                }
                if (status == 10 || status == 20 || status == 21 || status == 22) {
                    throw new ServiceException(406, "当前订单已退款,请稍后刷新订单列表");
                }
            }
            payOrderWorkOrderDO.setId(payOrderWorkOrderOld.getId());
            payOrderWorkOrderMapper.updateById(payOrderWorkOrderDO);
            System.out.println("更新payOrderWorkOrderDO表数据:" + payOrderWorkOrderDO.toString());
        } else {
            payOrderWorkOrderMapper.insert(payOrderWorkOrderDO);
            System.out.println("插入payOrderWorkOrderDO表数据:" + payOrderWorkOrderDO.toString());
        }


        Long appId = 11L;
        // 2.1 生成退款单号
        // 一般来说，用户发起退款的时候，都会单独插入一个售后维权表，然后使用该表的 id 作为 refundId
        // 这里我们是个简单的 demo，所以没有售后维权表，直接使用订单 id + "-refund" 来演示
        String refundId = merchantOrderIdNew;
        // 2.2 创建退款单
        Long payRefundId = payRefundApi.createRefund(new PayRefundCreateReqDTO()
                .setAppId(appId).setUserIp(getClientIP()) // 支付应用
                .setMerchantOrderId(workOrderProposeOrderDO.getThirdOrderNo()) // 支付单号
                .setMerchantRefundId(refundId)
                .setReason("退款金额：" + refundAmount + "元").setPrice(payOrderWorkOrderDO.getPrice())).getCheckedData();// 价格信息
        if (payRefundId != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("refundStatus", refundStatus);
            jsonObject.put("refundAmount", refundAmount);
            jsonObject.put("payRefundId", payRefundId);
            redisTemplate.opsForValue().set(payOrderWorkOrderDO.getMerchantOrderId(), new Gson().toJson(jsonObject), 60, TimeUnit.MINUTES);
        }
    }
}