package cn.sdqingyun.smartpark.module.bus.dal.mysql.tag;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagHouseDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;

/**
 * 房源标签 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface TagHouseMapper extends BaseMapperX<TagHouseDO> {

    default PageResult<TagHouseDO> selectPage(TagHousePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TagHouseDO>()
                .eqIfPresent(TagHouseDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(TagHouseDO::getName, reqVO.getName())
                .eqIfPresent(TagHouseDO::getDescVillage, reqVO.getDescVillage())
                .eqIfPresent(TagHouseDO::getColor, reqVO.getColor())
                .eqIfPresent(TagHouseDO::getUserShow, reqVO.getUserShow())
                .eqIfPresent(TagHouseDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TagHouseDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TagHouseDO::getId));
    }

}