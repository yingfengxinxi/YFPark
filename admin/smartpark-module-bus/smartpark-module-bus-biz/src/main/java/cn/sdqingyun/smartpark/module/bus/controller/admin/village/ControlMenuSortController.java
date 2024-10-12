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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ControlMenuSortDO;
import cn.sdqingyun.smartpark.module.bus.service.village.ControlMenuSortService;

@Tag(name = "管理后台 - 项目租控管理菜单排序")
@RestController
@RequestMapping("/bus/control-menu-sort")
@Validated
public class ControlMenuSortController {

    @Resource
    private ControlMenuSortService controlMenuSortService;

    @PostMapping("/create")
    @Operation(summary = "创建项目租控管理菜单排序")
    @PreAuthorize("@ss.hasPermission('bus:control-menu-sort:create')")
    public CommonResult<Long> createControlMenuSort(@Valid @RequestBody ControlMenuSortSaveReqVO createReqVO) {
        return success(controlMenuSortService.createControlMenuSort(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目租控管理菜单排序")
    @PreAuthorize("@ss.hasPermission('bus:control-menu-sort:update')")
    public CommonResult<Boolean> updateControlMenuSort(@Valid @RequestBody ControlMenuSortSaveReqVO updateReqVO) {
        controlMenuSortService.updateControlMenuSort(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目租控管理菜单排序")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:control-menu-sort:delete')")
    public CommonResult<Boolean> deleteControlMenuSort(@RequestParam("id") Long id) {
        controlMenuSortService.deleteControlMenuSort(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目租控管理菜单排序")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:control-menu-sort:query')")
    public CommonResult<ControlMenuSortRespVO> getControlMenuSort(@RequestParam("id") Long id) {
        ControlMenuSortDO controlMenuSort = controlMenuSortService.getControlMenuSort(id);
        return success(BeanUtils.toBean(controlMenuSort, ControlMenuSortRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目租控管理菜单排序分页")
    @PreAuthorize("@ss.hasPermission('bus:control-menu-sort:query')")
    public CommonResult<PageResult<ControlMenuSortRespVO>> getControlMenuSortPage(@Valid ControlMenuSortPageReqVO pageReqVO) {
        PageResult<ControlMenuSortDO> pageResult = controlMenuSortService.getControlMenuSortPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ControlMenuSortRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目租控管理菜单排序 Excel")
    @PreAuthorize("@ss.hasPermission('bus:control-menu-sort:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportControlMenuSortExcel(@Valid ControlMenuSortPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ControlMenuSortDO> list = controlMenuSortService.getControlMenuSortPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目租控管理菜单排序.xls", "数据", ControlMenuSortRespVO.class,
                        BeanUtils.toBean(list, ControlMenuSortRespVO.class));
    }

}