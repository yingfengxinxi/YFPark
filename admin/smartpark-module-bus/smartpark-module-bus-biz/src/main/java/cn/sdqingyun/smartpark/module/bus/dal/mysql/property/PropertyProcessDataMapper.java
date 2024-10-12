package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyProcessDataDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 业务流程单据关联资产 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyProcessDataMapper extends BaseMapperX<PropertyProcessDataDO> {

    default PageResult<PropertyProcessDataDO> selectPage(PropertyProcessDataPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyProcessDataDO>()
                .eqIfPresent(PropertyProcessDataDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyProcessDataDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyProcessDataDO::getProcessNumber, reqVO.getProcessNumber())
                .eqIfPresent(PropertyProcessDataDO::getPropertyId, reqVO.getPropertyId())
                .eqIfPresent(PropertyProcessDataDO::getPropertyNumber, reqVO.getPropertyNumber())
                .eqIfPresent(PropertyProcessDataDO::getType, reqVO.getType())
                .likeIfPresent(PropertyProcessDataDO::getName, reqVO.getName())
                .eqIfPresent(PropertyProcessDataDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyProcessDataDO::getPositionId, reqVO.getPositionId())
                .eqIfPresent(PropertyProcessDataDO::getBrand, reqVO.getBrand())
                .likeIfPresent(PropertyProcessDataDO::getModelName, reqVO.getModelName())
                .eqIfPresent(PropertyProcessDataDO::getBuildBind, reqVO.getBuildBind())
                .eqIfPresent(PropertyProcessDataDO::getMaintainFile, reqVO.getMaintainFile())
                .eqIfPresent(PropertyProcessDataDO::getMaintainPrice, reqVO.getMaintainPrice())
                .eqIfPresent(PropertyProcessDataDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyProcessDataDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyProcessDataDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyProcessDataDO::getId));
    }

}