package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLocationDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 位置 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyLocationMapper extends BaseMapperX<PropertyLocationDO> {

    default PageResult<PropertyLocationDO> selectPage(PropertyLocationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyLocationDO>()
                .eqIfPresent(PropertyLocationDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyLocationDO::getNumber, reqVO.getNumber())
                .likeIfPresent(PropertyLocationDO::getName, reqVO.getName())
                .eqIfPresent(PropertyLocationDO::getLevel, reqVO.getLevel())
                .eqIfPresent(PropertyLocationDO::getParentId, reqVO.getParentId())
                .eqIfPresent(PropertyLocationDO::getParam, reqVO.getParam())
                .eqIfPresent(PropertyLocationDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyLocationDO::getSort, reqVO.getSort())
                .eqIfPresent(PropertyLocationDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PropertyLocationDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyLocationDO::getId));
    }

}