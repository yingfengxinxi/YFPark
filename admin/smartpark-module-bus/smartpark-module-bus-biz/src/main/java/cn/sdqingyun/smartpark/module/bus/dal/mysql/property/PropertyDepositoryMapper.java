package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyDepositoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产仓库信息 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyDepositoryMapper extends BaseMapperX<PropertyDepositoryDO> {

    default PageResult<PropertyDepositoryDO> selectPage(PropertyDepositoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyDepositoryDO>()
                .eqIfPresent(PropertyDepositoryDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyDepositoryDO::getNumber, reqVO.getNumber())
                .eqIfPresent(PropertyDepositoryDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyDepositoryDO::getOperateUid, reqVO.getOperateUid())
                .betweenIfPresent(PropertyDepositoryDO::getOperateTime, reqVO.getOperateTime())
                .eqIfPresent(PropertyDepositoryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyDepositoryDO::getProcessCode, reqVO.getProcessCode())
                .eqIfPresent(PropertyDepositoryDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyDepositoryDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyDepositoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyDepositoryDO::getId));
    }

}