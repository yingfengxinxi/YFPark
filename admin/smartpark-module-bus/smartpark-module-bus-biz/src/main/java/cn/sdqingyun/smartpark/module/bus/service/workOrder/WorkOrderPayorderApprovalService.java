package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderPayorderApprovalPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderPayorderApprovalSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderPayorderApprovalDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 工单付款单审批记录 Service 接口
 *
 * @author 管理员
 */
public interface WorkOrderPayorderApprovalService {

    /**
     * 创建工单付款单审批记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid WorkOrderPayorderApprovalSaveReqVO createReqVO);

    /**
     * 更新工单付款单审批记录
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid WorkOrderPayorderApprovalSaveReqVO updateReqVO);

    /**
     * 删除工单付款单审批记录
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得工单付款单审批记录
     *
     * @param id 编号
     * @return 工单付款单审批记录
     */
    WorkOrderPayorderApprovalDO get(Long id);

    /**
     * 获得工单付款单审批记录分页
     *
     * @param pageReqVO 分页查询
     * @return 工单付款单审批记录分页
     */
    PageResult<WorkOrderPayorderApprovalDO> getPage(WorkOrderPayorderApprovalPageReqVO pageReqVO);

}