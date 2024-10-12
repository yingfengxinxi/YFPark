package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyMaintainSetDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产保养设置 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyMaintainSetMapper extends BaseMapperX<PropertyMaintainSetDO> {

    default PageResult<PropertyMaintainSetDO> selectPage(PropertyMaintainSetPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyMaintainSetDO>()
                .eqIfPresent(PropertyMaintainSetDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(PropertyMaintainSetDO::getMaintainVillageName, reqVO.getMaintainVillageName())
                .likeIfPresent(PropertyMaintainSetDO::getCreatorName, reqVO.getCreatorName())
                .eqIfPresent(PropertyMaintainSetDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PropertyMaintainSetDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyMaintainSetDO::getId));
    }

}