package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyHandoutDepositoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产派发仓库信息 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyHandoutDepositoryMapper extends BaseMapperX<PropertyHandoutDepositoryDO> {

    default PageResult<PropertyHandoutDepositoryDO> selectPage(PropertyHandoutDepositoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyHandoutDepositoryDO>()
                .eqIfPresent(PropertyHandoutDepositoryDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyHandoutDepositoryDO::getPropertyId, reqVO.getPropertyId())
                .eqIfPresent(PropertyHandoutDepositoryDO::getNumber, reqVO.getNumber())
                .eqIfPresent(PropertyHandoutDepositoryDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyHandoutDepositoryDO::getOperateUid, reqVO.getOperateUid())
                .betweenIfPresent(PropertyHandoutDepositoryDO::getOperateTime, reqVO.getOperateTime())
                .eqIfPresent(PropertyHandoutDepositoryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyHandoutDepositoryDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyHandoutDepositoryDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyHandoutDepositoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyHandoutDepositoryDO::getId));
    }

}