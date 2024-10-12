package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyChangeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产变更领用人 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyChangeMapper extends BaseMapperX<PropertyChangeDO> {

    default PageResult<PropertyChangeDO> selectPage(PropertyChangePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyChangeDO>()
                .eqIfPresent(PropertyChangeDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyChangeDO::getPropertyIds, reqVO.getPropertyIds())
                .eqIfPresent(PropertyChangeDO::getNumber, reqVO.getNumber())
                .eqIfPresent(PropertyChangeDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyChangeDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyChangeDO::getAfterUseUid, reqVO.getAfterUseUid())
                .eqIfPresent(PropertyChangeDO::getAfterUseDepartmentId, reqVO.getAfterUseDepartmentId())
                .betweenIfPresent(PropertyChangeDO::getAfterTime, reqVO.getAfterTime())
                .eqIfPresent(PropertyChangeDO::getOperateUid, reqVO.getOperateUid())
                .betweenIfPresent(PropertyChangeDO::getOperateTime, reqVO.getOperateTime())
                .eqIfPresent(PropertyChangeDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyChangeDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyChangeDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyChangeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyChangeDO::getId));
    }

}