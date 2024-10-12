package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ZoneDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 项目分区 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface ZoneService {

    /**
     * 创建项目分区
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createZone(@Valid ZoneSaveReqVO createReqVO);

    /**
     * 更新项目分区
     *
     * @param updateReqVO 更新信息
     */
    void updateZone(@Valid ZoneSaveReqVO updateReqVO);

    /**
     * 删除项目分区
     *
     * @param id 编号
     */
    void deleteZone(Long id);

    /**
     * 获得项目分区
     *
     * @param id 编号
     * @return 项目分区
     */
    ZoneDO getZone(Long id);

    /**
     * 获得项目分区分页
     *
     * @param pageReqVO 分页查询
     * @return 项目分区分页
     */
    PageResult<ZoneDO> getZonePage(ZonePageReqVO pageReqVO);

}