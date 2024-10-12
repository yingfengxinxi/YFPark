package cn.sdqingyun.smartpark.module.bus.dal.mysql.tag;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagOwnerDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;

/**
 * 租客/业主标签 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface TagOwnerMapper extends BaseMapperX<TagOwnerDO> {

    default PageResult<TagOwnerDO> selectPage(TagOwnerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TagOwnerDO>()
                .eqIfPresent(TagOwnerDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(TagOwnerDO::getName, reqVO.getName())
                .eqIfPresent(TagOwnerDO::getDescVillage, reqVO.getDescVillage())
                .eqIfPresent(TagOwnerDO::getColor, reqVO.getColor())
                .eqIfPresent(TagOwnerDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TagOwnerDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TagOwnerDO::getId));
    }

}