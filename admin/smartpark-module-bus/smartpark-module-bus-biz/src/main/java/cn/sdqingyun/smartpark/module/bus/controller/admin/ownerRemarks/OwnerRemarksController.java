package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerRemarks;

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

import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerRemarks.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerRemarks.OwnerRemarksDO;
import cn.sdqingyun.smartpark.module.bus.service.ownerRemarks.OwnerRemarksService;

@Tag(name = "管理后台 - 租客备注信息")
@RestController
@RequestMapping("/bus/owner-remarks")
@Validated
public class OwnerRemarksController {

    @Resource
    private OwnerRemarksService ownerRemarksService;

    @PostMapping("/create")
    @Operation(summary = "创建租客备注信息")
    @PreAuthorize("@ss.hasPermission('bus:owner-remarks:create')")
    public CommonResult<Long> createOwnerRemarks(@Valid @RequestBody OwnerRemarksSaveReqVO createReqVO) {
        return success(ownerRemarksService.createOwnerRemarks(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新租客备注信息")
    @PreAuthorize("@ss.hasPermission('bus:owner-remarks:update')")
    public CommonResult<Boolean> updateOwnerRemarks(@Valid @RequestBody OwnerRemarksSaveReqVO updateReqVO) {
        ownerRemarksService.updateOwnerRemarks(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除租客备注信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:owner-remarks:delete')")
    public CommonResult<Boolean> deleteOwnerRemarks(@RequestParam("id") Long id) {
        ownerRemarksService.deleteOwnerRemarks(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得租客备注信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:owner-remarks:query')")
    public CommonResult<OwnerRemarksRespVO> getOwnerRemarks(@RequestParam("id") Long id) {
        OwnerRemarksDO ownerRemarks = ownerRemarksService.getOwnerRemarks(id);
        return success(BeanUtils.toBean(ownerRemarks, OwnerRemarksRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得租客备注信息分页")
    @PreAuthorize("@ss.hasPermission('bus:owner-remarks:query')")
    public CommonResult<PageResult<OwnerRemarksRespVO>> getOwnerRemarksPage(@Valid OwnerRemarksPageReqVO pageReqVO) {
        PageResult<OwnerRemarksDO> pageResult = ownerRemarksService.getOwnerRemarksPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OwnerRemarksRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出租客备注信息 Excel")
    @PreAuthorize("@ss.hasPermission('bus:owner-remarks:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOwnerRemarksExcel(@Valid OwnerRemarksPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OwnerRemarksDO> list = ownerRemarksService.getOwnerRemarksPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "租客备注信息.xls", "数据", OwnerRemarksRespVO.class,
                        BeanUtils.toBean(list, OwnerRemarksRespVO.class));
    }

}