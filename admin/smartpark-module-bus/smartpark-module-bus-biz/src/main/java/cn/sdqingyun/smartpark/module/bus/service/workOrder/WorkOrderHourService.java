package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderHourPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderHourRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderHourSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderHourDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 工单工时配置 Service 接口
 *
 * @author 管理员
 */
public interface WorkOrderHourService {

    /**
     * 创建工单工时配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid WorkOrderHourSaveReqVO createReqVO);

    /**
     * 更新工单工时配置
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid WorkOrderHourSaveReqVO updateReqVO);

    /**
     *
     * @param id
     * @param status
     */
    void updateStatus(Long id,String status);

    /**
     * 删除工单工时配置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得工单工时配置
     *
     * @param id 编号
     * @return 工单工时配置
     */
    WorkOrderHourDO get(Long id);

    /**
     * 获得工单工时配置分页
     *
     * @param pageReqVO 分页查询
     * @return 工单工时配置分页
     */
    PageResult<WorkOrderHourRespVO> getPage(WorkOrderHourPageReqVO pageReqVO);

    /**
     *
     * @param application
     * @return
     */
    List<WorkOrderHourDO> workHourList(String application);

}