package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderPaymentOrderPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderPaymentOrderSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderPaymentOrderDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 付款单记录 Service 接口
 *
 * @author 管理员
 */
public interface WorkOrderPaymentOrderService {

    /**
     * 创建付款单记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid WorkOrderPaymentOrderSaveReqVO createReqVO);

    /**
     * 更新付款单记录
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid WorkOrderPaymentOrderSaveReqVO updateReqVO);

    /**
     * 删除付款单记录
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得付款单记录
     *
     * @param id 编号
     * @return 付款单记录
     */
    WorkOrderPaymentOrderDO get(Long id);

    /**
     * 获得付款单记录分页
     *
     * @param pageReqVO 分页查询
     * @return 付款单记录分页
     */
    PageResult<WorkOrderPaymentOrderDO> getPage(WorkOrderPaymentOrderPageReqVO pageReqVO);

}