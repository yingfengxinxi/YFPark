package cn.sdqingyun.smartpark.module.bus.service.tag;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagBuildPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagBuildSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagBuildDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.tag.TagBuildMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.TAG_BUILD_NOT_EXISTS;

/**
 * 楼宇标签 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class TagBuildServiceImpl implements TagBuildService {

    @Resource
    private TagBuildMapper tagBuildMapper;

    @Override
    public Long createTagBuild(TagBuildSaveReqVO createReqVO) {
        // 插入
        TagBuildDO tagBuild = BeanUtils.toBean(createReqVO, TagBuildDO.class);
        tagBuildMapper.insert(tagBuild);
        // 返回
        return tagBuild.getId();
    }

    @Override
    public void updateTagBuild(TagBuildSaveReqVO updateReqVO) {
        // 校验存在
        validateTagBuildExists(updateReqVO.getId());
        // 更新
        TagBuildDO updateObj = BeanUtils.toBean(updateReqVO, TagBuildDO.class);
        tagBuildMapper.updateById(updateObj);
    }

    @Override
    public void deleteTagBuild(Long id) {
        // 校验存在
        validateTagBuildExists(id);
        // 删除
        tagBuildMapper.deleteById(id);
    }

    private void validateTagBuildExists(Long id) {
        if (tagBuildMapper.selectById(id) == null) {
            throw exception(TAG_BUILD_NOT_EXISTS);
        }
    }

    @Override
    public TagBuildDO getTagBuild(Long id) {
        return tagBuildMapper.selectById(id);
    }

    @Override
    public PageResult<TagBuildDO> getTagBuildPage(TagBuildPageReqVO pageReqVO) {
        return tagBuildMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TagBuildDO> getTagBuildList(Long[] ids) {
        LambdaQueryWrapperX<TagBuildDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.in( TagBuildDO::getId, Arrays.stream( ids ).toArray() );
        return tagBuildMapper.selectList(wrapperX);
    }
}