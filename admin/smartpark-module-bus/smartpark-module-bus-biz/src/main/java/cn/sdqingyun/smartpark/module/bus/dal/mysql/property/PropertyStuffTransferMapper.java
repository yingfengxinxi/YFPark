package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffTransferDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 耗材业务调拨 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyStuffTransferMapper extends BaseMapperX<PropertyStuffTransferDO> {

    default PageResult<PropertyStuffTransferDO> selectPage(PropertyStuffTransferPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyStuffTransferDO>()
                .eqIfPresent(PropertyStuffTransferDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyStuffTransferDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyStuffTransferDO::getProcessNumber, reqVO.getProcessNumber())
                .eqIfPresent(PropertyStuffTransferDO::getOutAdminUid, reqVO.getOutAdminUid())
                .eqIfPresent(PropertyStuffTransferDO::getInAdminUid, reqVO.getInAdminUid())
                .eqIfPresent(PropertyStuffTransferDO::getOutDepositoryId, reqVO.getOutDepositoryId())
                .eqIfPresent(PropertyStuffTransferDO::getInDepositoryId, reqVO.getInDepositoryId())
                .eqIfPresent(PropertyStuffTransferDO::getTotalNum, reqVO.getTotalNum())
                .eqIfPresent(PropertyStuffTransferDO::getTotalPrice, reqVO.getTotalPrice())
                .eqIfPresent(PropertyStuffTransferDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyStuffTransferDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyStuffTransferDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyStuffTransferDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyStuffTransferDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyStuffTransferDO::getId));
    }

}