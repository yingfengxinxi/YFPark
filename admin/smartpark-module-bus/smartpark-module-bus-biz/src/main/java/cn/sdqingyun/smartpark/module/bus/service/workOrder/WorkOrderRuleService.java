package cn.sdqingyun.smartpark.module.bus.service.workOrder;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderRulePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderRuleRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo.WorkOrderRuleSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder.WorkOrderRuleDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 工单规则设置 Service 接口
 *
 * @author 管理员
 */
public interface WorkOrderRuleService {

    /**
     * 创建工单规则设置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid WorkOrderRuleSaveReqVO createReqVO);

    /**
     * 更新工单规则设置
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid WorkOrderRuleSaveReqVO updateReqVO);

    /**
     * 删除工单规则设置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得工单规则设置
     *
     * @param id 编号
     * @return 工单规则设置
     */
    WorkOrderRuleDO get(Long id);

    /**
     * 获得工单规则设置分页
     *
     * @param pageReqVO 分页查询
     * @return 工单规则设置分页
     */
    PageResult<WorkOrderRuleRespVO> getPage(WorkOrderRulePageReqVO pageReqVO);

}