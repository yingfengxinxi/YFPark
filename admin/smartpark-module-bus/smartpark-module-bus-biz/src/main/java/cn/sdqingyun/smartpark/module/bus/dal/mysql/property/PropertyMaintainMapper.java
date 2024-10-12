package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyMaintainDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产保养记录 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyMaintainMapper extends BaseMapperX<PropertyMaintainDO> {

    default PageResult<PropertyMaintainDO> selectPage(PropertyMaintainPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyMaintainDO>()
                .eqIfPresent(PropertyMaintainDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyMaintainDO::getMaintainUid, reqVO.getMaintainUid())
                .eqIfPresent(PropertyMaintainDO::getDepartmentId, reqVO.getDepartmentId())
                .likeIfPresent(PropertyMaintainDO::getDepartmentName, reqVO.getDepartmentName())
                .eqIfPresent(PropertyMaintainDO::getMaintainVillageId, reqVO.getMaintainVillageId())
                .eqIfPresent(PropertyMaintainDO::getPropertyIds, reqVO.getPropertyIds())
                .eqIfPresent(PropertyMaintainDO::getPropertyData, reqVO.getPropertyData())
                .eqIfPresent(PropertyMaintainDO::getNumber, reqVO.getNumber())
                .eqIfPresent(PropertyMaintainDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyMaintainDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyMaintainDO::getMaintainTotalPrice, reqVO.getMaintainTotalPrice())
                .betweenIfPresent(PropertyMaintainDO::getMaintainDate, reqVO.getMaintainDate())
                .betweenIfPresent(PropertyMaintainDO::getNextMaintainDate, reqVO.getNextMaintainDate())
                .eqIfPresent(PropertyMaintainDO::getOperateUid, reqVO.getOperateUid())
                .betweenIfPresent(PropertyMaintainDO::getOperateTime, reqVO.getOperateTime())
                .eqIfPresent(PropertyMaintainDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyMaintainDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyMaintainDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyMaintainDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyMaintainDO::getId));
    }

}