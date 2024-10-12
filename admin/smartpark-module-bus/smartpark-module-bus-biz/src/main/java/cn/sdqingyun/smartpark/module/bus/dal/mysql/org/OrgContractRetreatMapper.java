package cn.sdqingyun.smartpark.module.bus.dal.mysql.org;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgContractRetreatPageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgContractRetreatDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 机构合同退租 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface OrgContractRetreatMapper extends BaseMapperX<OrgContractRetreatDO> {

    default PageResult<OrgContractRetreatDO> selectPage(OrgContractRetreatPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrgContractRetreatDO>()
                .eqIfPresent(OrgContractRetreatDO::getOwnerId, reqVO.getOwnerId())
                .eqIfPresent(OrgContractRetreatDO::getContractId, reqVO.getContractId())
                .eqIfPresent(OrgContractRetreatDO::getApplyTemplateId, reqVO.getApplyTemplateId())
                .eqIfPresent(OrgContractRetreatDO::getBuildBind, reqVO.getBuildBind())
                .eqIfPresent(OrgContractRetreatDO::getBillInfo, reqVO.getBillInfo())
                .eqIfPresent(OrgContractRetreatDO::getBondInfo, reqVO.getBondInfo())
                .eqIfPresent(OrgContractRetreatDO::getTotalCost, reqVO.getTotalCost())
                .eqIfPresent(OrgContractRetreatDO::getRetreatType, reqVO.getRetreatType())
                .eqIfPresent(OrgContractRetreatDO::getRetreatReason, reqVO.getRetreatReason())
                .eqIfPresent(OrgContractRetreatDO::getRemark, reqVO.getRemark())
                .eqIfPresent(OrgContractRetreatDO::getApplyContract, reqVO.getApplyContract())
                .orderByDesc(OrgContractRetreatDO::getId));
    }

    /**
     *
     * @param contractId
     */
    void deleteByContractId(@Param("contractId") Long contractId);
}