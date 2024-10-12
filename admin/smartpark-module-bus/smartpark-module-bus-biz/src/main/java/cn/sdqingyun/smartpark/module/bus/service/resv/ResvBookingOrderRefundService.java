package cn.sdqingyun.smartpark.module.bus.service.resv;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingOrderRefundPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingOrderRefundRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.ResvBookingOrderRefundSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvBookingOrderRefundDO;
import jakarta.validation.Valid;

/**
 * 预约订单退款 Service 接口
 *
 * @author 智慧园区
 */
public interface ResvBookingOrderRefundService {

    /**
     * 创建预约订单退款
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createResvBookingOrderRefund(@Valid ResvBookingOrderRefundSaveReqVO createReqVO);

    /**
     * 更新预约订单退款
     *
     * @param updateReqVO 更新信息
     */
    void updateResvBookingOrderRefund(@Valid ResvBookingOrderRefundSaveReqVO updateReqVO);

    /**
     * 删除预约订单退款
     *
     * @param id 编号
     */
    void deleteResvBookingOrderRefund(Long id);

    /**
     * 获得预约订单退款
     *
     * @param id 编号
     * @return 预约订单退款
     */
    ResvBookingOrderRefundDO getResvBookingOrderRefund(Long id);

    /**
     * 获得预约订单退款分页
     *
     * @param pageReqVO 分页查询
     * @return 预约订单退款分页
     */
    PageResult<ResvBookingOrderRefundDO> getResvBookingOrderRefundPage(ResvBookingOrderRefundPageReqVO pageReqVO);

    /**
     * 获得预约订单退款分页VO
     *
     * @param pageReqVO 分页查询
     * @return 预约订单退款分页
     */
    PageResult<ResvBookingOrderRefundRespVO> getResvBookingOrderRefundPageVO(ResvBookingOrderRefundPageReqVO pageReqVO);

}