package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffHandleDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 耗材业务处置 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffHandleMapper extends BaseMapperX<PropertyStuffHandleDO> {

    default PageResult<PropertyStuffHandleDO> selectPage(PropertyStuffHandlePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffHandleDO>()
                .eqIfPresent(PropertyStuffHandleDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffHandleDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyStuffHandleDO::getProcessNumber, reqVO.getProcessNumber())
                .eqIfPresent(PropertyStuffHandleDO::getDepartmentId, reqVO.getDepartmentId())
                .likeIfPresent(PropertyStuffHandleDO::getDepartmentName, reqVO.getDepartmentName())
                .eqIfPresent(PropertyStuffHandleDO::getDepositoryId, reqVO.getDepositoryId())
                .betweenIfPresent(PropertyStuffHandleDO::getLaunchTime, reqVO.getLaunchTime())
                .eqIfPresent(PropertyStuffHandleDO::getTotalPrice, reqVO.getTotalPrice())
                .eqIfPresent(PropertyStuffHandleDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyStuffHandleDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyStuffHandleDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyStuffHandleDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyStuffHandleDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffHandleDO::getId));
    }

}