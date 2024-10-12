package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyRevertDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产归还 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyRevertMapper extends BaseMapperX<PropertyRevertDO> {

    default PageResult<PropertyRevertDO> selectPage(PropertyRevertPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyRevertDO>()
                .eqIfPresent(PropertyRevertDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyRevertDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(PropertyRevertDO::getBuildId, reqVO.getBuildId())
                .eqIfPresent(PropertyRevertDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(PropertyRevertDO::getPropertyIds, reqVO.getPropertyIds())
                .eqIfPresent(PropertyRevertDO::getNumber, reqVO.getNumber())
                .eqIfPresent(PropertyRevertDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyRevertDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyRevertDO::getRevertUid, reqVO.getRevertUid())
                .eqIfPresent(PropertyRevertDO::getDepartmentId, reqVO.getDepartmentId())
                .betweenIfPresent(PropertyRevertDO::getRevertTime, reqVO.getRevertTime())
                .betweenIfPresent(PropertyRevertDO::getOperateTime, reqVO.getOperateTime())
                .eqIfPresent(PropertyRevertDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyRevertDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyRevertDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyRevertDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyRevertDO::getId));
    }

}