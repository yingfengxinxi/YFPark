package cn.sdqingyun.smartpark.module.bus.service.patrol;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolPositionPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolPositionSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolPositionDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 巡检点位数据 Service 接口
 *
 * @author 智慧园区
 */
public interface PatrolPositionService {

    /**
     * 创建巡检点位数据
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid PatrolPositionSaveReqVO createReqVO);

    /**
     * 更新巡检点位数据
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid PatrolPositionSaveReqVO updateReqVO);

    /**
     * 删除巡检点位数据
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得巡检点位数据
     *
     * @param id 编号
     * @return 巡检点位数据
     */
    PatrolPositionDO get(Long id);

    /**
     * 获得巡检点位数据分页
     *
     * @param pageReqVO 分页查询
     * @return 巡检点位数据分页
     */
    PageResult<PatrolPositionDO> getPage(PatrolPositionPageReqVO pageReqVO);

}