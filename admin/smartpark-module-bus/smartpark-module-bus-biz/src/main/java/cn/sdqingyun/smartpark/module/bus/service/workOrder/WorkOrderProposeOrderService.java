package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import java.math.BigDecimal;
import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.OrderRecordDetailVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderProposeOrderPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderProposeOrderRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderProposeOrderSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeOrderDO;
import com.alibaba.fastjson.JSONObject;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 付费工单订单 Service 接口
 *
 * @author 管理员
 */
public interface WorkOrderProposeOrderService {

    /**
     * 创建付费工单订单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid WorkOrderProposeOrderSaveReqVO createReqVO);

    /**
     * 更新付费工单订单
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid WorkOrderProposeOrderSaveReqVO updateReqVO);

    /**
     * 删除付费工单订单
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得付费工单订单
     *
     * @param id 编号
     * @return 付费工单订单
     */
    WorkOrderProposeOrderDO get(Long id);

    /**
     *
     * @param id
     * @return
     */
    OrderRecordDetailVO orderRecordDetail(Long id);

    /**
     * 获得付费工单订单分页
     *
     * @param pageReqVO 分页查询
     * @return 付费工单订单分页
     */
    PageResult<WorkOrderProposeOrderRespVO> getPage(WorkOrderProposeOrderPageReqVO pageReqVO);

    /**
     *
     * @param pageReqVO
     * @return
     */
    List<JSONObject> getOrderStatistics(WorkOrderProposeOrderPageReqVO pageReqVO);

    /**
     *
     * @param ids
     * @return
     */
    String scanCodeBillPay(String ids);

    /**
     *
     * @param id
     * @param refundStatus
     * @param refundAmount
     */
    void orderRefund(String id, String refundStatus, BigDecimal refundAmount);
}