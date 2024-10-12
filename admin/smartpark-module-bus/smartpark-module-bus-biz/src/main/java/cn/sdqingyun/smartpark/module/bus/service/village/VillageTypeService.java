package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageTypeDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 项目类型 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface VillageTypeService {

    /**
     * 创建项目类型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVillageType(@Valid VillageTypeSaveReqVO createReqVO);

    /**
     * 更新项目类型
     *
     * @param updateReqVO 更新信息
     */
    void updateVillageType(@Valid VillageTypeSaveReqVO updateReqVO);

    /**
     * 删除项目类型
     *
     * @param id 编号
     */
    void deleteVillageType(Long id);

    /**
     * 获得项目类型
     *
     * @param id 编号
     * @return 项目类型
     */
    VillageTypeDO getVillageType(Long id);

    /**
     * 获得项目类型分页
     *
     * @param pageReqVO 分页查询
     * @return 项目类型分页
     */
    PageResult<VillageTypeDO> getVillageTypePage(VillageTypePageReqVO pageReqVO);

}