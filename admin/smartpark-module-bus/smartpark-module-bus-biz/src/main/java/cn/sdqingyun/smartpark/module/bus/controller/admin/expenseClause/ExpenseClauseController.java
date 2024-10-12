package cn.sdqingyun.smartpark.module.bus.controller.admin.expenseClause;

import cn.sdqingyun.smartpark.module.bus.controller.admin.expenseClause.vo.ExpenseClausePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.expenseClause.vo.ExpenseClauseRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.expenseClause.vo.ExpenseClauseSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.expenseclause.ExpenseClauseDO;
import cn.sdqingyun.smartpark.module.bus.service.expenseclause.ExpenseClauseService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.*;

@Tag(name = "管理后台 - 机构合同费用条款附加")
@RestController
@RequestMapping("/bus/expenseclause")
@Validated
public class ExpenseClauseController {

    @Resource
    private ExpenseClauseService expenseClauseService;

    @PostMapping("/create")
    @Operation(summary = "创建机构合同费用条款附加")
    @PreAuthorize("@ss.hasPermission('bus:expenseclause:create')")
    public CommonResult<Long> createExpenseClause(@Valid @RequestBody ExpenseClauseSaveReqVO createReqVO) {
        return success(expenseClauseService.createExpenseClause(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新机构合同费用条款附加")
    @PreAuthorize("@ss.hasPermission('bus:expenseclause:update')")
    public CommonResult<Boolean> updateExpenseClause(@Valid @RequestBody ExpenseClauseSaveReqVO updateReqVO) {
        expenseClauseService.updateExpenseClause(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除机构合同费用条款附加")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:expenseclause:delete')")
    public CommonResult<Boolean> deleteExpenseClause(@RequestParam("id") Long id) {
        expenseClauseService.deleteExpenseClause(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得机构合同费用条款附加")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:expenseclause:query')")
    public CommonResult<ExpenseClauseRespVO> getExpenseClause(@RequestParam("id") Long id) {
        ExpenseClauseDO expenseClause = expenseClauseService.getExpenseClause(id);
        return success(BeanUtils.toBean(expenseClause, ExpenseClauseRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得机构合同费用条款附加分页")
    @PreAuthorize("@ss.hasPermission('bus:expenseclause:query')")
    public CommonResult<PageResult<ExpenseClauseRespVO>> getExpenseClausePage(@Valid ExpenseClausePageReqVO pageReqVO) {
        PageResult<ExpenseClauseDO> pageResult = expenseClauseService.getExpenseClausePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ExpenseClauseRespVO.class));
    }

    @GetMapping("/export")
    @Operation(summary = "导出机构合同费用条款附加 Excel")
    @PreAuthorize("@ss.hasPermission('bus:expenseclause:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExpenseClauseExcel(@Valid ExpenseClausePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ExpenseClauseDO> list = expenseClauseService.getExpenseClausePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "机构合同费用条款附加.xls", "数据", ExpenseClauseRespVO.class,
                        BeanUtils.toBean(list, ExpenseClauseRespVO.class));
    }

}