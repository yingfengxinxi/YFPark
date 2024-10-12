package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffReturnDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 耗材业务耗材退还 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffReturnMapper extends BaseMapperX<PropertyStuffReturnDO> {

    default PageResult<PropertyStuffReturnDO> selectPage(PropertyStuffReturnPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffReturnDO>()
                .eqIfPresent(PropertyStuffReturnDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffReturnDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyStuffReturnDO::getProcessNumber, reqVO.getProcessNumber())
                .eqIfPresent(PropertyStuffReturnDO::getRelationType, reqVO.getRelationType())
                .eqIfPresent(PropertyStuffReturnDO::getRelationNumber, reqVO.getRelationNumber())
                .eqIfPresent(PropertyStuffReturnDO::getDepartmentId, reqVO.getDepartmentId())
                .likeIfPresent(PropertyStuffReturnDO::getDepartmentName, reqVO.getDepartmentName())
                .eqIfPresent(PropertyStuffReturnDO::getDepositoryId, reqVO.getDepositoryId())
                .eqIfPresent(PropertyStuffReturnDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyStuffReturnDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyStuffReturnDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyStuffReturnDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyStuffReturnDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffReturnDO::getId));
    }

}