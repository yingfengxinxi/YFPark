package cn.sdqingyun.smartpark.module.bus.service.village;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageTypeDictDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 项目类型字典 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface VillageTypeDictService {

    /**
     * 创建项目类型字典
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVillageTypeDict(@Valid VillageTypeDictSaveReqVO createReqVO);

    /**
     * 更新项目类型字典
     *
     * @param updateReqVO 更新信息
     */
    void updateVillageTypeDict(@Valid VillageTypeDictSaveReqVO updateReqVO);

    /**
     * 删除项目类型字典
     *
     * @param id 编号
     */
    void deleteVillageTypeDict(Long id);

    /**
     * 获得项目类型字典
     *
     * @param id 编号
     * @return 项目类型字典
     */
    VillageTypeDictDO getVillageTypeDict(Long id);

    /**
     * 获得项目类型字典分页
     *
     * @param pageReqVO 分页查询
     * @return 项目类型字典分页
     */
    PageResult<VillageTypeDictDO> getVillageTypeDictPage(VillageTypeDictPageReqVO pageReqVO);

}