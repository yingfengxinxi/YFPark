package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffLogDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产耗材业务记录 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffLogMapper extends BaseMapperX<PropertyStuffLogDO> {

    default PageResult<PropertyStuffLogDO> selectPage(PropertyStuffLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffLogDO>()
                .eqIfPresent(PropertyStuffLogDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffLogDO::getStuffId, reqVO.getStuffId())
                .eqIfPresent(PropertyStuffLogDO::getDepositoryId, reqVO.getDepositoryId())
                .eqIfPresent(PropertyStuffLogDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PropertyStuffLogDO::getBusiness, reqVO.getBusiness())
                .eqIfPresent(PropertyStuffLogDO::getType, reqVO.getType())
                .eqIfPresent(PropertyStuffLogDO::getNum, reqVO.getNum())
                .eqIfPresent(PropertyStuffLogDO::getExtraData, reqVO.getExtraData())
                .eqIfPresent(PropertyStuffLogDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyStuffLogDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyStuffLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffLogDO::getId));
    }

}