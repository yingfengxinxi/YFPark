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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ControlMenuDO;
import cn.sdqingyun.smartpark.module.bus.service.village.ControlMenuService;

@Tag(name = "管理后台 - 项目租控管理菜单")
@RestController
@RequestMapping("/bus/control-menu")
@Validated
public class ControlMenuController {

    @Resource
    private ControlMenuService controlMenuService;

    @PostMapping("/create")
    @Operation(summary = "创建项目租控管理菜单")
    @PreAuthorize("@ss.hasPermission('bus:control-menu:create')")
    public CommonResult<Long> createControlMenu(@Valid @RequestBody ControlMenuSaveReqVO createReqVO) {
        return success(controlMenuService.createControlMenu(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目租控管理菜单")
    @PreAuthorize("@ss.hasPermission('bus:control-menu:update')")
    public CommonResult<Boolean> updateControlMenu(@Valid @RequestBody ControlMenuSaveReqVO updateReqVO) {
        controlMenuService.updateControlMenu(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目租控管理菜单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:control-menu:delete')")
    public CommonResult<Boolean> deleteControlMenu(@RequestParam("id") Long id) {
        controlMenuService.deleteControlMenu(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目租控管理菜单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:control-menu:query')")
    public CommonResult<ControlMenuRespVO> getControlMenu(@RequestParam("id") Long id) {
        ControlMenuDO controlMenu = controlMenuService.getControlMenu(id);
        return success(BeanUtils.toBean(controlMenu, ControlMenuRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目租控管理菜单分页")
    @PreAuthorize("@ss.hasPermission('bus:control-menu:query')")
    public CommonResult<PageResult<ControlMenuRespVO>> getControlMenuPage(@Valid ControlMenuPageReqVO pageReqVO) {
        PageResult<ControlMenuDO> pageResult = controlMenuService.getControlMenuPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ControlMenuRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目租控管理菜单 Excel")
    @PreAuthorize("@ss.hasPermission('bus:control-menu:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportControlMenuExcel(@Valid ControlMenuPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ControlMenuDO> list = controlMenuService.getControlMenuPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目租控管理菜单.xls", "数据", ControlMenuRespVO.class,
                        BeanUtils.toBean(list, ControlMenuRespVO.class));
    }

}