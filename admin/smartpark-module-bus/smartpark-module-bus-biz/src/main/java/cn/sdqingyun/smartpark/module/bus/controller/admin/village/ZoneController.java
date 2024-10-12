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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ZoneDO;
import cn.sdqingyun.smartpark.module.bus.service.village.ZoneService;

@Tag(name = "管理后台 - 项目分区")
@RestController
@RequestMapping("/bus/zone")
@Validated
public class ZoneController {

    @Resource
    private ZoneService zoneService;

    @PostMapping("/create")
    @Operation(summary = "创建项目分区")
    @PreAuthorize("@ss.hasPermission('bus:zone:create')")
    public CommonResult<Long> createZone(@Valid @RequestBody ZoneSaveReqVO createReqVO) {
        return success(zoneService.createZone(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目分区")
    @PreAuthorize("@ss.hasPermission('bus:zone:update')")
    public CommonResult<Boolean> updateZone(@Valid @RequestBody ZoneSaveReqVO updateReqVO) {
        zoneService.updateZone(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目分区")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:zone:delete')")
    public CommonResult<Boolean> deleteZone(@RequestParam("id") Long id) {
        zoneService.deleteZone(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目分区")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:zone:query')")
    public CommonResult<ZoneRespVO> getZone(@RequestParam("id") Long id) {
        ZoneDO zone = zoneService.getZone(id);
        return success(BeanUtils.toBean(zone, ZoneRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目分区分页")
    @PreAuthorize("@ss.hasPermission('bus:zone:query')")
    public CommonResult<PageResult<ZoneRespVO>> getZonePage(@Valid ZonePageReqVO pageReqVO) {
        PageResult<ZoneDO> pageResult = zoneService.getZonePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZoneRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目分区 Excel")
    @PreAuthorize("@ss.hasPermission('bus:zone:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportZoneExcel(@Valid ZonePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZoneDO> list = zoneService.getZonePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目分区.xls", "数据", ZoneRespVO.class,
                        BeanUtils.toBean(list, ZoneRespVO.class));
    }

}