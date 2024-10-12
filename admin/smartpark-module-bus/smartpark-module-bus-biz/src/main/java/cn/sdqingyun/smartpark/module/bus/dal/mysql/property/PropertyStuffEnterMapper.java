package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffEnterDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 耗材入库记录 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffEnterMapper extends BaseMapperX<PropertyStuffEnterDO> {

    default PageResult<PropertyStuffEnterDO> selectPage(PropertyStuffEnterPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffEnterDO>()
                .eqIfPresent(PropertyStuffEnterDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffEnterDO::getNumber, reqVO.getNumber())
                .eqIfPresent(PropertyStuffEnterDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyStuffEnterDO::getDepositoryId, reqVO.getDepositoryId())
                .eqIfPresent(PropertyStuffEnterDO::getEnterUid, reqVO.getEnterUid())
                .betweenIfPresent(PropertyStuffEnterDO::getEnterTime, reqVO.getEnterTime())
                .eqIfPresent(PropertyStuffEnterDO::getSupplier, reqVO.getSupplier())
                .eqIfPresent(PropertyStuffEnterDO::getTotalPrice, reqVO.getTotalPrice())
                .eqIfPresent(PropertyStuffEnterDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyStuffEnterDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyStuffEnterDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyStuffEnterDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyStuffEnterDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffEnterDO::getId));
    }

}