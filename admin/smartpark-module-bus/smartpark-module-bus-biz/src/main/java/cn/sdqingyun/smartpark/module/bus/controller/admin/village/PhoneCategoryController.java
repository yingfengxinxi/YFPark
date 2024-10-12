package cn.sdqingyun.smartpark.module.bus.controller.admin.village;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.PhoneCategoryPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.PhoneCategoryRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.PhoneCategorySaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.PhoneCategoryDO;
import cn.sdqingyun.smartpark.module.bus.service.village.PhoneCategoryService;
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

@Tag(name = "管理后台 - 项目电话类型")
@RestController
@RequestMapping("/bus/phone-category")
@Validated
public class PhoneCategoryController {

    @Resource
    private PhoneCategoryService phoneCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建项目电话类型")
    @PreAuthorize("@ss.hasPermission('bus:phone-category:create')")
    public CommonResult<Long> createPhoneCategory(@Valid @RequestBody PhoneCategorySaveReqVO createReqVO) {
        return success(phoneCategoryService.createPhoneCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目电话类型")
    @PreAuthorize("@ss.hasPermission('bus:phone-category:update')")
    public CommonResult<Boolean> updatePhoneCategory(@Valid @RequestBody PhoneCategorySaveReqVO updateReqVO) {
        phoneCategoryService.updatePhoneCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目电话类型")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:phone-category:delete')")
    public CommonResult<Boolean> deletePhoneCategory(@RequestParam("id") Long id) {
        phoneCategoryService.deletePhoneCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目电话类型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:phone-category:query')")
    public CommonResult<PhoneCategoryRespVO> getPhoneCategory(@RequestParam("id") Long id) {
        PhoneCategoryDO phoneCategory = phoneCategoryService.getPhoneCategory(id);
        return success(BeanUtils.toBean(phoneCategory, PhoneCategoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目电话类型分页")
    @PreAuthorize("@ss.hasPermission('bus:phone-category:query')")
    public CommonResult<PageResult<PhoneCategoryRespVO>> getPhoneCategoryPage(@Valid PhoneCategoryPageReqVO pageReqVO) {
        PageResult<PhoneCategoryDO> pageResult = phoneCategoryService.getPhoneCategoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PhoneCategoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目电话类型 Excel")
    @PreAuthorize("@ss.hasPermission('bus:phone-category:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPhoneCategoryExcel(@Valid PhoneCategoryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PhoneCategoryDO> list = phoneCategoryService.getPhoneCategoryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目电话类型.xls", "数据", PhoneCategoryRespVO.class,
                        BeanUtils.toBean(list, PhoneCategoryRespVO.class));
    }

    @GetMapping("/getpage")
    @Operation(summary = "获得项目电话类型分页")
    @PreAuthorize("@ss.hasPermission('bus:phone-category:query')")
    public CommonResult<PageResult<PhoneCategoryRespVO>> getPhoneCategoryVOPage(@Valid PhoneCategoryPageReqVO pageReqVO) {
        PageResult<PhoneCategoryRespVO> pageResult = phoneCategoryService.getPhoneCategoryVOPage(pageReqVO);
        return success(pageResult);
    }
}