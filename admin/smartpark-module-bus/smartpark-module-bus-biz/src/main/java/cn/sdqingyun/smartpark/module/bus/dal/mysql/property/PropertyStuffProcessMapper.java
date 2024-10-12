package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffProcessDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 耗材业务流程 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffProcessMapper extends BaseMapperX<PropertyStuffProcessDO> {

    default PageResult<PropertyStuffProcessDO> selectPage(PropertyStuffProcessPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffProcessDO>()
                .eqIfPresent(PropertyStuffProcessDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffProcessDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyStuffProcessDO::getProcessNumber, reqVO.getProcessNumber())
                .eqIfPresent(PropertyStuffProcessDO::getProcessType, reqVO.getProcessType())
                .eqIfPresent(PropertyStuffProcessDO::getStuffId, reqVO.getStuffId())
                .eqIfPresent(PropertyStuffProcessDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(PropertyStuffProcessDO::getDepositoryId, reqVO.getDepositoryId())
                .eqIfPresent(PropertyStuffProcessDO::getInDepositoryId, reqVO.getInDepositoryId())
                .likeIfPresent(PropertyStuffProcessDO::getName, reqVO.getName())
                .eqIfPresent(PropertyStuffProcessDO::getNumber, reqVO.getNumber())
                .eqIfPresent(PropertyStuffProcessDO::getBrand, reqVO.getBrand())
                .likeIfPresent(PropertyStuffProcessDO::getModelName, reqVO.getModelName())
                .eqIfPresent(PropertyStuffProcessDO::getMeterUnit, reqVO.getMeterUnit())
                .eqIfPresent(PropertyStuffProcessDO::getPrice, reqVO.getPrice())
                .eqIfPresent(PropertyStuffProcessDO::getNum, reqVO.getNum())
                .eqIfPresent(PropertyStuffProcessDO::getTotalPrice, reqVO.getTotalPrice())
                .eqIfPresent(PropertyStuffProcessDO::getImageUrl, reqVO.getImageUrl())
                .eqIfPresent(PropertyStuffProcessDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyStuffProcessDO::getHandoutNum, reqVO.getHandoutNum())
                .eqIfPresent(PropertyStuffProcessDO::getRetreatNum, reqVO.getRetreatNum())
                .eqIfPresent(PropertyStuffProcessDO::getExtra, reqVO.getExtra())
                .eqIfPresent(PropertyStuffProcessDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyStuffProcessDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyStuffProcessDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffProcessDO::getId));
    }

}