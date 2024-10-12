package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产分类 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyCategoryMapper extends BaseMapperX<PropertyCategoryDO> {

    default PageResult<PropertyCategoryDO> selectPage(PropertyCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyCategoryDO>()
                .eqIfPresent(PropertyCategoryDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyCategoryDO::getNumber, reqVO.getNumber())
                .likeIfPresent(PropertyCategoryDO::getName, reqVO.getName())
                .eqIfPresent(PropertyCategoryDO::getLevel, reqVO.getLevel())
                .eqIfPresent(PropertyCategoryDO::getParentId, reqVO.getParentId())
                .eqIfPresent(PropertyCategoryDO::getParam, reqVO.getParam())
                .eqIfPresent(PropertyCategoryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyCategoryDO::getSort, reqVO.getSort())
                .eqIfPresent(PropertyCategoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PropertyCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyCategoryDO::getId));
    }

}