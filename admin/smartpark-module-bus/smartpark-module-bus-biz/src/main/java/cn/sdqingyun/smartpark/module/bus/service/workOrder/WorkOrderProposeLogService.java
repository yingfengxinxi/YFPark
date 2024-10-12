package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderProposeLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderProposeLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderProposeLogDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 工单操作日志 Service 接口
 *
 * @author 管理员
 */
public interface WorkOrderProposeLogService {

    /**
     * 创建工单操作日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid WorkOrderProposeLogSaveReqVO createReqVO);

    /**
     * 更新工单操作日志
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid WorkOrderProposeLogSaveReqVO updateReqVO);

    /**
     * 删除工单操作日志
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得工单操作日志
     *
     * @param id 编号
     * @return 工单操作日志
     */
    WorkOrderProposeLogDO get(Long id);

    /**
     * 获得工单操作日志分页
     *
     * @param pageReqVO 分页查询
     * @return 工单操作日志分页
     */
    PageResult<WorkOrderProposeLogDO> getPage(WorkOrderProposeLogPageReqVO pageReqVO);

}