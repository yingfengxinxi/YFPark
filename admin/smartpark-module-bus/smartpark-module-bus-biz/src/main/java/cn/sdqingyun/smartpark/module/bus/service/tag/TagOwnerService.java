package cn.sdqingyun.smartpark.module.bus.service.tag;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagOwnerDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 租客/业主标签 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface TagOwnerService {

    /**
     * 创建租客/业主标签
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTagOwner(@Valid TagOwnerSaveReqVO createReqVO);

    /**
     * 更新租客/业主标签
     *
     * @param updateReqVO 更新信息
     */
    void updateTagOwner(@Valid TagOwnerSaveReqVO updateReqVO);

    /**
     * 删除租客/业主标签
     *
     * @param id 编号
     */
    void deleteTagOwner(Long id);

    /**
     * 获得租客/业主标签
     *
     * @param id 编号
     * @return 租客/业主标签
     */
    TagOwnerDO getTagOwner(Long id);

    /**
     * 获得租客/业主标签分页
     *
     * @param pageReqVO 分页查询
     * @return 租客/业主标签分页
     */
    PageResult<TagOwnerDO> getTagOwnerPage(TagOwnerPageReqVO pageReqVO);

}