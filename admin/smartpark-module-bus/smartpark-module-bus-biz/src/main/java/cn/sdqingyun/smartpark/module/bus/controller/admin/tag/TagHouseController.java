package cn.sdqingyun.smartpark.module.bus.controller.admin.tag;

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

import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagHouseDO;
import cn.sdqingyun.smartpark.module.bus.service.tag.TagHouseService;

@Tag(name = "管理后台 - 房源标签")
@RestController
@RequestMapping("/bus/tag-house")
@Validated
public class TagHouseController {

    @Resource
    private TagHouseService tagHouseService;

    @PostMapping("/create")
    @Operation(summary = "创建房源标签")
    @PreAuthorize("@ss.hasPermission('bus:tag-house:create')")
    public CommonResult<Long> createTagHouse(@Valid @RequestBody TagHouseSaveReqVO createReqVO) {
        return success(tagHouseService.createTagHouse(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新房源标签")
    @PreAuthorize("@ss.hasPermission('bus:tag-house:update')")
    public CommonResult<Boolean> updateTagHouse(@Valid @RequestBody TagHouseSaveReqVO updateReqVO) {
        tagHouseService.updateTagHouse(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除房源标签")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:tag-house:delete')")
    public CommonResult<Boolean> deleteTagHouse(@RequestParam("id") Long id) {
        tagHouseService.deleteTagHouse(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得房源标签")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:tag-house:query')")
    public CommonResult<TagHouseRespVO> getTagHouse(@RequestParam("id") Long id) {
        TagHouseDO tagHouse = tagHouseService.getTagHouse(id);
        return success(BeanUtils.toBean(tagHouse, TagHouseRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得房源标签分页")
    @PreAuthorize("@ss.hasPermission('bus:tag-house:query')")
    public CommonResult<PageResult<TagHouseRespVO>> getTagHousePage(@Valid TagHousePageReqVO pageReqVO) {
        PageResult<TagHouseDO> pageResult = tagHouseService.getTagHousePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TagHouseRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出房源标签 Excel")
    @PreAuthorize("@ss.hasPermission('bus:tag-house:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTagHouseExcel(@Valid TagHousePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TagHouseDO> list = tagHouseService.getTagHousePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "房源标签.xls", "数据", TagHouseRespVO.class,
                        BeanUtils.toBean(list, TagHouseRespVO.class));
    }

    @PostMapping("/getTagHouseList")
    @Operation(summary = "获得房源标签列表")
    @PreAuthorize("@ss.hasPermission('bus:tag-house:query')")
    public CommonResult<List<TagHouseRespVO>> getTagHouseList(@RequestBody @Valid TagHousePageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TagHouseDO> list = tagHouseService.getTagHousePage(pageReqVO).getList();
        return success(BeanUtils.toBean(list, TagHouseRespVO.class));
    }
}