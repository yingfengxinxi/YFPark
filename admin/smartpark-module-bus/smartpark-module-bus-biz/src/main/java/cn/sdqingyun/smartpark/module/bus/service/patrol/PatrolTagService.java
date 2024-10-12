package cn.sdqingyun.smartpark.module.bus.service.patrol;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolTagPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo.PatrolTagSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol.PatrolTagDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 巡检标签模板 Service 接口
 *
 * @author 智慧园区
 */
public interface PatrolTagService {

    /**
     * 创建巡检标签模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid PatrolTagSaveReqVO createReqVO);

    /**
     * 更新巡检标签模板
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid PatrolTagSaveReqVO updateReqVO);

    /**
     * 删除巡检标签模板
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得巡检标签模板
     *
     * @param id 编号
     * @return 巡检标签模板
     */
    PatrolTagDO get(Long id);

    /**
     * 获得巡检标签模板
     *
     * @param application
     * @return
     */
    List<PatrolTagDO> getList(String application);

}