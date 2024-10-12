package cn.sdqingyun.smartpark.module.bus.dal.mysql.tag;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagVillageDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;

/**
 * 项目标签 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface TagVillageMapper extends BaseMapperX<TagVillageDO> {

    default PageResult<TagVillageDO> selectPage(TagVillagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TagVillageDO>()
                .eqIfPresent(TagVillageDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(TagVillageDO::getName, reqVO.getName())
                .eqIfPresent(TagVillageDO::getDescVillage, reqVO.getDescVillage())
                .eqIfPresent(TagVillageDO::getColor, reqVO.getColor())
                .eqIfPresent(TagVillageDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TagVillageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TagVillageDO::getId));
    }

}