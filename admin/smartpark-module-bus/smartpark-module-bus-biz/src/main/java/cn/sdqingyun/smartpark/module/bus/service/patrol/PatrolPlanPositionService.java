package cn.sdqingyun.smartpark.module.bus.service.patrol;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolPlanPositionPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolPlanPositionSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolPlanPositionDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 应用巡检计划绑定巡检点 Service 接口
 *
 * @author 智慧园区
 */
public interface PatrolPlanPositionService {

    /**
     * 创建应用巡检计划绑定巡检点
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid PatrolPlanPositionSaveReqVO createReqVO);

    /**
     * 更新应用巡检计划绑定巡检点
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid PatrolPlanPositionSaveReqVO updateReqVO);

    /**
     * 删除应用巡检计划绑定巡检点
     *
     * @param planId 计划编号
     */
    void delete(Long planId);

    /**
     * 获得应用巡检计划绑定巡检点
     *
     * @param id 编号
     * @return 应用巡检计划绑定巡检点
     */
    PatrolPlanPositionDO get(Long id);

    /**
     * 获得应用巡检计划绑定巡检点分页
     *
     * @param pageReqVO 分页查询
     * @return 应用巡检计划绑定巡检点分页
     */
    List<PatrolPlanPositionSaveReqVO> getList(PatrolPlanPositionPageReqVO pageReqVO);

}