package cn.sdqingyun.smartpark.module.bus.service.tag;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagVillageDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 项目标签 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface TagVillageService {

    /**
     * 创建项目标签
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTagVillage(@Valid TagVillageSaveReqVO createReqVO);

    /**
     * 更新项目标签
     *
     * @param updateReqVO 更新信息
     */
    void updateTagVillage(@Valid TagVillageSaveReqVO updateReqVO);

    /**
     * 删除项目标签
     *
     * @param id 编号
     */
    void deleteTagVillage(Long id);

    /**
     * 获得项目标签
     *
     * @param id 编号
     * @return 项目标签
     */
    TagVillageDO getTagVillage(Long id);

    /**
     * 获得项目标签分页
     *
     * @param pageReqVO 分页查询
     * @return 项目标签分页
     */
    PageResult<TagVillageDO> getTagVillagePage(TagVillagePageReqVO pageReqVO);

}