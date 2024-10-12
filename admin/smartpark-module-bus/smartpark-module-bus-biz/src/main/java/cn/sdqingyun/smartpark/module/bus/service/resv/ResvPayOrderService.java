package cn.sdqingyun.smartpark.module.bus.service.resv;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingDetilVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingOrderRefundSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPayOrderPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvPayOrderSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvPayOrderDO;
import jakarta.validation.Valid;

/**
 * 订单支付 Service 接口
 *
 * @author 智慧园区
 */
public interface ResvPayOrderService {

    /**
     * 创建订单支付
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createResvPayOrder(@Valid ResvPayOrderSaveReqVO createReqVO);

    /**
     * 更新订单支付
     *
     * @param updateReqVO 更新信息
     */
    void updateResvPayOrder(@Valid ResvPayOrderSaveReqVO updateReqVO);

    /**
     * 删除订单支付
     *
     * @param id 编号
     */
    void deleteResvPayOrder(Long id);

    /**
     * 获得订单支付
     *
     * @param id 编号
     * @return 订单支付
     */
    ResvPayOrderDO getResvPayOrder(Long id);

    /**
     * 获得订单支付分页
     *
     * @param pageReqVO 分页查询
     * @return 订单支付分页
     */
    PageResult<ResvPayOrderDO> getResvPayOrderPage(ResvPayOrderPageReqVO pageReqVO);

    /**
    * @Author SUNk
    * @Description 场地预约订单退款
    * @Date 11:22 2024/8/5
    * @Param [id]
    * @return void
    **/
    Long refundResvPayOrder(Long id);

    /**
    * @Author SUNk
    * @Description 管理员审核场地订单的退款，创建退款订单
    * @Date 16:48 2024/8/5
    * @Param [vo]
    * @return java.lang.Long
    **/
    Long processRefundResvPayOrder(ResvBookingOrderRefundSaveReqVO vo);

    /**
     * 更新示例订单为已支付
     *
     * @param orderNo 编号
     * @param payOrderId 支付订单号
     */
    void updateResvPayOrderPaid(Long orderNo, Long payOrderId);

    /**
     * 更新示例订单为已退款
     *
     * @param id 编号
     * @param payRefundId 退款订单号
     */
    void updateResvPayOrderRefunded(Long id, Long payRefundId);

    /**
    * @Author SUNk
    * @Description 取消订单、
    * @Date 20:46 2024/8/5
    * @Param [id]
    * @return void
    **/
    void cancelResvPayOrder(Long id);

    /**
     * @Author SUNk
     * @Description 获得预约详情
     * @Date 9:03 2024/7/30
     * @Param [id]
     * @return cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingDetilVO
     **/
    ResvBookingDetilVO findOneBookingDetil(Long id);
}