package cn.sdqingyun.smartpark.module.bus.service.patrol;

import java.text.ParseException;
import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolPlanEquipmentPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolPlanEquipmentRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolPlanEquipmentSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolPlanEquipmentDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 应用巡检计划 Service 接口
 *
 * @author 智慧园区
 */
public interface PatrolPlanEquipmentService {

    /**
     * 创建应用巡检计划
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid PatrolPlanEquipmentSaveReqVO createReqVO) throws Exception;

    /**
     * 更新应用巡检计划
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid PatrolPlanEquipmentSaveReqVO updateReqVO) throws Exception;

    /**
     * 删除应用巡检计划
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得应用巡检计划
     *
     * @param id 编号
     * @return 应用巡检计划
     */
    PatrolPlanEquipmentRespVO get(Long id);

    /**
     * 获得应用巡检计划分页
     *
     * @param pageReqVO 分页查询
     * @return 应用巡检计划分页
     */
    PageResult<PatrolPlanEquipmentRespVO> getPage(PatrolPlanEquipmentPageReqVO pageReqVO);

    /**
     *
     * @param id
     * @param status
     */
    void updateStatus(Long id, String status);

    /**
     *
     * @param application
     * @return
     */
    Map<String, Object> getTopCount(String application);
}