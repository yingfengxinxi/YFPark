package cn.sdqingyun.smartpark.module.bus.dal.mysql.property;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyLendoutDepositoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo.*;

/**
 * 资产借出仓库信息 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface PropertyLendoutDepositoryMapper extends BaseMapperX<PropertyLendoutDepositoryDO> {

    default PageResult<PropertyLendoutDepositoryDO> selectPage(PropertyLendoutDepositoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyLendoutDepositoryDO>()
                .eqIfPresent(PropertyLendoutDepositoryDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(PropertyLendoutDepositoryDO::getPropertyId, reqVO.getPropertyId())
                .eqIfPresent(PropertyLendoutDepositoryDO::getNumber, reqVO.getNumber())
                .eqIfPresent(PropertyLendoutDepositoryDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyLendoutDepositoryDO::getOperateUid, reqVO.getOperateUid())
                .betweenIfPresent(PropertyLendoutDepositoryDO::getOperateTime, reqVO.getOperateTime())
                .eqIfPresent(PropertyLendoutDepositoryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PropertyLendoutDepositoryDO::getCuserUid, reqVO.getCuserUid())
                .eqIfPresent(PropertyLendoutDepositoryDO::getMuserUid, reqVO.getMuserUid())
                .betweenIfPresent(PropertyLendoutDepositoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PropertyLendoutDepositoryDO::getId));
    }

}