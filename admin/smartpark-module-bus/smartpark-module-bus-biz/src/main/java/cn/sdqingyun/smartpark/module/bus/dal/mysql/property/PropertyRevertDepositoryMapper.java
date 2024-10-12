package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyRevertDepositoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产归还仓库信息 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyRevertDepositoryMapper extends BaseMapperX<PropertyRevertDepositoryDO> {

    default PageResult<PropertyRevertDepositoryDO> selectPage(PropertyRevertDepositoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyRevertDepositoryDO>()
                .eqIfPresent(PropertyRevertDepositoryDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyRevertDepositoryDO::getPropertyId, reqVO.getPropertyId())
                .eqIfPresent(PropertyRevertDepositoryDO::getNumber, reqVO.getNumber())
                .eqIfPresent(PropertyRevertDepositoryDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyRevertDepositoryDO::getOperateUid, reqVO.getOperateUid())
                .betweenIfPresent(PropertyRevertDepositoryDO::getOperateTime, reqVO.getOperateTime())
                .eqIfPresent(PropertyRevertDepositoryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyRevertDepositoryDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyRevertDepositoryDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyRevertDepositoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyRevertDepositoryDO::getId));
    }

}