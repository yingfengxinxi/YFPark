package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyInventoryLogDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产盘点操作日志 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyInventoryLogMapper extends BaseMapperX<PropertyInventoryLogDO> {

    default PageResult<PropertyInventoryLogDO> selectPage(PropertyInventoryLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyInventoryLogDO>()
                .eqIfPresent(PropertyInventoryLogDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyInventoryLogDO::getOperatorId, reqVO.getOperatorId())
                .likeIfPresent(PropertyInventoryLogDO::getOperatorName, reqVO.getOperatorName())
                .eqIfPresent(PropertyInventoryLogDO::getInventoryId, reqVO.getInventoryId())
                .eqIfPresent(PropertyInventoryLogDO::getRecordId, reqVO.getRecordId())
                .eqIfPresent(PropertyInventoryLogDO::getType, reqVO.getType())
                .eqIfPresent(PropertyInventoryLogDO::getContent, reqVO.getContent())
                .betweenIfPresent(PropertyInventoryLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyInventoryLogDO::getId));
    }

}