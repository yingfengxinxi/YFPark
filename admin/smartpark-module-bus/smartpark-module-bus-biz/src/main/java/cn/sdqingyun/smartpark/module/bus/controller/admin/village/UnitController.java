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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.UnitDO;
import cn.sdqingyun.smartpark.module.bus.service.village.UnitService;

@Tag(name = "管理后台 - 项目单元")
@RestController
@RequestMapping("/bus/unit")
@Validated
public class UnitController {

    @Resource
    private UnitService unitService;

    @PostMapping("/create")
    @Operation(summary = "创建项目单元")
    @PreAuthorize("@ss.hasPermission('bus:unit:create')")
    public CommonResult<Long> createUnit(@Valid @RequestBody UnitSaveReqVO createReqVO) {
        return success(unitService.createUnit(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目单元")
    @PreAuthorize("@ss.hasPermission('bus:unit:update')")
    public CommonResult<Boolean> updateUnit(@Valid @RequestBody UnitSaveReqVO updateReqVO) {
        unitService.updateUnit(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目单元")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:unit:delete')")
    public CommonResult<Boolean> deleteUnit(@RequestParam("id") Long id) {
        unitService.deleteUnit(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目单元")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:unit:query')")
    public CommonResult<UnitRespVO> getUnit(@RequestParam("id") Long id) {
        UnitDO unit = unitService.getUnit(id);
        return success(BeanUtils.toBean(unit, UnitRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目单元分页")
    @PreAuthorize("@ss.hasPermission('bus:unit:query')")
    public CommonResult<PageResult<UnitRespVO>> getUnitPage(@Valid UnitPageReqVO pageReqVO) {
        PageResult<UnitDO> pageResult = unitService.getUnitPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, UnitRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目单元 Excel")
    @PreAuthorize("@ss.hasPermission('bus:unit:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportUnitExcel(@Valid UnitPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<UnitDO> list = unitService.getUnitPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目单元.xls", "数据", UnitRespVO.class,
                        BeanUtils.toBean(list, UnitRespVO.class));
    }

}