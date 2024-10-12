package cn.sdqingyun.smartpark.module.bus.dal.mysql.ownerPaySet;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerPaySet.OwnerPaySetDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerPaySet.vo.*;

/**
 * 企业自动缴费费用配置 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OwnerPaySetMapper extends BaseMapperX<OwnerPaySetDO> {

    default PageResult<OwnerPaySetDO> selectPage(OwnerPaySetPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OwnerPaySetDO>()
                .eqIfPresent(OwnerPaySetDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(OwnerPaySetDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(OwnerPaySetDO::getBuildId, reqVO.getBuildId())
                .eqIfPresent(OwnerPaySetDO::getOwnerId, reqVO.getOwnerId())
                .eqIfPresent(OwnerPaySetDO::getCostTypes, reqVO.getCostTypes())
                .betweenIfPresent(OwnerPaySetDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OwnerPaySetDO::getId));
    }

}