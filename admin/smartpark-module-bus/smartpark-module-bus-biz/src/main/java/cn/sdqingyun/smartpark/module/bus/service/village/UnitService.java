package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UnitDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 项目单元 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface UnitService {

    /**
     * 创建项目单元
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUnit(@Valid UnitSaveReqVO createReqVO);

    /**
     * 更新项目单元
     *
     * @param updateReqVO 更新信息
     */
    void updateUnit(@Valid UnitSaveReqVO updateReqVO);

    /**
     * 删除项目单元
     *
     * @param id 编号
     */
    void deleteUnit(Long id);

    /**
     * 获得项目单元
     *
     * @param id 编号
     * @return 项目单元
     */
    UnitDO getUnit(Long id);

    /**
     * 获得项目单元分页
     *
     * @param pageReqVO 分页查询
     * @return 项目单元分页
     */
    PageResult<UnitDO> getUnitPage(UnitPageReqVO pageReqVO);

}