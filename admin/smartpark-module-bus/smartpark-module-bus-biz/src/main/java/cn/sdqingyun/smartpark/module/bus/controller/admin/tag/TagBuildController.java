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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagBuildDO;
import cn.sdqingyun.smartpark.module.bus.service.tag.TagBuildService;

@Tag(name = "管理后台 - 楼宇标签")
@RestController
@RequestMapping("/bus/tag-build")
@Validated
public class TagBuildController {

    @Resource
    private TagBuildService tagBuildService;

    @PostMapping("/create")
    @Operation(summary = "创建楼宇标签")
    @PreAuthorize("@ss.hasPermission('bus:tag-build:create')")
    public CommonResult<Long> createTagBuild(@Valid @RequestBody TagBuildSaveReqVO createReqVO) {
        return success(tagBuildService.createTagBuild(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新楼宇标签")
    @PreAuthorize("@ss.hasPermission('bus:tag-build:update')")
    public CommonResult<Boolean> updateTagBuild(@Valid @RequestBody TagBuildSaveReqVO updateReqVO) {
        tagBuildService.updateTagBuild(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除楼宇标签")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:tag-build:delete')")
    public CommonResult<Boolean> deleteTagBuild(@RequestParam("id") Long id) {
        tagBuildService.deleteTagBuild(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得楼宇标签")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:tag-build:query')")
    public CommonResult<TagBuildRespVO> getTagBuild(@RequestParam("id") Long id) {
        TagBuildDO tagBuild = tagBuildService.getTagBuild(id);
        return success(BeanUtils.toBean(tagBuild, TagBuildRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得楼宇标签分页")
    @PreAuthorize("@ss.hasPermission('bus:tag-build:query')")
    public CommonResult<PageResult<TagBuildRespVO>> getTagBuildPage(@Valid TagBuildPageReqVO pageReqVO) {
        PageResult<TagBuildDO> pageResult = tagBuildService.getTagBuildPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TagBuildRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出楼宇标签 Excel")
    @PreAuthorize("@ss.hasPermission('bus:tag-build:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTagBuildExcel(@Valid TagBuildPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TagBuildDO> list = tagBuildService.getTagBuildPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "楼宇标签.xls", "数据", TagBuildRespVO.class,
                        BeanUtils.toBean(list, TagBuildRespVO.class));
    }

    @GetMapping("/getTagBuildListByIds")
    @Operation(summary = "通过id集合获得楼宇标签列表")
    @Parameter(name = "ids", description = "编号集合", required = true, example = "1,2")
    @PreAuthorize("@ss.hasPermission('bus:tag-build:query')")
    public CommonResult<List<TagBuildRespVO>> getTagBuildListByIds(@RequestParam("ids") Long[] ids) {
        List<TagBuildDO> tagBuild = tagBuildService.getTagBuildList(ids);
        return success(BeanUtils.toBean(tagBuild, TagBuildRespVO.class));
    }

    @GetMapping("/getTagBuildList")
    @Operation(summary = "获得楼宇标签列表")
    @PreAuthorize("@ss.hasPermission('bus:tag-build:query')")
    public CommonResult<List<TagBuildRespVO>> getTagBuildList(@Valid TagBuildPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TagBuildDO> list = tagBuildService.getTagBuildPage(pageReqVO).getList();
        return success(BeanUtils.toBean(list, TagBuildRespVO.class));
    }
}