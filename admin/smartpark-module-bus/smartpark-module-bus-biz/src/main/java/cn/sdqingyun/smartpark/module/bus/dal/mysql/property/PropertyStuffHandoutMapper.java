package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffHandoutDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 耗材业务派发 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffHandoutMapper extends BaseMapperX<PropertyStuffHandoutDO> {

    default PageResult<PropertyStuffHandoutDO> selectPage(PropertyStuffHandoutPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffHandoutDO>()
                .eqIfPresent(PropertyStuffHandoutDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffHandoutDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyStuffHandoutDO::getProcessNumber, reqVO.getProcessNumber())
                .eqIfPresent(PropertyStuffHandoutDO::getReceiveUid, reqVO.getReceiveUid())
                .eqIfPresent(PropertyStuffHandoutDO::getDepartmentId, reqVO.getDepartmentId())
                .likeIfPresent(PropertyStuffHandoutDO::getDepartmentName, reqVO.getDepartmentName())
                .eqIfPresent(PropertyStuffHandoutDO::getDepositoryId, reqVO.getDepositoryId())
                .betweenIfPresent(PropertyStuffHandoutDO::getHandoutTime, reqVO.getHandoutTime())
                .eqIfPresent(PropertyStuffHandoutDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyStuffHandoutDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyStuffHandoutDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyStuffHandoutDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyStuffHandoutDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffHandoutDO::getId));
    }

}