package cn.sdqingyun.smartpark.module.bus.controller.admin.village;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.build.BuildLayerDataVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.build.BuildLayerReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.countNum.CountBuildDataVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import cn.sdqingyun.smartpark.module.bus.service.village.BuildService;
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

@Tag(name = "管理后台 - 项目楼栋")
@RestController
@RequestMapping("/bus/build")
@Validated
public class BuildController {

    @Resource
    private BuildService buildService;

    @PostMapping("/create")
    @Operation(summary = "创建项目楼栋")
    @PreAuthorize("@ss.hasPermission('bus:build:create')")
    public CommonResult<Long> createBuild(@Valid @RequestBody BuildSaveReqVO createReqVO) {
        return success(buildService.createBuild(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目楼栋")
    @PreAuthorize("@ss.hasPermission('bus:build:update')")
    public CommonResult<Boolean> updateBuild(@Valid @RequestBody BuildSaveReqVO updateReqVO) {
        buildService.updateBuild(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目楼栋")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:build:delete')")
    public CommonResult<Boolean> deleteBuild(@RequestParam("id") Long id) {
        buildService.deleteBuild(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目楼栋")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:build:query')")
    public CommonResult<BuildRespVO> getBuild(@RequestParam("id") Long id) {

        return success(buildService.getBuild(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目楼栋分页")
    @PreAuthorize("@ss.hasPermission('bus:build:query')")
    public CommonResult<PageResult<BuildRespVO>> getBuildPage(@Valid BuildPageReqVO pageReqVO) {
        PageResult<BuildRespVO> pageResult = buildService.getBuildVOPage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目楼栋 Excel")
    @PreAuthorize("@ss.hasPermission('bus:build:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBuildExcel(@Valid BuildPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BuildDO> list = buildService.getBuildPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目楼栋.xls", "数据", BuildRespVO.class,
                        BeanUtils.toBean(list, BuildRespVO.class));
    }

    @PostMapping("/projectBuild")
    @Operation(summary = "建筑列表统计")
    @PreAuthorize("@ss.hasPermission('bus:build:query')")
    public CommonResult<CountBuildDataVO> projectBuild(@RequestBody BuildReqVO  buildReqVO) {

        return success(buildService.projectBuild(buildReqVO));
    }

    @PostMapping("/projectProfile")
    @Operation(summary = "剖面图查询")
    @PreAuthorize("@ss.hasPermission('bus:build:query')")
    public CommonResult<BuildLayerDataVO> projectProfile(@RequestBody BuildLayerReqVO buildLayerReqVO) {

        return success(buildService.projectProfile(buildLayerReqVO));
    }

    @GetMapping("/getBuildList")
    @Operation(summary = "获得项目楼栋列表")
    @PreAuthorize("@ss.hasPermission('bus:build:query')")
    public CommonResult<List<BuildRespVO>> getBuildList(@Valid BuildPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BuildDO> list = buildService.getBuildPage(pageReqVO).getList();
        return success(BeanUtils.toBean(list, BuildRespVO.class));
    }
}