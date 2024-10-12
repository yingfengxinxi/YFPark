package cn.sdqingyun.smartpark.module.bus.service.patrol;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.TaskUserLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.TaskUserLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.TaskUserLogDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 应用巡检任务变更执行人日志 Service 接口
 *
 * @author 管理员
 */
public interface TaskUserLogService {

    /**
     * 创建应用巡检任务变更执行人日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid TaskUserLogSaveReqVO createReqVO);

    /**
     * 更新应用巡检任务变更执行人日志
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid TaskUserLogSaveReqVO updateReqVO);

    /**
     * 删除应用巡检任务变更执行人日志
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得应用巡检任务变更执行人日志
     *
     * @param id 编号
     * @return 应用巡检任务变更执行人日志
     */
    TaskUserLogDO get(Long id);

    /**
     * 获得应用巡检任务变更执行人日志分页
     *
     * @param pageReqVO 分页查询
     * @return 应用巡检任务变更执行人日志分页
     */
    PageResult<TaskUserLogDO> getPage(TaskUserLogPageReqVO pageReqVO);

}