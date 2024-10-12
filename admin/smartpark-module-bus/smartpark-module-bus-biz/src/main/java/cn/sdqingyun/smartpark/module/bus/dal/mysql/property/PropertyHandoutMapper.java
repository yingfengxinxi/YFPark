package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyHandoutDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产派发/退库 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyHandoutMapper extends BaseMapperX<PropertyHandoutDO> {

    default PageResult<PropertyHandoutDO> selectPage(PropertyHandoutPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyHandoutDO>()
                .eqIfPresent(PropertyHandoutDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyHandoutDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(PropertyHandoutDO::getBuildId, reqVO.getBuildId())
                .eqIfPresent(PropertyHandoutDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(PropertyHandoutDO::getPropertyIds, reqVO.getPropertyIds())
                .eqIfPresent(PropertyHandoutDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyHandoutDO::getType, reqVO.getType())
                .eqIfPresent(PropertyHandoutDO::getNumber, reqVO.getNumber())
                .eqIfPresent(PropertyHandoutDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyHandoutDO::getDepartmentId, reqVO.getDepartmentId())
                .eqIfPresent(PropertyHandoutDO::getReceiveUid, reqVO.getReceiveUid())
                .betweenIfPresent(PropertyHandoutDO::getHandoutTime, reqVO.getHandoutTime())
                .betweenIfPresent(PropertyHandoutDO::getReturnTime, reqVO.getReturnTime())
                .eqIfPresent(PropertyHandoutDO::getOperateUid, reqVO.getOperateUid())
                .betweenIfPresent(PropertyHandoutDO::getOperateTime, reqVO.getOperateTime())
                .eqIfPresent(PropertyHandoutDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyHandoutDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyHandoutDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyHandoutDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyHandoutDO::getId));
    }

}