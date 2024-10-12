package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderAdminPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderAdminRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderAdminSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderAdminDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 工单应用管理员信息 Service 接口
 *
 * @author 管理员
 */
public interface WorkOrderAdminService {

    /**
     * 创建工单应用管理员信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid WorkOrderAdminSaveReqVO createReqVO);

    /**
     * 更新工单应用管理员信息
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid WorkOrderAdminSaveReqVO updateReqVO);

    /**
     * 删除工单应用管理员信息
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得工单应用管理员信息
     *
     * @param id 编号
     * @return 工单应用管理员信息
     */
    WorkOrderAdminDO get(Long id);

    /**
     * 获得工单应用管理员信息分页
     *
     * @param pageReqVO 分页查询
     * @return 工单应用管理员信息分页
     */
    PageResult<WorkOrderAdminRespVO> getPage(WorkOrderAdminPageReqVO pageReqVO);

}