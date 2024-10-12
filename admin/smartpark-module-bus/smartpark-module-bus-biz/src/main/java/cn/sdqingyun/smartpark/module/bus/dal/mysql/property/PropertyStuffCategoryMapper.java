package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产耗材分类 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffCategoryMapper extends BaseMapperX<PropertyStuffCategoryDO> {

    default PageResult<PropertyStuffCategoryDO> selectPage(PropertyStuffCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffCategoryDO>()
                .eqIfPresent(PropertyStuffCategoryDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffCategoryDO::getNumber, reqVO.getNumber())
                .likeIfPresent(PropertyStuffCategoryDO::getName, reqVO.getName())
                .eqIfPresent(PropertyStuffCategoryDO::getParentId, reqVO.getParentId())
                .eqIfPresent(PropertyStuffCategoryDO::getParam, reqVO.getParam())
                .eqIfPresent(PropertyStuffCategoryDO::getLevel, reqVO.getLevel())
                .eqIfPresent(PropertyStuffCategoryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyStuffCategoryDO::getSort, reqVO.getSort())
                .eqIfPresent(PropertyStuffCategoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PropertyStuffCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffCategoryDO::getId));
    }

}