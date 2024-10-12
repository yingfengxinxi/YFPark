package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyTransferDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产调拨 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyTransferMapper extends BaseMapperX<PropertyTransferDO> {

    default PageResult<PropertyTransferDO> selectPage(PropertyTransferPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyTransferDO>()
                .eqIfPresent(PropertyTransferDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyTransferDO::getPropertyIds, reqVO.getPropertyIds())
                .eqIfPresent(PropertyTransferDO::getNumber, reqVO.getNumber())
                .eqIfPresent(PropertyTransferDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyTransferDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyTransferDO::getOutAdminUid, reqVO.getOutAdminUid())
                .eqIfPresent(PropertyTransferDO::getInAdminUid, reqVO.getInAdminUid())
                .eqIfPresent(PropertyTransferDO::getInLocationId, reqVO.getInLocationId())
                .eqIfPresent(PropertyTransferDO::getOperateUid, reqVO.getOperateUid())
                .betweenIfPresent(PropertyTransferDO::getOperateTime, reqVO.getOperateTime())
                .eqIfPresent(PropertyTransferDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyTransferDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyTransferDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyTransferDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyTransferDO::getId));
    }

}