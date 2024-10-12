package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyInventoryRecordDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产盘点记录 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyInventoryRecordMapper extends BaseMapperX<PropertyInventoryRecordDO> {

    default PageResult<PropertyInventoryRecordDO> selectPage(PropertyInventoryRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyInventoryRecordDO>()
                .eqIfPresent(PropertyInventoryRecordDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyInventoryRecordDO::getInventoryId, reqVO.getInventoryId())
                .eqIfPresent(PropertyInventoryRecordDO::getPropertyId, reqVO.getPropertyId())
                .eqIfPresent(PropertyInventoryRecordDO::getTypeId, reqVO.getTypeId())
                .eqIfPresent(PropertyInventoryRecordDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyInventoryRecordDO::getPropertyStatus, reqVO.getPropertyStatus())
                .eqIfPresent(PropertyInventoryRecordDO::getIsRange, reqVO.getIsRange())
                .betweenIfPresent(PropertyInventoryRecordDO::getIsUpdate, reqVO.getIsUpdate())
                .eqIfPresent(PropertyInventoryRecordDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyInventoryRecordDO::getImage, reqVO.getImage())
                .eqIfPresent(PropertyInventoryRecordDO::getTag, reqVO.getTag())
                .betweenIfPresent(PropertyInventoryRecordDO::getInventoryTime, reqVO.getInventoryTime())
                .eqIfPresent(PropertyInventoryRecordDO::getInventoryUid, reqVO.getInventoryUid())
                .eqIfPresent(PropertyInventoryRecordDO::getPositionId, reqVO.getPositionId())
                .eqIfPresent(PropertyInventoryRecordDO::getHandleUid, reqVO.getHandleUid())
                .eqIfPresent(PropertyInventoryRecordDO::getDepartmentId, reqVO.getDepartmentId())
                .eqIfPresent(PropertyInventoryRecordDO::getPropertyInfo, reqVO.getPropertyInfo())
                .likeIfPresent(PropertyInventoryRecordDO::getName, reqVO.getName())
                .eqIfPresent(PropertyInventoryRecordDO::getPropertyNumber, reqVO.getPropertyNumber())
                .betweenIfPresent(PropertyInventoryRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyInventoryRecordDO::getId));
    }

}