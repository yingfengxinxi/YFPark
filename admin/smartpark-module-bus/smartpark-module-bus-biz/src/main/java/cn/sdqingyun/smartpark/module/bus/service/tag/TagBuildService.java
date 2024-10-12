package cn.sdqingyun.smartpark.module.bus.service.tag;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagBuildDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 楼宇标签 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface TagBuildService {

    /**
     * 创建楼宇标签
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTagBuild(@Valid TagBuildSaveReqVO createReqVO);

    /**
     * 更新楼宇标签
     *
     * @param updateReqVO 更新信息
     */
    void updateTagBuild(@Valid TagBuildSaveReqVO updateReqVO);

    /**
     * 删除楼宇标签
     *
     * @param id 编号
     */
    void deleteTagBuild(Long id);

    /**
     * 获得楼宇标签
     *
     * @param id 编号
     * @return 楼宇标签
     */
    TagBuildDO getTagBuild(Long id);

    /**
     * 获得楼宇标签分页
     *
     * @param pageReqVO 分页查询
     * @return 楼宇标签分页
     */
    PageResult<TagBuildDO> getTagBuildPage(TagBuildPageReqVO pageReqVO);

    /**
     * 获得楼宇标签列表
     *
     * @param ids 编号
     * @return 楼宇标签
     */
    List<TagBuildDO> getTagBuildList(Long[] ids);

}