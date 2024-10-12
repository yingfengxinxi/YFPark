package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerPaySet;

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

import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerPaySet.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerPaySet.OwnerPaySetDO;
import cn.sdqingyun.smartpark.module.bus.service.ownerPaySet.OwnerPaySetService;

@Tag(name = "管理后台 - 企业自动缴费费用配置")
@RestController
@RequestMapping("/bus/owner-pay-set")
@Validated
public class OwnerPaySetController {

    @Resource
    private OwnerPaySetService ownerPaySetService;

    @PostMapping("/create")
    @Operation(summary = "创建企业自动缴费费用配置")
    @PreAuthorize("@ss.hasPermission('bus:owner-pay-set:create')")
    public CommonResult<Long> createOwnerPaySet(@Valid @RequestBody OwnerPaySetSaveReqVO createReqVO) {
        return success(ownerPaySetService.createOwnerPaySet(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新企业自动缴费费用配置")
    @PreAuthorize("@ss.hasPermission('bus:owner-pay-set:update')")
    public CommonResult<Boolean> updateOwnerPaySet(@Valid @RequestBody OwnerPaySetSaveReqVO updateReqVO) {
        ownerPaySetService.updateOwnerPaySet(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除企业自动缴费费用配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:owner-pay-set:delete')")
    public CommonResult<Boolean> deleteOwnerPaySet(@RequestParam("id") Long id) {
        ownerPaySetService.deleteOwnerPaySet(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得企业自动缴费费用配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:owner-pay-set:query')")
    public CommonResult<OwnerPaySetRespVO> getOwnerPaySet(@RequestParam("id") Long id) {
        OwnerPaySetDO ownerPaySet = ownerPaySetService.getOwnerPaySet(id);
        return success(BeanUtils.toBean(ownerPaySet, OwnerPaySetRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得企业自动缴费费用配置分页")
    @PreAuthorize("@ss.hasPermission('bus:owner-pay-set:query')")
    public CommonResult<PageResult<OwnerPaySetRespVO>> getOwnerPaySetPage(@Valid OwnerPaySetPageReqVO pageReqVO) {
        PageResult<OwnerPaySetDO> pageResult = ownerPaySetService.getOwnerPaySetPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OwnerPaySetRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出企业自动缴费费用配置 Excel")
    @PreAuthorize("@ss.hasPermission('bus:owner-pay-set:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOwnerPaySetExcel(@Valid OwnerPaySetPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OwnerPaySetDO> list = ownerPaySetService.getOwnerPaySetPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "企业自动缴费费用配置.xls", "数据", OwnerPaySetRespVO.class,
                        BeanUtils.toBean(list, OwnerPaySetRespVO.class));
    }

}