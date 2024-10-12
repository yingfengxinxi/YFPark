package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyApproveDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产单据审批记录 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyApproveMapper extends BaseMapperX<PropertyApproveDO> {

    default PageResult<PropertyApproveDO> selectPage(PropertyApprovePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyApproveDO>()
                .eqIfPresent(PropertyApproveDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyApproveDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(PropertyApproveDO::getBuildId, reqVO.getBuildId())
                .eqIfPresent(PropertyApproveDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(PropertyApproveDO::getApproveNumber, reqVO.getApproveNumber())
                .eqIfPresent(PropertyApproveDO::getRelationNumber, reqVO.getRelationNumber())
                .eqIfPresent(PropertyApproveDO::getRelationType, reqVO.getRelationType())
                .eqIfPresent(PropertyApproveDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyApproveDO::getProcessType, reqVO.getProcessType())
                .eqIfPresent(PropertyApproveDO::getContent, reqVO.getContent())
                .betweenIfPresent(PropertyApproveDO::getLaunchTime, reqVO.getLaunchTime())
                .betweenIfPresent(PropertyApproveDO::getApproveOvertime, reqVO.getApproveOvertime())
                .betweenIfPresent(PropertyApproveDO::getExpectRevertTime, reqVO.getExpectRevertTime())
                .eqIfPresent(PropertyApproveDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyApproveDO::getDepartmentId, reqVO.getDepartmentId())
                .eqIfPresent(PropertyApproveDO::getHandoverUid, reqVO.getHandoverUid())
                .eqIfPresent(PropertyApproveDO::getHandoverDepartmentId, reqVO.getHandoverDepartmentId())
                .eqIfPresent(PropertyApproveDO::getApprovalId, reqVO.getApprovalId())
                .eqIfPresent(PropertyApproveDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyApproveDO::getMuserUid, reqVO.getMuserUid())
                .eqIfPresent(PropertyApproveDO::getEndInventoryAfter, reqVO.getEndInventoryAfter())
                .betweenIfPresent(PropertyApproveDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyApproveDO::getId));
    }

}