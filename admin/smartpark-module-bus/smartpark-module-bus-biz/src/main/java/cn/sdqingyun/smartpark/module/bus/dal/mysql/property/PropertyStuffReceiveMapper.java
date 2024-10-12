package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffReceiveDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 耗材业务领用 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffReceiveMapper extends BaseMapperX<PropertyStuffReceiveDO> {

    default PageResult<PropertyStuffReceiveDO> selectPage(PropertyStuffReceivePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffReceiveDO>()
                .eqIfPresent(PropertyStuffReceiveDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffReceiveDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyStuffReceiveDO::getProcessNumber, reqVO.getProcessNumber())
                .eqIfPresent(PropertyStuffReceiveDO::getDepositoryId, reqVO.getDepositoryId())
                .eqIfPresent(PropertyStuffReceiveDO::getDepartmentId, reqVO.getDepartmentId())
                .likeIfPresent(PropertyStuffReceiveDO::getDepartmentName, reqVO.getDepartmentName())
                .eqIfPresent(PropertyStuffReceiveDO::getTotalNum, reqVO.getTotalNum())
                .eqIfPresent(PropertyStuffReceiveDO::getRelationType, reqVO.getRelationType())
                .eqIfPresent(PropertyStuffReceiveDO::getRelationNumber, reqVO.getRelationNumber())
                .eqIfPresent(PropertyStuffReceiveDO::getHandoutNum, reqVO.getHandoutNum())
                .betweenIfPresent(PropertyStuffReceiveDO::getApplyTime, reqVO.getApplyTime())
                .eqIfPresent(PropertyStuffReceiveDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyStuffReceiveDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyStuffReceiveDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyStuffReceiveDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyStuffReceiveDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffReceiveDO::getId));
    }

}