package cn.sdqingyun.smartpark.module.bus.dal.mysql.tag;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagBuildDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;

/**
 * 楼宇标签 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface TagBuildMapper extends BaseMapperX<TagBuildDO> {

    default PageResult<TagBuildDO> selectPage(TagBuildPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TagBuildDO>()
                .eqIfPresent(TagBuildDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(TagBuildDO::getName, reqVO.getName())
                .eqIfPresent(TagBuildDO::getDescVillage, reqVO.getDescVillage())
                .eqIfPresent(TagBuildDO::getColor, reqVO.getColor())
                .eqIfPresent(TagBuildDO::getUserShow, reqVO.getUserShow())
                .eqIfPresent(TagBuildDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TagBuildDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TagBuildDO::getId));
    }

}