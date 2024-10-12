package cn.sdqingyun.smartpark.module.bus.service.tag;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagOwnerDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.tag.TagOwnerMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 租客/业主标签 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class TagOwnerServiceImpl implements TagOwnerService {

    @Resource
    private TagOwnerMapper tagOwnerMapper;

    @Override
    public Long createTagOwner(TagOwnerSaveReqVO createReqVO) {
        // 插入
        TagOwnerDO tagOwner = BeanUtils.toBean(createReqVO, TagOwnerDO.class);
        tagOwnerMapper.insert(tagOwner);
        // 返回
        return tagOwner.getId();
    }

    @Override
    public void updateTagOwner(TagOwnerSaveReqVO updateReqVO) {
        // 校验存在
        validateTagOwnerExists(updateReqVO.getId());
        // 更新
        TagOwnerDO updateObj = BeanUtils.toBean(updateReqVO, TagOwnerDO.class);
        tagOwnerMapper.updateById(updateObj);
    }

    @Override
    public void deleteTagOwner(Long id) {
        // 校验存在
        validateTagOwnerExists(id);
        // 删除
        tagOwnerMapper.deleteById(id);
    }

    private void validateTagOwnerExists(Long id) {
        if (tagOwnerMapper.selectById(id) == null) {
            throw exception(TAG_OWNER_NOT_EXISTS);
        }
    }

    @Override
    public TagOwnerDO getTagOwner(Long id) {
        return tagOwnerMapper.selectById(id);
    }

    @Override
    public PageResult<TagOwnerDO> getTagOwnerPage(TagOwnerPageReqVO pageReqVO) {
        return tagOwnerMapper.selectPage(pageReqVO);
    }

}