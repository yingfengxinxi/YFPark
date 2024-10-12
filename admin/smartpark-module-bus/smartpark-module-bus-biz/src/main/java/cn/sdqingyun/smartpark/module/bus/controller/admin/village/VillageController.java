package cn.sdqingyun.smartpark.module.bus.controller.admin.village;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.datapermission.core.annotation.DataPermission;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageInsertReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillagePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.countNum.CountDataVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.countNum.ProjectOverviewVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.villagebuild.BuildArrRespVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillageDO;
import cn.sdqingyun.smartpark.module.bus.service.village.VillageService;
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

@Tag(name = "管理后台 - 项目")
@RestController
@RequestMapping("/bus/village")
@Validated
public class VillageController {

    @Resource
    private VillageService villageService;

    @PostMapping("/create")
    @Operation(summary = "创建项目")
    @PreAuthorize("@ss.hasPermission('bus:village:create')")
    public CommonResult<Long> createVillage(@Valid @RequestBody VillageSaveReqVO createReqVO) {
        return success(villageService.createVillage(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目")
    @PreAuthorize("@ss.hasPermission('bus:village:update')")
    public CommonResult<Boolean> updateVillage(@Valid @RequestBody VillageSaveReqVO updateReqVO) {
        villageService.updateVillage(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:village:delete')")
    public CommonResult<Boolean> deleteVillage(@RequestParam("id") Long id) {
        villageService.deleteVillage(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:village:query')")
    public CommonResult<VillageRespVO> getVillage(@RequestParam("id") Long id) {
        VillageDO village = villageService.getVillage(id);
        return success(BeanUtils.toBean(village, VillageRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目分页")
    @PreAuthorize("@ss.hasPermission('bus:village:query')")
    public CommonResult<PageResult<VillageRespVO>> getVillagePage(@Valid VillagePageReqVO pageReqVO) {
        PageResult<VillageDO> pageResult = villageService.getVillagePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VillageRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目 Excel")
    @PreAuthorize("@ss.hasPermission('bus:village:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportVillageExcel(@Valid VillagePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VillageDO> list = villageService.getVillagePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目.xls", "数据", VillageRespVO.class,
                        BeanUtils.toBean(list, VillageRespVO.class));
    }

    @PostMapping("/saveVillage")
    @Operation(summary = "新增保存项目")
    @PreAuthorize("@ss.hasPermission('bus:village:create')")
    public CommonResult<String> saveVillage(@Valid @RequestBody VillageInsertReqVO createReqVO) {
        return success(villageService.saveVillage(createReqVO));
    }

    @PostMapping("/villageAndBuildList")
    @Operation(summary = "房态管理-获取项目楼宇列表")
    @PreAuthorize("@ss.hasPermission('bus:village:query')")
    public CommonResult<BuildArrRespVO> villageAndBuildList(@RequestBody VillageReqVO villageReqVO) {
        BuildArrRespVO village = villageService.villageAndBuildList(villageReqVO);
        return success(village);
    }

    @PostMapping("/projectManage")
    @Operation(summary = "小区列表-获取小区列表统计")
    @PreAuthorize("@ss.hasPermission('bus:village:query')")
    public CommonResult<CountDataVO> projectManage(@RequestBody VillageReqVO villageReqVO) {
        return success(villageService.projectManage(villageReqVO));
    }

    @GetMapping("/projectOverview")
    @Operation(summary = "项目概况统计")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:village:query')")
    public CommonResult<ProjectOverviewVO> projectOverview(@RequestParam("id") Long id) {
        return success(villageService.projectOverview(id));
    }

    @GetMapping("/getVillageList")
    @Operation(summary = "获得项目列表")
    @PreAuthorize("@ss.hasPermission('bus:village:query')")
    public CommonResult<List<VillageRespVO>> getVillageList(@Valid VillagePageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VillageDO> list = villageService.getVillagePage(pageReqVO).getList();
        return success(BeanUtils.toBean(list, VillageRespVO.class));
    }

    @PostMapping("/villageAndBuildListExDate")
    @Operation(summary = "房态管理-获取项目楼宇列表")
    @PreAuthorize("@ss.hasPermission('bus:village:query')")
    @DataPermission(enable = false)
    public CommonResult<BuildArrRespVO> villageAndBuildListExDate(@RequestBody VillageReqVO villageReqVO) {
        BuildArrRespVO village = villageService.villageAndBuildList(villageReqVO);
        return success(village);
    }
}