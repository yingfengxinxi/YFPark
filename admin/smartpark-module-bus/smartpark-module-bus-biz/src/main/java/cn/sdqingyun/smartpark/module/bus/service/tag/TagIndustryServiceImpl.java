package cn.sdqingyun.smartpark.module.bus.service.tag;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagIndustryDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.tag.TagIndustryMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 行业分类标签 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class TagIndustryServiceImpl implements TagIndustryService {

    @Resource
    private TagIndustryMapper tagIndustryMapper;

    @Override
    public Long createTagIndustry(TagIndustrySaveReqVO createReqVO) {
        // 插入
        TagIndustryDO tagIndustry = BeanUtils.toBean(createReqVO, TagIndustryDO.class);
        tagIndustryMapper.insert(tagIndustry);
        // 返回
        return tagIndustry.getId();
    }

    @Override
    public void updateTagIndustry(TagIndustrySaveReqVO updateReqVO) {
        // 校验存在
        validateTagIndustryExists(updateReqVO.getId());
        // 更新
        TagIndustryDO updateObj = BeanUtils.toBean(updateReqVO, TagIndustryDO.class);
        tagIndustryMapper.updateById(updateObj);
    }

    @Override
    public void deleteTagIndustry(Long id) {
        // 校验存在
        validateTagIndustryExists(id);
        // 删除
        tagIndustryMapper.deleteById(id);
    }

    private void validateTagIndustryExists(Long id) {
        if (tagIndustryMapper.selectById(id) == null) {
            throw exception(TAG_INDUSTRY_NOT_EXISTS);
        }
    }

    @Override
    public TagIndustryDO getTagIndustry(Long id) {
        return tagIndustryMapper.selectById(id);
    }

    @Override
    public PageResult<TagIndustryDO> getTagIndustryPage(TagIndustryPageReqVO pageReqVO) {
        return tagIndustryMapper.selectPage(pageReqVO);
    }

}