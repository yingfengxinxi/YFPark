package cn.sdqingyun.smartpark.module.bus.dal.mysql.tag;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagIndustryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;

/**
 * 行业分类标签 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface TagIndustryMapper extends BaseMapperX<TagIndustryDO> {

    default PageResult<TagIndustryDO> selectPage(TagIndustryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TagIndustryDO>()
                .eqIfPresent(TagIndustryDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(TagIndustryDO::getName, reqVO.getName())
                .eqIfPresent(TagIndustryDO::getDescVillage, reqVO.getDescVillage())
                .eqIfPresent(TagIndustryDO::getColor, reqVO.getColor())
                .eqIfPresent(TagIndustryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TagIndustryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TagIndustryDO::getId));
    }

}