package cn.sdqingyun.smartpark.module.bus.service.tag;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagVillageDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.tag.TagVillageMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 项目标签 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class TagVillageServiceImpl implements TagVillageService {

    @Resource
    private TagVillageMapper tagVillageMapper;

    @Override
    public Long createTagVillage(TagVillageSaveReqVO createReqVO) {
        // 插入
        TagVillageDO tagVillage = BeanUtils.toBean(createReqVO, TagVillageDO.class);
        tagVillageMapper.insert(tagVillage);
        // 返回
        return tagVillage.getId();
    }

    @Override
    public void updateTagVillage(TagVillageSaveReqVO updateReqVO) {
        // 校验存在
        validateTagVillageExists(updateReqVO.getId());
        // 更新
        TagVillageDO updateObj = BeanUtils.toBean(updateReqVO, TagVillageDO.class);
        tagVillageMapper.updateById(updateObj);
    }

    @Override
    public void deleteTagVillage(Long id) {
        // 校验存在
        validateTagVillageExists(id);
        // 删除
        tagVillageMapper.deleteById(id);
    }

    private void validateTagVillageExists(Long id) {
        if (tagVillageMapper.selectById(id) == null) {
            throw exception(TAG_VILLAGE_NOT_EXISTS);
        }
    }

    @Override
    public TagVillageDO getTagVillage(Long id) {
        return tagVillageMapper.selectById(id);
    }

    @Override
    public PageResult<TagVillageDO> getTagVillagePage(TagVillagePageReqVO pageReqVO) {
        return tagVillageMapper.selectPage(pageReqVO);
    }

}