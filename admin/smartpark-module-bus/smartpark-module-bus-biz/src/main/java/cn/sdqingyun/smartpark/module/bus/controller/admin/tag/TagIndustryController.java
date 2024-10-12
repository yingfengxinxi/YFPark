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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagIndustryDO;
import cn.sdqingyun.smartpark.module.bus.service.tag.TagIndustryService;

@Tag(name = "管理后台 - 行业分类标签")
@RestController
@RequestMapping("/bus/tag-industry")
@Validated
public class TagIndustryController {

    @Resource
    private TagIndustryService tagIndustryService;

    @PostMapping("/create")
    @Operation(summary = "创建行业分类标签")
    @PreAuthorize("@ss.hasPermission('bus:tag-industry:create')")
    public CommonResult<Long> createTagIndustry(@Valid @RequestBody TagIndustrySaveReqVO createReqVO) {
        return success(tagIndustryService.createTagIndustry(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新行业分类标签")
    @PreAuthorize("@ss.hasPermission('bus:tag-industry:update')")
    public CommonResult<Boolean> updateTagIndustry(@Valid @RequestBody TagIndustrySaveReqVO updateReqVO) {
        tagIndustryService.updateTagIndustry(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除行业分类标签")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:tag-industry:delete')")
    public CommonResult<Boolean> deleteTagIndustry(@RequestParam("id") Long id) {
        tagIndustryService.deleteTagIndustry(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得行业分类标签")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:tag-industry:query')")
    public CommonResult<TagIndustryRespVO> getTagIndustry(@RequestParam("id") Long id) {
        TagIndustryDO tagIndustry = tagIndustryService.getTagIndustry(id);
        return success(BeanUtils.toBean(tagIndustry, TagIndustryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得行业分类标签分页")
    @PreAuthorize("@ss.hasPermission('bus:tag-industry:query')")
    public CommonResult<PageResult<TagIndustryRespVO>> getTagIndustryPage(@Valid TagIndustryPageReqVO pageReqVO) {
        PageResult<TagIndustryDO> pageResult = tagIndustryService.getTagIndustryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TagIndustryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出行业分类标签 Excel")
    @PreAuthorize("@ss.hasPermission('bus:tag-industry:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTagIndustryExcel(@Valid TagIndustryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TagIndustryDO> list = tagIndustryService.getTagIndustryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "行业分类标签.xls", "数据", TagIndustryRespVO.class,
                        BeanUtils.toBean(list, TagIndustryRespVO.class));
    }

    @GetMapping("/getTagIndustryList")
    @Operation(summary = "获得行业分类标签列表")
    @PreAuthorize("@ss.hasPermission('bus:tag-industry:query')")
    public CommonResult<List<TagIndustryRespVO>> getTagIndustryList(@Valid TagIndustryPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TagIndustryDO> list = tagIndustryService.getTagIndustryPage(pageReqVO).getList();
        return success(BeanUtils.toBean(list, TagIndustryRespVO.class));
    }
}