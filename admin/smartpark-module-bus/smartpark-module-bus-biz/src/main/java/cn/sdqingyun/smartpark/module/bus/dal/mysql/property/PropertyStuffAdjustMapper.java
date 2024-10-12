package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffAdjustDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 耗材业务调整 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffAdjustMapper extends BaseMapperX<PropertyStuffAdjustDO> {

    default PageResult<PropertyStuffAdjustDO> selectPage(PropertyStuffAdjustPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffAdjustDO>()
                .eqIfPresent(PropertyStuffAdjustDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffAdjustDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyStuffAdjustDO::getProcessNumber, reqVO.getProcessNumber())
                .eqIfPresent(PropertyStuffAdjustDO::getDepositoryId, reqVO.getDepositoryId())
                .betweenIfPresent(PropertyStuffAdjustDO::getAdjustTime, reqVO.getAdjustTime())
                .eqIfPresent(PropertyStuffAdjustDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyStuffAdjustDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyStuffAdjustDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyStuffAdjustDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyStuffAdjustDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffAdjustDO::getId));
    }

}