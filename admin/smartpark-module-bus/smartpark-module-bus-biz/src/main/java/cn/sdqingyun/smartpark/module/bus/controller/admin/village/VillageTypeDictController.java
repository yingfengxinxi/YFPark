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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageTypeDictDO;
import cn.sdqingyun.smartpark.module.bus.service.village.VillageTypeDictService;

@Tag(name = "管理后台 - 项目类型字典")
@RestController
@RequestMapping("/bus/village-type-dict")
@Validated
public class VillageTypeDictController {

    @Resource
    private VillageTypeDictService villageTypeDictService;

    @PostMapping("/create")
    @Operation(summary = "创建项目类型字典")
    @PreAuthorize("@ss.hasPermission('bus:village-type-dict:create')")
    public CommonResult<Long> createVillageTypeDict(@Valid @RequestBody VillageTypeDictSaveReqVO createReqVO) {
        return success(villageTypeDictService.createVillageTypeDict(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目类型字典")
    @PreAuthorize("@ss.hasPermission('bus:village-type-dict:update')")
    public CommonResult<Boolean> updateVillageTypeDict(@Valid @RequestBody VillageTypeDictSaveReqVO updateReqVO) {
        villageTypeDictService.updateVillageTypeDict(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目类型字典")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:village-type-dict:delete')")
    public CommonResult<Boolean> deleteVillageTypeDict(@RequestParam("id") Long id) {
        villageTypeDictService.deleteVillageTypeDict(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目类型字典")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:village-type-dict:query')")
    public CommonResult<VillageTypeDictRespVO> getVillageTypeDict(@RequestParam("id") Long id) {
        VillageTypeDictDO villageTypeDict = villageTypeDictService.getVillageTypeDict(id);
        return success(BeanUtils.toBean(villageTypeDict, VillageTypeDictRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目类型字典分页")
    @PreAuthorize("@ss.hasPermission('bus:village-type-dict:query')")
    public CommonResult<PageResult<VillageTypeDictRespVO>> getVillageTypeDictPage(@Valid VillageTypeDictPageReqVO pageReqVO) {
        PageResult<VillageTypeDictDO> pageResult = villageTypeDictService.getVillageTypeDictPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VillageTypeDictRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目类型字典 Excel")
    @PreAuthorize("@ss.hasPermission('bus:village-type-dict:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportVillageTypeDictExcel(@Valid VillageTypeDictPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VillageTypeDictDO> list = villageTypeDictService.getVillageTypeDictPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目类型字典.xls", "数据", VillageTypeDictRespVO.class,
                        BeanUtils.toBean(list, VillageTypeDictRespVO.class));
    }

}