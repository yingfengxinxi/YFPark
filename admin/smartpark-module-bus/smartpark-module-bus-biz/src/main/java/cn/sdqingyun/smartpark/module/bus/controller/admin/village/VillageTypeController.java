package cn.sdqingyun.smartpark.module.bus.controller.admin.village;

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

import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageTypeDO;
import cn.sdqingyun.smartpark.module.bus.service.village.VillageTypeService;

@Tag(name = "管理后台 - 项目类型")
@RestController
@RequestMapping("/bus/village-type")
@Validated
public class VillageTypeController {

    @Resource
    private VillageTypeService villageTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建项目类型")
    @PreAuthorize("@ss.hasPermission('bus:village-type:create')")
    public CommonResult<Long> createVillageType(@Valid @RequestBody VillageTypeSaveReqVO createReqVO) {
        return success(villageTypeService.createVillageType(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目类型")
    @PreAuthorize("@ss.hasPermission('bus:village-type:update')")
    public CommonResult<Boolean> updateVillageType(@Valid @RequestBody VillageTypeSaveReqVO updateReqVO) {
        villageTypeService.updateVillageType(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目类型")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:village-type:delete')")
    public CommonResult<Boolean> deleteVillageType(@RequestParam("id") Long id) {
        villageTypeService.deleteVillageType(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目类型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:village-type:query')")
    public CommonResult<VillageTypeRespVO> getVillageType(@RequestParam("id") Long id) {
        VillageTypeDO villageType = villageTypeService.getVillageType(id);
        return success(BeanUtils.toBean(villageType, VillageTypeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目类型分页")
    @PreAuthorize("@ss.hasPermission('bus:village-type:query')")
    public CommonResult<PageResult<VillageTypeRespVO>> getVillageTypePage(@Valid VillageTypePageReqVO pageReqVO) {
        PageResult<VillageTypeDO> pageResult = villageTypeService.getVillageTypePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VillageTypeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目类型 Excel")
    @PreAuthorize("@ss.hasPermission('bus:village-type:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportVillageTypeExcel(@Valid VillageTypePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VillageTypeDO> list = villageTypeService.getVillageTypePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目类型.xls", "数据", VillageTypeRespVO.class,
                        BeanUtils.toBean(list, VillageTypeRespVO.class));
    }

    @GetMapping("/getList")
    @Operation(summary = "获得项目类型列表")
    @PreAuthorize("@ss.hasPermission('bus:village-type:query')")
    public CommonResult<List<VillageTypeRespVO>> getListVillageType(@Valid VillageTypePageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VillageTypeDO> list = villageTypeService.getVillageTypePage(pageReqVO).getList();
        return success(BeanUtils.toBean(list, VillageTypeRespVO.class));
    }
}