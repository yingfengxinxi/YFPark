package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgIncomeDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgIncomeService;
import io.swagger.v3.oas.annotations.Parameters;
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

@Tag(name = "管理后台 - 机构收入")
@RestController
@RequestMapping("/bus/orgIncome")
@Validated
public class OrgIncomeController {

    @Resource
    private OrgIncomeService Service;

    @PostMapping("/create")
    @Operation(summary = "创建机构收入")
    @PreAuthorize("@ss.hasPermission('bus:orgIncome:create')")
    public CommonResult<Long> create(@Valid @RequestBody OrgIncomeSaveReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新机构收入")
    @PreAuthorize("@ss.hasPermission('bus:orgIncome:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody OrgIncomeSaveReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除机构收入")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgIncome:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得机构收入")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgIncome:query')")
    public CommonResult<OrgIncomeRespVO> get(@RequestParam("id") Long id) {
        OrgIncomeDO incomeDO = Service.get(id);
        return success(BeanUtils.toBean(incomeDO, OrgIncomeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "收入、支出确认分页")
    @PreAuthorize("@ss.hasPermission('bus:orgIncome:query')")
    @Parameters({
            @Parameter(name = "pageSize", description = "显示条数", required = true),
            @Parameter(name = "pageNo", description = "第几页", required = true),
            @Parameter(name = "billType", description = "1=收入确认2=支出确认", required = true),
            @Parameter(name = "startMonth", description = "开始月份", required = false),
            @Parameter(name = "endMonth", description = "结束月份", required = false),
            @Parameter(name = "isConfirm", description = "是否确认账单1=待确认2=已确认", required = false),
            @Parameter(name = "contractStatus", description = "合同状态【数据字典】", required = false),
            @Parameter(name = "ownerName", description = "租客名称", required = false)
    })
    public CommonResult<PageResult<BillTypeIncomeListPageVO>> getPage(@Valid BillTypeIncomeListPageVO pageReqVO) {
        PageResult<BillTypeIncomeListPageVO> pageResult = Service.getByBillTypeIncomeListPage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/incomeCount")
    @Operation(summary = "收入确认数量统计")
    @Parameters({
            @Parameter(name = "billType", description = "1=收入确认2=支出确认", required = true),
            @Parameter(name = "startMonth", description = "开始月份", required = false),
            @Parameter(name = "endMonth", description = "结束月份", required = false),
            @Parameter(name = "isConfirm", description = "是否确认账单1=待确认2=已确认", required = false),
            @Parameter(name = "contractStatus", description = "合同状态【数据字典】", required = false),
            @Parameter(name = "ownerName", description = "租客名称", required = false)
    })
    public CommonResult<Integer> incomeCount(@Valid BillTypeIncomeListPageVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BillTypeIncomeListPageVO> list = Service.getByBillTypeIncomeListPage(pageReqVO).getList();
        return success(list.size());
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出收入、支出确认收入 Excel")
    @PreAuthorize("@ss.hasPermission('bus:orgIncome:export')")
    @ApiAccessLog(operateType = EXPORT)
    @Parameters({
            @Parameter(name = "billType", description = "1=收入确认2=支出确认", required = true),
            @Parameter(name = "startMonth", description = "开始月份", required = false),
            @Parameter(name = "endMonth", description = "结束月份", required = false),
            @Parameter(name = "isConfirm", description = "是否确认账单1=待确认2=已确认", required = false),
            @Parameter(name = "contractStatus", description = "合同状态【数据字典】", required = false),
            @Parameter(name = "ownerName", description = "租客名称", required = false)
    })
    public void exportExcel(@Valid BillTypeIncomeListPageVO pageReqVO,
                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BillTypeIncomeListPageVO> list = Service.getByBillTypeIncomeListPage(pageReqVO).getList();
        // 导出 Excel
        String fileName = "机构收入";
        if (pageReqVO.getBillType().equals("1")) {
            fileName = "机构支出";
        }
        ExcelUtils.write(response, fileName + ".xls", "数据", BillTypeIncomeListPageVO.class, list);
    }

    @GetMapping("/getAmountStatistics")
    @Operation(summary = "收入列表顶部统计")
    public CommonResult<?> getAmountStatistics() {

        return CommonResult.success(Service.getAmountStatistics());
    }


    /**
     * @param orgBillConfirmIncomeVO
     * @return
     */
    @PostMapping("/confirmIncome")
    @Operation(summary = "确认/取消收支流水")
    @Parameters({
            @Parameter(name = "ids", description = "列表id集合【多个值使用逗号隔开1,2,3,4】", required = true),
            @Parameter(name = "isConfirm", description = "确认状态2=确认1=取消确认", required = true)
    })
    public CommonResult<?> confirmIncome(@Valid @RequestBody OrgBillConfirmIncomeVO orgBillConfirmIncomeVO) {
        Service.confirmIncome(orgBillConfirmIncomeVO);
        return success("操作成功");
    }

    //收入列表->按收入费用类型查询

    @GetMapping("/getIncomeTypeExpensesPage")
    @Operation(summary = "收入列表->按收入费用类型查询分页【未完成】")
    @Parameters({
            @Parameter(name = "billType", description = "1=收入确认2=支出确认", required = true),
            @Parameter(name = "matchMonth", description = "开始月份", required = false),
            @Parameter(name = "isConfirm", description = "是否确认账单1=待确认2=已确认", required = false),
            @Parameter(name = "contractStatus", description = "合同状态【数据字典】", required = false),
            @Parameter(name = "ownerName", description = "租客名称", required = false)
    })
    public CommonResult<PageResult<BillTypeIncomeListPageVO>> getIncomeTypeExpensesPage(@Valid BillTypeIncomeListPageVO pageReqVO) {
        Service.getIncomeTypeExpensesPage(pageReqVO);
        return success(null);
    }

}