package cn.sdqingyun.smartpark.module.bus.controller.admin.village;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageCollectionPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageCollectionRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageCollectionSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageCollectionDO;
import cn.sdqingyun.smartpark.module.bus.service.village.VillageCollectionService;
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

@Tag(name = "管理后台 - 项目集合表，方便快速选择")
@RestController
@RequestMapping("/bus/village-collection")
@Validated
public class VillageCollectionController {

    @Resource
    private VillageCollectionService villageCollectionService;

    @PostMapping("/create")
    @Operation(summary = "创建项目集合表，方便快速选择")
    @PreAuthorize("@ss.hasPermission('bus:village-collection:create')")
    public CommonResult<Long> createVillageCollection(@Valid @RequestBody VillageCollectionSaveReqVO createReqVO) {
        return success(villageCollectionService.createVillageCollection(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目集合表，方便快速选择")
    @PreAuthorize("@ss.hasPermission('bus:village-collection:update')")
    public CommonResult<Boolean> updateVillageCollection(@Valid @RequestBody VillageCollectionSaveReqVO updateReqVO) {
        villageCollectionService.updateVillageCollection(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目集合表，方便快速选择")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:village-collection:delete')")
    public CommonResult<Boolean> deleteVillageCollection(@RequestParam("id") Long id) {
        villageCollectionService.deleteVillageCollection(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目集合表，方便快速选择")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:village-collection:query')")
    public CommonResult<VillageCollectionRespVO> getVillageCollection(@RequestParam("id") Long id) {
        VillageCollectionDO villageCollection = villageCollectionService.getVillageCollection(id);
        return success(BeanUtils.toBean(villageCollection, VillageCollectionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目集合表(包含楼宇名称）")
    @PreAuthorize("@ss.hasPermission('bus:village-collection:query')")
    public CommonResult<PageResult<VillageCollectionRespVO>> getVillageCollectionPage(@Valid VillageCollectionPageReqVO pageReqVO) {
        PageResult<VillageCollectionRespVO> pageResult = villageCollectionService.getVillageCollectionRespVOPage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目集合表，方便快速选择 Excel")
    @PreAuthorize("@ss.hasPermission('bus:village-collection:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportVillageCollectionExcel(@Valid VillageCollectionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VillageCollectionDO> list = villageCollectionService.getVillageCollectionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目集合表，方便快速选择.xls", "数据", VillageCollectionRespVO.class,
                        BeanUtils.toBean(list, VillageCollectionRespVO.class));
    }

    @GetMapping("/getList")
    @Operation(summary = "获得项目集合列表")
    @PreAuthorize("@ss.hasPermission('bus:village-collection:query')")
    public CommonResult<List<VillageCollectionRespVO>> getVillageCollectionList(@Valid VillageCollectionRespVO vo) {
        List<VillageCollectionRespVO> villageCollectionList = villageCollectionService.getVillageCollectionList( vo );
        return success(villageCollectionList);
    }
}