package cn.sdqingyun.smartpark.module.bus.service.tag;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagHouseDO;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import cn.sdqingyun.smartpark.module.bus.dal.mysql.tag.TagHouseMapper;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.*;

/**
 * 房源标签 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class TagHouseServiceImpl implements TagHouseService {

    @Resource
    private TagHouseMapper tagHouseMapper;

    @Override
    public Long createTagHouse(TagHouseSaveReqVO createReqVO) {
        // 插入
        TagHouseDO tagHouse = BeanUtils.toBean(createReqVO, TagHouseDO.class);
        tagHouseMapper.insert(tagHouse);
        // 返回
        return tagHouse.getId();
    }

    @Override
    public void updateTagHouse(TagHouseSaveReqVO updateReqVO) {
        // 校验存在
        validateTagHouseExists(updateReqVO.getId());
        // 更新
        TagHouseDO updateObj = BeanUtils.toBean(updateReqVO, TagHouseDO.class);
        tagHouseMapper.updateById(updateObj);
    }

    @Override
    public void deleteTagHouse(Long id) {
        // 校验存在
        validateTagHouseExists(id);
        // 删除
        tagHouseMapper.deleteById(id);
    }

    private void validateTagHouseExists(Long id) {
        if (tagHouseMapper.selectById(id) == null) {
            throw exception(TAG_HOUSE_NOT_EXISTS);
        }
    }

    @Override
    public TagHouseDO getTagHouse(Long id) {
        return tagHouseMapper.selectById(id);
    }

    @Override
    public PageResult<TagHouseDO> getTagHousePage(TagHousePageReqVO pageReqVO) {
        return tagHouseMapper.selectPage(pageReqVO);
    }

}