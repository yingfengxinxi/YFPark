package cn.sdqingyun.smartpark.module.bus.service.expenseclause;

import java.util.*;

import cn.sdqingyun.smartpark.module.bus.controller.admin.expenseClause.vo.ExpenseClausePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.expenseClause.vo.ExpenseClauseSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.expenseclause.ExpenseClauseDO;
import jakarta.validation.*;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;

/**
 * 机构合同费用条款附加 Service 接口
 *
 * @author 智慧园区管理员
 */
public interface ExpenseClauseService {

    /**
     * 创建机构合同费用条款附加
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createExpenseClause(@Valid ExpenseClauseSaveReqVO createReqVO);

    /**
     * 更新机构合同费用条款附加
     *
     * @param updateReqVO 更新信息
     */
    void updateExpenseClause(@Valid ExpenseClauseSaveReqVO updateReqVO);

    /**
     * 删除机构合同费用条款附加
     *
     * @param id 编号
     */
    void deleteExpenseClause(Long id);

    /**
     *
     * @param contractId
     */
    void deleteByContractId(Long contractId);

    /**
     * 获得机构合同费用条款附加
     *
     * @param id 编号
     * @return 机构合同费用条款附加
     */
    ExpenseClauseDO getExpenseClause(Long id);


    /**
     *
     * @param contractId
     * @return
     */
    List<ExpenseClauseDO> getByContractIdExpenseClauseList(Long contractId);

    /**
     * 获得机构合同费用条款附加分页
     *
     * @param pageReqVO 分页查询
     * @return 机构合同费用条款附加分页
     */
    PageResult<ExpenseClauseDO> getExpenseClausePage(ExpenseClausePageReqVO pageReqVO);

}