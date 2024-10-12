package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyHandleDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产处置单据记录 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyHandleMapper extends BaseMapperX<PropertyHandleDO> {

    default PageResult<PropertyHandleDO> selectPage(PropertyHandlePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyHandleDO>()
                .eqIfPresent(PropertyHandleDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyHandleDO::getDepartmentId, reqVO.getDepartmentId())
                .eqIfPresent(PropertyHandleDO::getProcessNumber, reqVO.getProcessNumber())
                .eqIfPresent(PropertyHandleDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyHandleDO::getHandleAmount, reqVO.getHandleAmount())
                .eqIfPresent(PropertyHandleDO::getHandleExpenses, reqVO.getHandleExpenses())
                .eqIfPresent(PropertyHandleDO::getHandleType, reqVO.getHandleType())
                .eqIfPresent(PropertyHandleDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(PropertyHandleDO::getApplyTime, reqVO.getApplyTime())
                .eqIfPresent(PropertyHandleDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyHandleDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyHandleDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyHandleDO::getId));
    }

}