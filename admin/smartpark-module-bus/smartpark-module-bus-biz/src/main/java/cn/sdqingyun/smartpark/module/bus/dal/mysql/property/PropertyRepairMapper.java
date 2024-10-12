package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyRepairDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产维修 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyRepairMapper extends BaseMapperX<PropertyRepairDO> {

    default PageResult<PropertyRepairDO> selectPage(PropertyRepairPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyRepairDO>()
                .eqIfPresent(PropertyRepairDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyRepairDO::getPropertyIds, reqVO.getPropertyIds())
                .eqIfPresent(PropertyRepairDO::getNumber, reqVO.getNumber())
                .eqIfPresent(PropertyRepairDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyRepairDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyRepairDO::getRepairUid, reqVO.getRepairUid())
                .eqIfPresent(PropertyRepairDO::getRepairDepartmentId, reqVO.getRepairDepartmentId())
                .betweenIfPresent(PropertyRepairDO::getRepairTime, reqVO.getRepairTime())
                .eqIfPresent(PropertyRepairDO::getRepairReason, reqVO.getRepairReason())
                .eqIfPresent(PropertyRepairDO::getOperateUid, reqVO.getOperateUid())
                .eqIfPresent(PropertyRepairDO::getExpectRepairPrice, reqVO.getExpectRepairPrice())
                .betweenIfPresent(PropertyRepairDO::getOperateTime, reqVO.getOperateTime())
                .eqIfPresent(PropertyRepairDO::getRepairContent, reqVO.getRepairContent())
                .eqIfPresent(PropertyRepairDO::getWorkorderInfo, reqVO.getWorkorderInfo())
                .eqIfPresent(PropertyRepairDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyRepairDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyRepairDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyRepairDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyRepairDO::getId));
    }

}