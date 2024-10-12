package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerFiles;

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

import cn.sdqingyun.smartpark.module.bus.controller.admin.ownerFiles.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerFiles.OwnerFilesDO;
import cn.sdqingyun.smartpark.module.bus.service.ownerFiles.OwnerFilesService;

@Tag(name = "管理后台 - 租客附件")
@RestController
@RequestMapping("/bus/owner-files")
@Validated
public class OwnerFilesController {

    @Resource
    private OwnerFilesService ownerFilesService;

    @PostMapping("/create")
    @Operation(summary = "创建租客附件")
    @PreAuthorize("@ss.hasPermission('bus:owner-files:create')")
    public CommonResult<Long> createOwnerFiles(@Valid @RequestBody OwnerFilesSaveReqVO createReqVO) {
        return success(ownerFilesService.createOwnerFiles(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新租客附件")
    @PreAuthorize("@ss.hasPermission('bus:owner-files:update')")
    public CommonResult<Boolean> updateOwnerFiles(@Valid @RequestBody OwnerFilesSaveReqVO updateReqVO) {
        ownerFilesService.updateOwnerFiles(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除租客附件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:owner-files:delete')")
    public CommonResult<Boolean> deleteOwnerFiles(@RequestParam("id") Long id) {
        ownerFilesService.deleteOwnerFiles(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得租客附件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:owner-files:query')")
    public CommonResult<OwnerFilesRespVO> getOwnerFiles(@RequestParam("id") Long id) {
        OwnerFilesDO ownerFiles = ownerFilesService.getOwnerFiles(id);
        return success(BeanUtils.toBean(ownerFiles, OwnerFilesRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得租客附件分页")
    @PreAuthorize("@ss.hasPermission('bus:owner-files:query')")
    public CommonResult<PageResult<OwnerFilesRespVO>> getOwnerFilesPage(@Valid OwnerFilesPageReqVO pageReqVO) {
        PageResult<OwnerFilesDO> pageResult = ownerFilesService.getOwnerFilesPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OwnerFilesRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出租客附件 Excel")
    @PreAuthorize("@ss.hasPermission('bus:owner-files:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOwnerFilesExcel(@Valid OwnerFilesPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OwnerFilesDO> list = ownerFilesService.getOwnerFilesPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "租客附件.xls", "数据", OwnerFilesRespVO.class,
                        BeanUtils.toBean(list, OwnerFilesRespVO.class));
    }

}