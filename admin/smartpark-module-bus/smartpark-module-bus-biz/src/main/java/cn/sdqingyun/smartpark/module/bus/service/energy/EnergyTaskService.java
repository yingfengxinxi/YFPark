package cn.sdqingyun.smartpark.module.bus.service.energy;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyNotCompleteTaskListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyTaskPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo.EnergyTaskSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy.EnergyTaskDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 自定义抄表任务 Service 接口
 *
 * @author 管理员
 */
public interface EnergyTaskService {

    /**
     * 创建自定义抄表任务
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid EnergyTaskSaveReqVO createReqVO);

    /**
     * 更新自定义抄表任务
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid EnergyTaskSaveReqVO updateReqVO);

    /**
     * 删除自定义抄表任务
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得自定义抄表任务
     *
     * @param id 编号
     * @return 自定义抄表任务
     */
    EnergyTaskDO get(Long id);

    /**
     * 获得自定义抄表任务分页
     *
     * @param pageReqVO 分页查询
     * @return 自定义抄表任务分页
     */
    PageResult<EnergyTaskDO> getPage(EnergyTaskPageReqVO pageReqVO);

    /**
     *
     * @param planId
     * @return
     */
    List<EnergyNotCompleteTaskListVO> notCompleteTaskList(Long planId);
}