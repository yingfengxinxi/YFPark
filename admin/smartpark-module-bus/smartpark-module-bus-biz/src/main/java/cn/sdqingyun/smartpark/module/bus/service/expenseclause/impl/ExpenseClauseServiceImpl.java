package cn.sdqingyun.smartpark.module.bus.service.expenseclause.impl;

import cn.sdqingyun.smartpark.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.sdqingyun.smartpark.module.bus.controller.admin.expenseClause.vo.ExpenseClausePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.expenseClause.vo.ExpenseClauseSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.expenseclause.ExpenseClauseDO;
import cn.sdqingyun.smartpark.module.bus.dal.mysql.expenseclause.ExpenseClauseMapper;
import cn.sdqingyun.smartpark.module.bus.service.expenseclause.ExpenseClauseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.sdqingyun.smartpark.module.bus.enums.ErrorCodeConstants.EXPENSE_CLAUSE_NOT_EXISTS;

/**
 * 机构合同费用条款附加 Service 实现类
 *
 * @author 智慧园区管理员
 */
@Service
@Validated
public class ExpenseClauseServiceImpl implements ExpenseClauseService {

    @Resource
    private ExpenseClauseMapper expenseClauseMapper;

    @Override
    public Long createExpenseClause(ExpenseClauseSaveReqVO createReqVO) {
        // 插入
        ExpenseClauseDO expenseClause = BeanUtils.toBean(createReqVO, ExpenseClauseDO.class);
        expenseClauseMapper.insert(expenseClause);
        // 返回
        return expenseClause.getId();
    }

    @Override
    public void updateExpenseClause(ExpenseClauseSaveReqVO updateReqVO) {
        // 校验存在
        validateExpenseClauseExists(updateReqVO.getId());
        // 更新
        ExpenseClauseDO updateObj = BeanUtils.toBean(updateReqVO, ExpenseClauseDO.class);
        expenseClauseMapper.updateById(updateObj);
    }

    @Override
    public void deleteExpenseClause(Long id) {
        // 校验存在
        validateExpenseClauseExists(id);
        // 删除
        expenseClauseMapper.deleteById(id);
    }

    /**
     * @param contractId
     */
    @Override
    public void deleteByContractId(Long contractId) {
        LambdaQueryWrapper<ExpenseClauseDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExpenseClauseDO::getContractId, contractId);
        expenseClauseMapper.delete(queryWrapper);
    }

    private void validateExpenseClauseExists(Long id) {
        if (expenseClauseMapper.selectById(id) == null) {
            throw exception(EXPENSE_CLAUSE_NOT_EXISTS);
        }
    }

    @Override
    public ExpenseClauseDO getExpenseClause(Long id) {
        return expenseClauseMapper.selectById(id);
    }

    /**
     *
     * @param contractId
     * @return
     */
    @Override
    public List<ExpenseClauseDO> getByContractIdExpenseClauseList(Long contractId) {
        LambdaQueryWrapper<ExpenseClauseDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExpenseClauseDO::getContractId, contractId);
        return expenseClauseMapper.selectList(queryWrapper);
    }

    @Override
    public PageResult<ExpenseClauseDO> getExpenseClausePage(ExpenseClausePageReqVO pageReqVO) {
        return expenseClauseMapper.selectPage(pageReqVO);
    }

}