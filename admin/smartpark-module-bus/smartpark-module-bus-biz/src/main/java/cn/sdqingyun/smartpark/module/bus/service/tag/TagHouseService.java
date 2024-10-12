package cn.sdqingyun.smartpark.module.bus.service.tag;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagHouseDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 房源标签 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface TagHouseService {

    /**
     * 创建房源标签
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTagHouse(@Valid TagHouseSaveReqVO createReqVO);

    /**
     * 更新房源标签
     *
     * @param updateReqVO 更新信息
     */
    void updateTagHouse(@Valid TagHouseSaveReqVO updateReqVO);

    /**
     * 删除房源标签
     *
     * @param id 编号
     */
    void deleteTagHouse(Long id);

    /**
     * 获得房源标签
     *
     * @param id 编号
     * @return 房源标签
     */
    TagHouseDO getTagHouse(Long id);

    /**
     * 获得房源标签分页
     *
     * @param pageReqVO 分页查询
     * @return 房源标签分页
     */
    PageResult<TagHouseDO> getTagHousePage(TagHousePageReqVO pageReqVO);

}