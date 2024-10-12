package cn.sdqingyun.smartpark.module.bus.service.resv;

import cn.hutool.core.collection.CollUtil;
import cn.sdqingyun.smartpark.framework.common.enums.order.PayOrderStatusEnum;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.common.util.qrcode.QRCodeUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.security.core.util.SecurityFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingDetilVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingOrderRefundSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPayOrderPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPayOrderSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.*;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.resv.*;
import cn.sdqingyun.smartpark.module.infra.api.file.FileApi;
import cn.sdqingyun.smartpark.module.pay.api.order.PayOrderApi;
import cn.sdqingyun.smartpark.module.pay.api.order.dto.PayOrderCreateReqDTO;
import cn.sdqingyun.smartpark.module.pay.api.order.dto.PayOrderRespDTO;
import cn.sdqingyun.smartpark.module.pay.api.refund.PayRefundApi;
import cn.sdqingyun.smartpark.module.pay.api.refund.dto.PayRefundCreateReqDTO;
import cn.sdqingyun.smartpark.module.pay.api.refund.dto.PayRefundRespDTO;
import cn.sdqingyun.smartpark.module.pay.enums.refund.PayRefundStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static cn.hutool.core.util.ObjectUtil.notEqual;
import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.framework.common.util.date.LocalDateTimeUtils.addTime;
import static cn.sdqingyun.smartpark.framework.common.util.json.JsonUtils.toJsonString;
import static cn.sdqingyun.smartpark.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;
import static cn.sdqingyun.smartpark.module.pay.enums.ErrorCodeConstants.*;

/**
 * 订单支付 Service 实现类
 *
 * @author 智慧园区
 */
@Service
@Validated
@Slf4j
public class ResvPayOrderServiceImpl implements ResvPayOrderService {

    @Resource
    private ResvPayOrderMapper resvPayOrderMapper;
    @Resource
    private ResvBookingMapper resvBookingMapper;
    @Resource
    private ResvBookingOrderMapper resvBookingOrderMapper;
    @Resource
    private PayOrderApi payOrderApi;
    @Resource
    private PayRefundApi payRefundApi;
    @Resource
    private ResvBookingOrderRefundMapper resvBookingOrderRefundMapper;
    @Resource
    private ResvBookingVerificationMapper resvBookingVerificationMapper;
    @Resource
    private FileApi fileApi;
    @Resource
    private ResvPlaceMapper resvPlaceMapper;
    @Resource
    private ResvPlaceCategoryMapper resvPlaceCategoryMapper;

    /**
     * 接入的实力应用编号
     *
     * 从 [支付管理 -> 应用信息] 里添加
     */
    private static final Long PAY_APP_ID = 10L;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createResvPayOrder(ResvPayOrderSaveReqVO createReqVO) {
        createReqVO.setPayStatus( 0 );
        createReqVO.setUserId( SecurityFrameworkUtils.getLoginUserId() );
        //根据订单号查询相关业务
        ResvBookingDO resvBooking = resvBookingMapper.selectOne( "order_no", createReqVO.getOrderNo() );
        if(resvBooking == null){
            throw exception(RESV_BOOKING_NOT_EXISTS);
        }
        if(resvBooking.getStatus() != 0){
            switch(resvBooking.getStatus()){
                case 1:
                    throw new ServiceException(406,"当前订单已支付，请稍后查看");
                case 2:
                    throw new ServiceException(406,"当前订单已核销，请稍后查看");
                case 3:
                    throw new ServiceException(406,"当前订单已过期，请重新预约");
                default:
                    throw new ServiceException(406,"订单状态异常,请联系管理员");
            }
        }

        ResvBookingOrderDO resvBookingOrderDO = resvBookingOrderMapper.selectOne( new LambdaQueryWrapperX<ResvBookingOrderDO>().eq( ResvBookingOrderDO::getOrderNo, createReqVO.getOrderNo() ) );
        if(resvBookingOrderDO == null){
            throw exception(RESV_BOOKING_ORDER_NOT_EXISTS);
        }
        if(resvBookingOrderDO.getOrderStatus() != 0){
            switch(resvBookingOrderDO.getOrderStatus()){
                case 1:
                    throw new ServiceException(406,"当前订单已支付，请刷新查看");
                case 2:
                    throw new ServiceException(406,"当前订单已核销，请刷新查看");
                case 3:
                    throw new ServiceException(406,"当前订单已取消，请重新预约");
                case 4:
                    throw new ServiceException(406,"当前订单已过期，请重新预约");
                default:
                    throw new ServiceException(406,"订单状态异常,请联系管理员");
            }
        }

        // 插入订单
        ResvPayOrderDO resvPayOrder = BeanUtils.toBean(createReqVO, ResvPayOrderDO.class);
        resvPayOrderMapper.insert(resvPayOrder);

        // 2.1 创建支付单
        Long payOrderId = payOrderApi.createOrder(new PayOrderCreateReqDTO()
                .setAppId(PAY_APP_ID).setUserIp(getClientIP()) // 支付应用
                .setMerchantOrderId(resvPayOrder.getOrderNo()) // 业务的订单编号
                .setSubject("场地预约费用").setBody("").setPrice(Integer.valueOf(createReqVO.getPayAmount().toString())) // 价格信息
                .setExpireTime(addTime( Duration.ofMinutes(10L)))).getCheckedData();

        resvPayOrder.setThirdOrderNo( String.valueOf( payOrderId ));
        resvPayOrderMapper.updateById( resvPayOrder );

        // 返回
        return payOrderId;
    }

    @Override
    public void updateResvPayOrder(ResvPayOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateResvPayOrderExists(updateReqVO.getId());
        // 更新
        ResvPayOrderDO updateObj = BeanUtils.toBean(updateReqVO, ResvPayOrderDO.class);
        resvPayOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteResvPayOrder(Long id) {
        // 校验存在
        validateResvPayOrderExists(id);
        // 删除
        resvPayOrderMapper.deleteById(id);
    }

    private void validateResvPayOrderExists(Long id) {
        if (resvPayOrderMapper.selectById(id) == null) {
            throw exception(RESV_PAY_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public ResvPayOrderDO getResvPayOrder(Long id) {
        return resvPayOrderMapper.selectById(id);
    }

    @Override
    public PageResult<ResvPayOrderDO> getResvPayOrderPage(ResvPayOrderPageReqVO pageReqVO) {
        return resvPayOrderMapper.selectPage(pageReqVO);
    }

    @Override
    public Long refundResvPayOrder(Long id) {
        // 1. 校验订单是否可以退款
        ResvPayOrderDO order = validateResvPayOrderCanRefund(id);
        //根据订单号查询相关业务
        ResvBookingDO resvBooking = resvBookingMapper.selectOne( new LambdaQueryWrapperX<ResvBookingDO>().eq( ResvBookingDO::getOrderNo, order.getOrderNo() ) );
        if(resvBooking == null){
            throw exception(RESV_BOOKING_NOT_EXISTS);
        }


        // 2.1 生成退款单号
        ResvBookingOrderRefundDO refundDO = new ResvBookingOrderRefundDO();
        refundDO.setAppSign( order.getAppSign() );
        refundDO.setVillageId( order.getVillageId() );
        refundDO.setBookingId( resvBooking.getId() );
        refundDO.setOrderId( order.getId() );
        refundDO.setAmount( order.getPayAmount() );
        refundDO.setUserId( resvBooking.getUserId() );
        refundDO.setOwnerId( resvBooking.getOwnerId() );
        refundDO.setOrderNo( "ref"+order.getOrderNo() );
        refundDO.setStatus( 0 );

        resvBookingOrderRefundMapper.insert(refundDO);
        return refundDO.getId();
    }

    private ResvPayOrderDO validateResvPayOrderCanRefund(Long id) {
        // 校验订单是否存在
        ResvPayOrderDO orderDO = resvPayOrderMapper.selectById( id );
        if (orderDO == null) {
            throw exception(RESV_PAY_ORDER_NOT_EXISTS);
        }
        // 校验订单是否支付
        if (orderDO.getPayStatus() == 0) {
            throw exception(RESV_PAY_ORDER_REF_EXISTS);
        }
        // 校验订单是否已退款
        LambdaQueryWrapperX<ResvBookingOrderRefundDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq( ResvBookingOrderRefundDO::getOrderId, orderDO.getId() );

        List<ResvBookingOrderRefundDO> refundDOS = resvBookingOrderRefundMapper.selectList( wrapper );
           if(CollUtil.isNotEmpty(refundDOS)){
               for (ResvBookingOrderRefundDO refundDO : refundDOS) {
                   if(refundDO.getStatus() != null){
                       switch(refundDO.getStatus()){
                           case 1:
                               throw new ServiceException(406,"当前订单已退款，请稍后查看");
                           case 0:
                               throw new ServiceException(406,"当前订单已提交退款申请，请勿重复提交");
                       }
                   }

               }
           }

        return orderDO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long processRefundResvPayOrder(ResvBookingOrderRefundSaveReqVO vo) {
        ResvBookingOrderRefundDO orderRefundDO = resvBookingOrderRefundMapper.selectById( vo.getId() );
        if(orderRefundDO == null){
            throw exception(RESV_BOOKING_ORDER_REFUND_NOT_EXISTS);
        }
        if(orderRefundDO.getStatus() != null && orderRefundDO.getStatus() != 0){
            throw exception(RESV_BOOKING_ORDER_REFUND_REF_EXISTS);
        }

        orderRefundDO.setActualAmount( vo.getActualAmount() );
        orderRefundDO.setStatus( vo.getStatus() );
        orderRefundDO.setHandler( SecurityFrameworkUtils.getLoginUserId() );
        orderRefundDO.setHandlerName( SecurityFrameworkUtils.getLoginUserNickname() );
        orderRefundDO.setHandleTime( LocalDateTime.now() );
        orderRefundDO.setRefundType( vo.getRefundType() );
        if(vo.getStatus() == 2){
            resvBookingOrderRefundMapper.updateById( orderRefundDO );
            return orderRefundDO.getId();
        }

        //查询支付单号
        ResvBookingDO bookingDO = resvBookingMapper.selectById( orderRefundDO.getBookingId() );

        // 2.2 创建退款单
        Long payRefundId = payRefundApi.createRefund(new PayRefundCreateReqDTO()
                .setAppId(PAY_APP_ID).setUserIp(getClientIP()) // 支付应用
                .setMerchantOrderId(String.valueOf(bookingDO.getOrderNo())) // 支付单号
                .setMerchantRefundId(String.valueOf( orderRefundDO.getId() ))
                .setReason("退款").setPrice(Integer.valueOf(String.valueOf(orderRefundDO.getActualAmount())))).getCheckedData();// 价格信息
        // 2.3 更新退款单到退款订单
        resvBookingOrderRefundMapper.updateById(orderRefundDO.setOrderNo(String.valueOf(payRefundId)));

        return vo.getId();
    }

    @Override
    public void updateResvPayOrderPaid(Long orderNo, Long payOrderId) {
        // 校验并获得支付订单（可支付）
        PayOrderRespDTO payOrder = validateDemoOrderCanPaid(orderNo, payOrderId);
        if(payOrder == null){
            return;
        }
        //查询预约单
        ResvBookingDO order = resvBookingMapper.selectOne( new LambdaQueryWrapperX<ResvBookingDO>().eq( ResvBookingDO::getOrderNo, orderNo ) );

        //生成核销表
        ResvBookingVerificationDO booking =  new ResvBookingVerificationDO();
        booking.setAppSign(order.getAppSign());
        booking.setVillageId( order.getVillageId() );
        booking.setOrderNo( order.getOrderNo() );
        booking.setBookingId(order.getId());
        booking.setStatus( 0 );
        resvBookingVerificationMapper.insert( booking );

        //生成核销码
        byte[] bytes = QRCodeUtils.generateQRCode( booking.getId().toString(), 350, 350 );
        String file = fileApi.createFile( bytes );
        booking.setCode( file );
        resvBookingVerificationMapper.updateById( booking );

        //更新booking,booking_order,pay_order状态
        resvBookingMapper.update(null, new LambdaUpdateWrapper<ResvBookingDO>().eq( ResvBookingDO::getOrderNo, orderNo ).set( ResvBookingDO::getStatus, 1 ));
        resvBookingOrderMapper.update(null, new LambdaUpdateWrapper<ResvBookingOrderDO>()
                .eq(ResvBookingOrderDO::getOrderNo, orderNo)
                .set(ResvBookingOrderDO::getPayStatus, 1)
                .set( ResvBookingOrderDO::getPayTime,payOrder.getSuccessTime()));
        resvPayOrderMapper.update(null, new LambdaUpdateWrapper<ResvPayOrderDO>()
                .eq(ResvPayOrderDO::getOrderNo, orderNo)
                .set(ResvPayOrderDO::getPayStatus, 1)
                .set( ResvPayOrderDO::getPayTime,payOrder.getSuccessTime()));

    }

    /**
     * 校验交易订单满足被支付的条件
     *
     * 1. 交易订单未支付
     * 2. 支付单已支付
     *
     * @param orderNo 交易订单编号
     * @param payOrderId 支付订单编号
     * @return 交易订单
     */
    private PayOrderRespDTO validateDemoOrderCanPaid(Long orderNo, Long payOrderId) {
        // 1.1 校验订单是否存在
        ResvPayOrderDO order = resvPayOrderMapper.selectOne(new LambdaQueryWrapperX<ResvPayOrderDO>().eq( ResvPayOrderDO::getOrderNo, orderNo ));
        if (order == null) {
            throw exception(DEMO_ORDER_NOT_FOUND);
        }
        // 1.2 校验订单未支付
        if (order.getPayStatus() == 1) {
            log.error("[validateDemoOrderCanPaid][order({}) 不处于待支付状态，请进行处理！order 数据是：{}]",
                    orderNo, toJsonString(order));
            throw exception(DEMO_ORDER_UPDATE_PAID_STATUS_NOT_UNPAID);
        }
        // 1.3 校验支付订单匹配
        if (!StringUtils.equals(order.getThirdOrderNo(), payOrderId.toString())) { // 支付单号
            log.error("[validateDemoOrderCanPaid][order({}) 支付单不匹配({})，请进行处理！order 数据是：{}]",
                    orderNo, payOrderId, toJsonString(order));
            throw exception(DEMO_ORDER_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR);
        }

        // 2.1 校验支付单是否存在
        PayOrderRespDTO payOrder = payOrderApi.getOrder(payOrderId).getCheckedData();
        log.info("[validateDemoOrderCanPaid][order({}) payOrder({}) 获取成功，请进行处理！payOrder 数据是：{}]", orderNo, payOrderId, toJsonString(payOrder));

        if (payOrder == null) {
            log.error("[validateDemoOrderCanPaid][order({}) payOrder({}) 不存在，请进行处理！]", orderNo, payOrderId);
            throw exception(PAY_ORDER_NOT_FOUND);
        }
        // 2.2 校验支付单已支付
        if (!PayOrderStatusEnum.isSuccess(payOrder.getStatus())) {
            log.error("[validateDemoOrderCanPaid][order({}) payOrder({}) 未支付，请进行处理！payOrder 数据是：{}]",
                    orderNo, payOrderId, toJsonString(payOrder));
            if(PayOrderStatusEnum.isClosed(payOrder.getStatus())){
                //支付关闭
                resvBookingMapper.update(null, new LambdaUpdateWrapper<ResvBookingDO>().eq( ResvBookingDO::getOrderNo, orderNo ).set( ResvBookingDO::getStatus, 3 ));
                resvBookingOrderMapper.update(null, new LambdaUpdateWrapper<ResvBookingOrderDO>()
                        .eq(ResvBookingOrderDO::getOrderNo, orderNo)
                        .set(ResvBookingOrderDO::getPayStatus, 3)
                        .set(ResvBookingOrderDO::getOrderStatus, 3)
                        .set( ResvBookingOrderDO::getPayTime,payOrder.getSuccessTime()));
                resvPayOrderMapper.update(null, new LambdaUpdateWrapper<ResvPayOrderDO>()
                        .eq(ResvPayOrderDO::getOrderNo, orderNo)
                        .set(ResvPayOrderDO::getPayStatus, 2)
                        .set( ResvPayOrderDO::getPayTime,payOrder.getSuccessTime()));
                return null;
            }
            throw exception(DEMO_ORDER_UPDATE_PAID_FAIL_PAY_ORDER_STATUS_NOT_SUCCESS);
        }
        // 2.3 校验支付金额一致
        if (order.getPayAmount().compareTo( new BigDecimal( payOrder.getPrice() ) ) != 0) {
            log.error("[validateDemoOrderCanPaid][order({}) payOrder({}) 支付金额不匹配，请进行处理！order 数据是：{}，payOrder 数据是：{}]",
                    orderNo, payOrderId, toJsonString(order), toJsonString(payOrder));
            throw exception(DEMO_ORDER_UPDATE_PAID_FAIL_PAY_PRICE_NOT_MATCH);
        }
        // 2.4 校验支付订单匹配（二次）
        if (!StringUtils.equals(payOrder.getMerchantOrderId(), order.getOrderNo())) {
            log.error("[validateDemoOrderCanPaid][order({}) 支付单不匹配({})，请进行处理！payOrder 数据是：{}]",
                    orderNo, payOrderId, toJsonString(payOrder));
            throw exception(DEMO_ORDER_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR);
        }
        return payOrder;
    }

    @Override
    public void updateResvPayOrderRefunded(Long refundId, Long payRefundId) {
        // 1. 校验并获得退款订单（可退款）
        PayRefundRespDTO payRefund = validateDemoOrderCanRefunded(refundId, payRefundId);
        // 2.2 更新booking,booking_order,pay_order状态，如果已生成核销码则进行更新
        ResvBookingOrderRefundDO order = resvBookingOrderRefundMapper.selectById( refundId );
        ResvBookingDO bookingDO = resvBookingMapper.selectById( order.getBookingId() );
        String orderNo = bookingDO.getOrderNo();
        //更新booking,booking_order,pay_order状态
        resvBookingMapper.update(null, new LambdaUpdateWrapper<ResvBookingDO>().eq( ResvBookingDO::getOrderNo, orderNo ).set( ResvBookingDO::getStatus, 4 ));
        resvBookingOrderMapper.update(null, new LambdaUpdateWrapper<ResvBookingOrderDO>()
                .eq(ResvBookingOrderDO::getOrderNo, orderNo)
                .set(ResvBookingOrderDO::getPayStatus, 2)
                .set(ResvBookingOrderDO::getOrderStatus, 3)
                .set( ResvBookingOrderDO::getRefundAmount,payRefund.getRefundPrice()));
        resvBookingVerificationMapper.update( null, new LambdaUpdateWrapper<ResvBookingVerificationDO>().eq( ResvBookingVerificationDO::getOrderNo, orderNo).set( ResvBookingVerificationDO::getDeleted, 1 ) );

    }
    private PayRefundRespDTO validateDemoOrderCanRefunded(Long refundId, Long payRefundId) {
        // 1.1 校验示例订单
        ResvBookingOrderRefundDO order = resvBookingOrderRefundMapper.selectById( refundId );
        if (order == null) {
            throw exception(DEMO_ORDER_NOT_FOUND);
        }
        // 1.2 校验退款订单匹配
        if (!StringUtils.equals(order.getOrderNo(), payRefundId.toString())) {
            log.error("[validateDemoOrderCanRefunded][order({}) 退款单不匹配({})，请进行处理！order 数据是：{}]",
                    refundId, payRefundId, toJsonString(order));
            throw exception(DEMO_ORDER_REFUND_FAIL_REFUND_ORDER_ID_ERROR);
        }

        // 2.1 校验退款订单
        PayRefundRespDTO payRefund = payRefundApi.getRefund(payRefundId).getCheckedData();
        if (payRefund == null) {
            throw exception(DEMO_ORDER_REFUND_FAIL_REFUND_NOT_FOUND);
        }
        // 2.2
        if (!PayRefundStatusEnum.isSuccess(payRefund.getStatus())) {
            throw exception(DEMO_ORDER_REFUND_FAIL_REFUND_NOT_SUCCESS);
        }
        // 2.3 校验退款金额一致
        if (order.getActualAmount().compareTo( new BigDecimal(payRefund.getRefundPrice().toString()) ) != 0) {
            log.error("[validateDemoOrderCanRefunded][order({}) payRefund({}) 退款金额不匹配，请进行处理！order 数据是：{}，payRefund 数据是：{}]",
                    refundId, payRefundId, toJsonString(order), toJsonString(payRefund));
            throw exception(DEMO_ORDER_REFUND_FAIL_REFUND_PRICE_NOT_MATCH);
        }
        return payRefund;
    }

    @Override
    public void cancelResvPayOrder(Long id) {
        ResvPayOrderDO orderDO = resvPayOrderMapper.selectById( id );
        if(orderDO == null){
            throw exception(DEMO_ORDER_NOT_FOUND);
        }

        resvPayOrderMapper.update( null, new LambdaUpdateWrapper<ResvPayOrderDO>().eq( ResvPayOrderDO::getId, id ).set( ResvPayOrderDO::getPayStatus, 2) );
        resvBookingMapper.update( null, new LambdaUpdateWrapper<ResvBookingDO>().eq( ResvBookingDO::getOrderNo, orderDO.getOrderNo() ).set( ResvBookingDO::getStatus, 3) );
        resvBookingOrderMapper.update( null, new LambdaUpdateWrapper<ResvBookingOrderDO>().eq( ResvBookingOrderDO::getOrderNo, orderDO.getOrderNo() ).set( ResvBookingOrderDO::getOrderStatus, 3) );
    }

    @Override
    public ResvBookingDetilVO findOneBookingDetil(Long id) {
        ResvBookingDO resvBookingDO = resvBookingMapper.selectById( id );
        if(resvBookingDO == null){
            return null;
        }

        ResvBookingDetilVO vo = BeanUtils.toBean( resvBookingDO, ResvBookingDetilVO.class );
        //查询场地
        ResvPlaceDO resvPlaceDO = resvPlaceMapper.selectById( resvBookingDO.getPlaceId() );
        vo.setResvPlace( resvPlaceDO );
        //查询场地分类
        ResvPlaceCategoryDO resvPlaceCategoryDO = resvPlaceCategoryMapper.selectById( resvPlaceDO.getCategoryId() );
        vo.setResvPlaceCategory( resvPlaceCategoryDO );
        //查询订单
        if(StringUtils.isNotEmpty( resvBookingDO.getOrderNo() )){
            ResvBookingOrderDO resvBookingOrderDO = resvBookingOrderMapper.selectOne( new LambdaQueryWrapper<ResvBookingOrderDO>().eq( ResvBookingOrderDO::getOrderNo, vo.getOrderNo() ) );
            vo.setResvBookingOrder( resvBookingOrderDO );

            //查询支付订单表
            ResvPayOrderDO resvPayOrderDO = resvPayOrderMapper.selectOne( new LambdaQueryWrapper<ResvPayOrderDO>().eq( ResvPayOrderDO::getOrderNo, vo.getOrderNo() ) );
            if(resvPayOrderDO != null){
                vo.setResvPayOrder( resvPayOrderDO );
            }
            //查询核销表
            ResvBookingVerificationDO resvBookingVerificationDO = resvBookingVerificationMapper.selectOne( new LambdaQueryWrapper<ResvBookingVerificationDO>().eq( ResvBookingVerificationDO::getOrderNo, vo.getOrderNo() ) );
            if(resvBookingVerificationDO != null){
                vo.setResvBookingVerification( resvBookingVerificationDO );
            }
        }

        return vo;
    }
}