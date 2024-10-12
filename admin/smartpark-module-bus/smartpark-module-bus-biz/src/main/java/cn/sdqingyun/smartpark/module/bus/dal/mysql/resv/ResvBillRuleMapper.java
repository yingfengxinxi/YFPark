package cn.sdqingyun.smartpark.module.bus.dal.mysql.resv;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.ResvBillRuleDO;
import org.apache.ibatis.annotations.Mapper;
import cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo.*;

/**
 * 预约收费规则 Mapper
 *
 * @author 智慧园区
 */
@Mapper
public interface ResvBillRuleMapper extends BaseMapperX<ResvBillRuleDO> {

    default PageResult<ResvBillRuleDO> selectPage(ResvBillRulePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ResvBillRuleDO>()
                .eqIfPresent(ResvBillRuleDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(ResvBillRuleDO::getAppSign, reqVO.getAppSign())
                .likeIfPresent(ResvBillRuleDO::getName, reqVO.getName())
                .eqIfPresent(ResvBillRuleDO::getVillageId, reqVO.getVillageId())
                .eqIfPresent(ResvBillRuleDO::getChargeStandard, reqVO.getChargeStandard())
                .eqIfPresent(ResvBillRuleDO::getIsMultiTimeCharge, reqVO.getIsMultiTimeCharge())
                .eqIfPresent(ResvBillRuleDO::getMultiTimeChargeStandard, reqVO.getMultiTimeChargeStandard())
                .eqIfPresent(ResvBillRuleDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ResvBillRuleDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ResvBillRuleDO::getId));
    }

}