package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyInventoryEmployeeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产盘点员工记录 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyInventoryEmployeeMapper extends BaseMapperX<PropertyInventoryEmployeeDO> {

    default PageResult<PropertyInventoryEmployeeDO> selectPage(PropertyInventoryEmployeePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyInventoryEmployeeDO>()
                .eqIfPresent(PropertyInventoryEmployeeDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyInventoryEmployeeDO::getInventoryId, reqVO.getInventoryId())
                .eqIfPresent(PropertyInventoryEmployeeDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PropertyInventoryEmployeeDO::getTypeIds, reqVO.getTypeIds())
                .eqIfPresent(PropertyInventoryEmployeeDO::getDepartmentIds, reqVO.getDepartmentIds())
                .eqIfPresent(PropertyInventoryEmployeeDO::getPositionIds, reqVO.getPositionIds())
                .eqIfPresent(PropertyInventoryEmployeeDO::getPropertyStatus, reqVO.getPropertyStatus())
                .eqIfPresent(PropertyInventoryEmployeeDO::getAdminId, reqVO.getAdminId())
                .eqIfPresent(PropertyInventoryEmployeeDO::getPurchaseType, reqVO.getPurchaseType())
                .eqIfPresent(PropertyInventoryEmployeeDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyInventoryEmployeeDO::getIsAll, reqVO.getIsAll())
                .eqIfPresent(PropertyInventoryEmployeeDO::getExterData, reqVO.getExterData())
                .betweenIfPresent(PropertyInventoryEmployeeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyInventoryEmployeeDO::getId));
    }

}