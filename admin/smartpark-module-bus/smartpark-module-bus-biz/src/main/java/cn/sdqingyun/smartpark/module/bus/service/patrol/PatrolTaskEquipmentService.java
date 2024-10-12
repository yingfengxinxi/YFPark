package cn.sdqingyun.smartpark.module.bus.service.patrol;

import java.util.*;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolTaskEquipmentPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolTaskEquipmentRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolTaskEquipmentSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.TaskUserLogSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTaskEquipmentDO;
import cn.sdqingyun.smartpark.module.system.api.user.dto.AdminUserRespDTO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 应用巡检任务 Service 接口
 *
 * @author 智慧园区
 */
public interface PatrolTaskEquipmentService {

    /**
     * 创建应用巡检任务
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid PatrolTaskEquipmentSaveReqVO createReqVO);

    /**
     * 更新应用巡检任务
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid PatrolTaskEquipmentSaveReqVO updateReqVO);

    /**
     * 删除应用巡检任务
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得应用巡检任务
     *
     * @param id 编号
     * @return 应用巡检任务
     */
    PatrolTaskEquipmentDO get(Long id);

    /**
     * 获得应用巡检任务分页
     *
     * @param pageReqVO 分页查询
     * @return 应用巡检任务分页
     */
    PageResult<PatrolTaskEquipmentRespVO> getPage(PatrolTaskEquipmentPageReqVO pageReqVO);

    /**
     *
     * @return
     */
    Map<String, Long> getTopStatistics(String application);

    /**
     *
     * @param id
     * @return
     */
    List<AdminUserRespDTO> getTaskPost(Long id);

    /**
     *
     * @param createReqVo
     * @return
     */
    Boolean saveTaskPost(TaskUserLogSaveReqVO createReqVo);

    /**
     *
     * @param application
     * @return
     */
    Map<String,Object> patrolTaskStaticTop(String application);

    /**
     *
     * @param startDate
     * @param endDate
     * @param application
     * @return
     */
    List<JSONObject> patrolTaskStatic(String startDate,String endDate,String application);

    /**
     *
     * @param startDate
     * @param endDate
     * @param positionId
     * @param application
     * @return
     */
    JSONObject patrolPositionStatic(String startDate, String endDate, Long positionId,String application);
}