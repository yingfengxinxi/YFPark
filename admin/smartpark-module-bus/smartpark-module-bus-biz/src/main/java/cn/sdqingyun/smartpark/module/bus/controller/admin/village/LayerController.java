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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.LayerDO;
import cn.sdqingyun.smartpark.module.bus.service.village.LayerService;

@Tag(name = "管理后台 - 项目楼层")
@RestController
@RequestMapping("/bus/layer")
@Validated
public class LayerController {

    @Resource
    private LayerService layerService;

    @PostMapping("/create")
    @Operation(summary = "创建项目楼层")
    @PreAuthorize("@ss.hasPermission('bus:layer:create')")
    public CommonResult<Long> createLayer(@Valid @RequestBody LayerSaveReqVO createReqVO) {
        return success(layerService.createLayer(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目楼层")
    @PreAuthorize("@ss.hasPermission('bus:layer:update')")
    public CommonResult<Boolean> updateLayer(@Valid @RequestBody LayerSaveReqVO updateReqVO) {
        layerService.updateLayer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目楼层")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:layer:delete')")
    public CommonResult<Boolean> deleteLayer(@RequestParam("id") Long id) {
        layerService.deleteLayer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目楼层")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:layer:query')")
    public CommonResult<LayerRespVO> getLayer(@RequestParam("id") Long id) {
        LayerDO layer = layerService.getLayer(id);
        return success(BeanUtils.toBean(layer, LayerRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目楼层分页")
    @PreAuthorize("@ss.hasPermission('bus:layer:query')")
    public CommonResult<PageResult<LayerRespVO>> getLayerPage(@Valid LayerPageReqVO pageReqVO) {
        PageResult<LayerDO> pageResult = layerService.getLayerPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, LayerRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目楼层 Excel")
    @PreAuthorize("@ss.hasPermission('bus:layer:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportLayerExcel(@Valid LayerPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<LayerDO> list = layerService.getLayerPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目楼层.xls", "数据", LayerRespVO.class,
                        BeanUtils.toBean(list, LayerRespVO.class));
    }

    @GetMapping("/getLayerList")
    @Operation(summary = "获得项目楼层列表")
    @PreAuthorize("@ss.hasPermission('bus:layer:query')")
    public CommonResult<List<LayerRespVO>> getLayerList(@Valid LayerPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        return success(layerService.getLayerList(pageReqVO));
    }

}