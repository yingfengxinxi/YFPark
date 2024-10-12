package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLendoutDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产借出 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyLendoutMapper extends BaseMapperX<PropertyLendoutDO> {

    default PageResult<PropertyLendoutDO> selectPage(PropertyLendoutPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyLendoutDO>()
                .eqIfPresent(PropertyLendoutDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyLendoutDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(PropertyLendoutDO::getBuildId, reqVO.getBuildId())
                .eqIfPresent(PropertyLendoutDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(PropertyLendoutDO::getType, reqVO.getType())
                .eqIfPresent(PropertyLendoutDO::getPropertyIds, reqVO.getPropertyIds())
                .eqIfPresent(PropertyLendoutDO::getNumber, reqVO.getNumber())
                .eqIfPresent(PropertyLendoutDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyLendoutDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyLendoutDO::getLendUid, reqVO.getLendUid())
                .eqIfPresent(PropertyLendoutDO::getDepartmentId, reqVO.getDepartmentId())
                .betweenIfPresent(PropertyLendoutDO::getLendTime, reqVO.getLendTime())
                .betweenIfPresent(PropertyLendoutDO::getExpectRevertTime, reqVO.getExpectRevertTime())
                .eqIfPresent(PropertyLendoutDO::getOperateUid, reqVO.getOperateUid())
                .betweenIfPresent(PropertyLendoutDO::getOperateTime, reqVO.getOperateTime())
                .eqIfPresent(PropertyLendoutDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyLendoutDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyLendoutDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyLendoutDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyLendoutDO::getId));
    }

}