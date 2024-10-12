package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffStockReceiveDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 耗材业务库存领用 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffStockReceiveMapper extends BaseMapperX<PropertyStuffStockReceiveDO> {

    default PageResult<PropertyStuffStockReceiveDO> selectPage(PropertyStuffStockReceivePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffStockReceiveDO>()
                .eqIfPresent(PropertyStuffStockReceiveDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffStockReceiveDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyStuffStockReceiveDO::getProcessNumber, reqVO.getProcessNumber())
                .eqIfPresent(PropertyStuffStockReceiveDO::getRelationType, reqVO.getRelationType())
                .eqIfPresent(PropertyStuffStockReceiveDO::getRelationNumber, reqVO.getRelationNumber())
                .eqIfPresent(PropertyStuffStockReceiveDO::getDepartmentId, reqVO.getDepartmentId())
                .likeIfPresent(PropertyStuffStockReceiveDO::getDepartmentName, reqVO.getDepartmentName())
                .eqIfPresent(PropertyStuffStockReceiveDO::getDepositoryId, reqVO.getDepositoryId())
                .eqIfPresent(PropertyStuffStockReceiveDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyStuffStockReceiveDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyStuffStockReceiveDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyStuffStockReceiveDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyStuffStockReceiveDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffStockReceiveDO::getId));
    }

}