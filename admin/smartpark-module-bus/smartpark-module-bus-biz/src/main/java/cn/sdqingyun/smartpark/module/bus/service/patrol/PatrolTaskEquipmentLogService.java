package cn.sdqingyun.smartpark.module.bus.service.patrol;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolTaskEquipmentLogPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolTaskEquipmentLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTaskEquipmentLogDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 应用巡检任务日志 Service 接口
 *
 * @author 智慧园区
 */
public interface PatrolTaskEquipmentLogService {

    /**
     * 创建应用巡检任务日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid PatrolTaskEquipmentLogSaveReqVO createReqVO);

    /**
     * 更新应用巡检任务日志
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid PatrolTaskEquipmentLogSaveReqVO updateReqVO);

    /**
     * 删除应用巡检任务日志
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得应用巡检任务日志
     *
     * @param id 编号
     * @return 应用巡检任务日志
     */
    PatrolTaskEquipmentLogDO get(Long id);

    /**
     * 获得应用巡检任务日志分页
     *
     * @param pageReqVO 分页查询
     * @return 应用巡检任务日志分页
     */
    PageResult<PatrolTaskEquipmentLogDO> getPage(PatrolTaskEquipmentLogPageReqVO pageReqVO);

}