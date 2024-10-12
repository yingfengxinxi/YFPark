package cn.sdqingyun.smartpark.module.bus.controller.admin.tag;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagVillagePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagVillageRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagVillageSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagVillageDO;
import cn.sdqingyun.smartpark.module.bus.service.tag.TagVillageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 项目标签")
@RestController
@RequestMapping("/bus/tag-village")
@Validated
public class TagVillageController {

    @Resource
    private TagVillageService tagVillageService;

    @PostMapping("/create")
    @Operation(summary = "创建项目标签")
    @PreAuthorize("@ss.hasPermission('bus:tag-village:create')")
    public CommonResult<Long> createTagVillage(@Valid @RequestBody TagVillageSaveReqVO createReqVO) {
        return success(tagVillageService.createTagVillage(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目标签")
    @PreAuthorize("@ss.hasPermission('bus:tag-village:update')")
    public CommonResult<Boolean> updateTagVillage(@Valid @RequestBody TagVillageSaveReqVO updateReqVO) {
        tagVillageService.updateTagVillage(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目标签")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:tag-village:delete')")
    public CommonResult<Boolean> deleteTagVillage(@RequestParam("id") Long id) {
        tagVillageService.deleteTagVillage(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目标签")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:tag-village:query')")
    public CommonResult<TagVillageRespVO> getTagVillage(@RequestParam("id") Long id) {
        TagVillageDO tagVillage = tagVillageService.getTagVillage(id);
        return success(BeanUtils.toBean(tagVillage, TagVillageRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目标签分页")
    @PreAuthorize("@ss.hasPermission('bus:tag-village:query')")
    public CommonResult<PageResult<TagVillageRespVO>> getTagVillagePage(@Valid TagVillagePageReqVO pageReqVO) {
        PageResult<TagVillageDO> pageResult = tagVillageService.getTagVillagePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TagVillageRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目标签 Excel")
    @PreAuthorize("@ss.hasPermission('bus:tag-village:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTagVillageExcel(@Valid TagVillagePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TagVillageDO> list = tagVillageService.getTagVillagePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目标签.xls", "数据", TagVillageRespVO.class,
                        BeanUtils.toBean(list, TagVillageRespVO.class));
    }

    @GetMapping("/getList")
    @Operation(summary = "获得项目标签列表")
    @PreAuthorize("@ss.hasPermission('bus:tag-village:query')")
    public CommonResult<List<TagVillageRespVO>> getTagVillageList(@Valid TagVillagePageReqVO pageReqVO) {
        pageReqVO.setPageSize( PageParam.PAGE_SIZE_NONE );
        List<TagVillageDO> list = tagVillageService.getTagVillagePage( pageReqVO ).getList();
        return success( BeanUtils.toBean( list, TagVillageRespVO.class ) );
    }
}