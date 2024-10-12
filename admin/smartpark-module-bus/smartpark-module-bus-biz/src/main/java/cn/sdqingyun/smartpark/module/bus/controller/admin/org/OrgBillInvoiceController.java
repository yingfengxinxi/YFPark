package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillConfirmIncomeVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillInvoicePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillInvoiceRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillInvoiceSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillInvoiceDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillInvoiceService;
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

@Tag(name = "管理后台 - 机构账单开票记录")
@RestController
@RequestMapping("/bus/orgBillInvoice/")
@Validated
public class OrgBillInvoiceController {

    @Resource
    private OrgBillInvoiceService Service;

    @PostMapping("/create")
    @Operation(summary = "创建机构账单开票记录")
    @PreAuthorize("@ss.hasPermission('bus:orgBillInvoice:create')")
    public CommonResult<Long> create(@Valid @RequestBody OrgBillInvoiceSaveReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新机构账单开票记录")
    @PreAuthorize("@ss.hasPermission('bus:orgBillInvoice:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody OrgBillInvoiceSaveReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除机构账单开票记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgBillInvoice:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得机构账单开票记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgBillInvoice:query')")
    public CommonResult<OrgBillInvoiceRespVO> get(@RequestParam("id") Long id) {
        OrgBillInvoiceDO billInvoiceDO = Service.get(id);
        return success(BeanUtils.toBean(billInvoiceDO, OrgBillInvoiceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得机构账单开票记录分页")
    @PreAuthorize("@ss.hasPermission('bus:orgBillInvoice:query')")
    public CommonResult<PageResult<OrgBillInvoiceRespVO>> getPage(@Valid OrgBillInvoicePageReqVO pageReqVO) {
        PageResult<OrgBillInvoiceDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgBillInvoiceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出机构账单开票记录 Excel")
    @PreAuthorize("@ss.hasPermission('bus:orgBillInvoice:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExcel(@Valid OrgBillInvoicePageReqVO pageReqVO,
                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrgBillInvoiceDO> list = Service.getPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "机构账单开票记录.xls", "数据", OrgBillInvoiceRespVO.class,
                BeanUtils.toBean(list, OrgBillInvoiceRespVO.class));
    }

}