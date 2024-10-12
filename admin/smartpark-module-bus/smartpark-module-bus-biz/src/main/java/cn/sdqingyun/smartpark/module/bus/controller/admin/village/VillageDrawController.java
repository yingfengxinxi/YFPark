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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDrawDO;
import cn.sdqingyun.smartpark.module.bus.service.village.VillageDrawService;

@Tag(name = "管理后台 - 项目绘制数据")
@RestController
@RequestMapping("/bus/village-draw")
@Validated
public class VillageDrawController {

    @Resource
    private VillageDrawService villageDrawService;

    @PostMapping("/create")
    @Operation(summary = "创建项目绘制数据")
    @PreAuthorize("@ss.hasPermission('bus:village-draw:create')")
    public CommonResult<Long> createVillageDraw(@Valid @RequestBody VillageDrawSaveReqVO createReqVO) {
        return success(villageDrawService.createVillageDraw(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目绘制数据")
    @PreAuthorize("@ss.hasPermission('bus:village-draw:update')")
    public CommonResult<Boolean> updateVillageDraw(@Valid @RequestBody VillageDrawSaveReqVO updateReqVO) {
        villageDrawService.updateVillageDraw(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目绘制数据")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:village-draw:delete')")
    public CommonResult<Boolean> deleteVillageDraw(@RequestParam("id") Long id) {
        villageDrawService.deleteVillageDraw(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目绘制数据")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:village-draw:query')")
    public CommonResult<VillageDrawRespVO> getVillageDraw(@RequestParam("id") Long id) {
        VillageDrawDO villageDraw = villageDrawService.getVillageDraw(id);
        return success(BeanUtils.toBean(villageDraw, VillageDrawRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目绘制数据分页")
    @PreAuthorize("@ss.hasPermission('bus:village-draw:query')")
    public CommonResult<PageResult<VillageDrawRespVO>> getVillageDrawPage(@Valid VillageDrawPageReqVO pageReqVO) {
        PageResult<VillageDrawDO> pageResult = villageDrawService.getVillageDrawPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VillageDrawRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目绘制数据 Excel")
    @PreAuthorize("@ss.hasPermission('bus:village-draw:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportVillageDrawExcel(@Valid VillageDrawPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VillageDrawDO> list = villageDrawService.getVillageDrawPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目绘制数据.xls", "数据", VillageDrawRespVO.class,
                        BeanUtils.toBean(list, VillageDrawRespVO.class));
    }

}