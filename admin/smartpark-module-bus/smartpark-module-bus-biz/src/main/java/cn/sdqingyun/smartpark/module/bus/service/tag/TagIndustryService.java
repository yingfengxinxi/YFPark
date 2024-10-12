package cn.sdqingyun.smartpark.module.bus.service.tag;

import java.util.*;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagIndustryDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 行业分类标签 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface TagIndustryService {

    /**
     * 创建行业分类标签
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTagIndustry(@Valid TagIndustrySaveReqVO createReqVO);

    /**
     * 更新行业分类标签
     *
     * @param updateReqVO 更新信息
     */
    void updateTagIndustry(@Valid TagIndustrySaveReqVO updateReqVO);

    /**
     * 删除行业分类标签
     *
     * @param id 编号
     */
    void deleteTagIndustry(Long id);

    /**
     * 获得行业分类标签
     *
     * @param id 编号
     * @return 行业分类标签
     */
    TagIndustryDO getTagIndustry(Long id);

    /**
     * 获得行业分类标签分页
     *
     * @param pageReqVO 分页查询
     * @return 行业分类标签分页
     */
    PageResult<TagIndustryDO> getTagIndustryPage(TagIndustryPageReqVO pageReqVO);

}