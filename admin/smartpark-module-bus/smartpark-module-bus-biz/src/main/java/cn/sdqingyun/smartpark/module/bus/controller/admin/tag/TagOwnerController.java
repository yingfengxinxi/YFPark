package cn.sdqingyun.smartpark.module.bus.controller.admin.tag;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagOwnerPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagOwnerRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo.TagOwnerSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagOwnerDO;
import cn.sdqingyun.smartpark.module.bus.service.tag.TagOwnerService;
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

@Tag(name = "管理后台 - 租客/业主标签")
@RestController
@RequestMapping("/bus/tag-owner")
@Validated
public class TagOwnerController {

    @Resource
    private TagOwnerService tagOwnerService;

    @PostMapping("/create")
    @Operation(summary = "创建租客/业主标签")
    @PreAuthorize("@ss.hasPermission('bus:tag-owner:create')")
    public CommonResult<Long> createTagOwner(@Valid @RequestBody TagOwnerSaveReqVO createReqVO) {
        return success(tagOwnerService.createTagOwner(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新租客/业主标签")
    @PreAuthorize("@ss.hasPermission('bus:tag-owner:update')")
    public CommonResult<Boolean> updateTagOwner(@Valid @RequestBody TagOwnerSaveReqVO updateReqVO) {
        tagOwnerService.updateTagOwner(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除租客/业主标签")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:tag-owner:delete')")
    public CommonResult<Boolean> deleteTagOwner(@RequestParam("id") Long id) {
        tagOwnerService.deleteTagOwner(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得租客/业主标签")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:tag-owner:query')")
    public CommonResult<TagOwnerRespVO> getTagOwner(@RequestParam("id") Long id) {
        TagOwnerDO tagOwner = tagOwnerService.getTagOwner(id);
        return success(BeanUtils.toBean(tagOwner, TagOwnerRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得租客/业主标签分页")
    @PreAuthorize("@ss.hasPermission('bus:tag-owner:query')")
    public CommonResult<PageResult<TagOwnerRespVO>> getTagOwnerPage(@Valid TagOwnerPageReqVO pageReqVO) {
        PageResult<TagOwnerDO> pageResult = tagOwnerService.getTagOwnerPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TagOwnerRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出租客/业主标签 Excel")
    @PreAuthorize("@ss.hasPermission('bus:tag-owner:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTagOwnerExcel(@Valid TagOwnerPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TagOwnerDO> list = tagOwnerService.getTagOwnerPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "租客/业主标签.xls", "数据", TagOwnerRespVO.class,
                        BeanUtils.toBean(list, TagOwnerRespVO.class));
    }

    @GetMapping("/getTagOwnerList")
    @Operation(summary = "获得租客/业主标签分页")
    @PreAuthorize("@ss.hasPermission('bus:tag-owner:query')")
    public CommonResult<List<TagOwnerRespVO>> getTagOwnerList(@Valid TagOwnerPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TagOwnerDO> list = tagOwnerService.getTagOwnerPage(pageReqVO).getList();
        return success(BeanUtils.toBean(list, TagOwnerRespVO.class));
    }
}