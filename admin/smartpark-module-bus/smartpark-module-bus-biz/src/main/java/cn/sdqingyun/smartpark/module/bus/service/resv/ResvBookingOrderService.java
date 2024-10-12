package cn.sdqingyun.smartpark.module.bus.service.resv;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvBookingOrderDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 预约订单 Service 接口
 *
 * @author 智慧园区
 */
public interface ResvBookingOrderService {

    /**
     * 创建预约订单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createResvBookingOrder(@Valid ResvBookingOrderSaveReqVO createReqVO);

    /**
     * 更新预约订单
     *
     * @param updateReqVO 更新信息
     */
    void updateResvBookingOrder(@Valid ResvBookingOrderSaveReqVO updateReqVO);

    /**
     * 删除预约订单
     *
     * @param id 编号
     */
    void deleteResvBookingOrder(Long id);

    /**
     * 获得预约订单
     *
     * @param id 编号
     * @return 预约订单
     */
    ResvBookingOrderDO getResvBookingOrder(Long id);

    /**
     * 获得预约订单分页
     *
     * @param pageReqVO 分页查询
     * @return 预约订单分页
     */
    PageResult<ResvBookingOrderDO> getResvBookingOrderPage(ResvBookingOrderPageReqVO pageReqVO);

}