package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyTagDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产标签模板 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyTagMapper extends BaseMapperX<PropertyTagDO> {

    default PageResult<PropertyTagDO> selectPage(PropertyTagPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyTagDO>()
                .eqIfPresent(PropertyTagDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyTagDO::getIsApply, reqVO.getIsApply())
                .eqIfPresent(PropertyTagDO::getIsDefault, reqVO.getIsDefault())
                .eqIfPresent(PropertyTagDO::getTemplatePath, reqVO.getTemplatePath())
                .eqIfPresent(PropertyTagDO::getSort, reqVO.getSort())
                .likeIfPresent(PropertyTagDO::getName, reqVO.getName())
                .eqIfPresent(PropertyTagDO::getFieldLimit, reqVO.getFieldLimit())
                .eqIfPresent(PropertyTagDO::getHasLogo, reqVO.getHasLogo())
                .eqIfPresent(PropertyTagDO::getApplyJson, reqVO.getApplyJson())
                .eqIfPresent(PropertyTagDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyTagDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyTagDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyTagDO::getId));
    }

}