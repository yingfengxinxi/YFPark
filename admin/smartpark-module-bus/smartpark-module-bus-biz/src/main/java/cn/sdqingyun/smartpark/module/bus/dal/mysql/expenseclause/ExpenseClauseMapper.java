package cn.sdqingyun.smartpark.module.bus.dal.mysql.expenseclause;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.framework.mybatis.core.mapper.BaseMapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.expenseClause.vo.ExpenseClausePageReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.expenseclause.ExpenseClauseDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 机构合同费用条款附加 Mapper
 *
 * @author 智慧园区管理员
 */
@Mapper
public interface ExpenseClauseMapper extends BaseMapperX<ExpenseClauseDO> {

    default PageResult<ExpenseClauseDO> selectPage(ExpenseClausePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ExpenseClauseDO>()
                .eqIfPresent(ExpenseClauseDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(ExpenseClauseDO::getContractId, reqVO.getContractId())
                .eqIfPresent(ExpenseClauseDO::getClauseId, reqVO.getClauseId())
                .eqIfPresent(ExpenseClauseDO::getClauseType, reqVO.getClauseType())
                .eqIfPresent(ExpenseClauseDO::getAttribute, reqVO.getAttribute())
                .eqIfPresent(ExpenseClauseDO::getBasicClause, reqVO.getBasicClause())
                .eqIfPresent(ExpenseClauseDO::getBondClause, reqVO.getBondClause())
                .eqIfPresent(ExpenseClauseDO::getTaxClause, reqVO.getTaxClause())
                .eqIfPresent(ExpenseClauseDO::getMultipleClause, reqVO.getMultipleClause())
                .eqIfPresent(ExpenseClauseDO::getIncrementClause, reqVO.getIncrementClause())
                .eqIfPresent(ExpenseClauseDO::getDiscountClause, reqVO.getDiscountClause())
                .eqIfPresent(ExpenseClauseDO::getRemarkClause, reqVO.getRemarkClause())
                .eqIfPresent(ExpenseClauseDO::getReportDetail, reqVO.getReportDetail())
                .eqIfPresent(ExpenseClauseDO::getDiscountPrice, reqVO.getDiscountPrice())
                .eqIfPresent(ExpenseClauseDO::getDiscountData, reqVO.getDiscountData())
                .betweenIfPresent(ExpenseClauseDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ExpenseClauseDO::getId));
    }

    /**
     *
     * @param contractId
     */
    void deleteByContractId(@Param("contractId") Long contractId);
}