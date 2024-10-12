package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffRetreatDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 耗材业务退库 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffRetreatMapper extends BaseMapperX<PropertyStuffRetreatDO> {

    default PageResult<PropertyStuffRetreatDO> selectPage(PropertyStuffRetreatPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffRetreatDO>()
                .eqIfPresent(PropertyStuffRetreatDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffRetreatDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyStuffRetreatDO::getProcessNumber, reqVO.getProcessNumber())
                .eqIfPresent(PropertyStuffRetreatDO::getRetreatUid, reqVO.getRetreatUid())
                .eqIfPresent(PropertyStuffRetreatDO::getDepartmentId, reqVO.getDepartmentId())
                .likeIfPresent(PropertyStuffRetreatDO::getDepartmentName, reqVO.getDepartmentName())
                .eqIfPresent(PropertyStuffRetreatDO::getDepositoryId, reqVO.getDepositoryId())
                .betweenIfPresent(PropertyStuffRetreatDO::getRetreatDate, reqVO.getRetreatDate())
                .eqIfPresent(PropertyStuffRetreatDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyStuffRetreatDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyStuffRetreatDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyStuffRetreatDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyStuffRetreatDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffRetreatDO::getId));
    }

}