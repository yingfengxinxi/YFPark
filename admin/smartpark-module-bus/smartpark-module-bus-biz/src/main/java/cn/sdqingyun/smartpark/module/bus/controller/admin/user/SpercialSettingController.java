package cn.sdqingyun.smartpark.module.bus.controller.admin.user;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.user.vo.SpercialSettingPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.user.vo.SpercialSettingRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.user.vo.SpercialSettingSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.user.SpercialSettingDO;
import cn.sdqingyun.smartpark.module.bus.service.user.SpercialSettingService;
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

@Tag(name = "管理后台 - 机构用户自定义操作配置")
@RestController
@RequestMapping("/bus/spercial-setting")
@Validated
public class SpercialSettingController {

    @Resource
    private SpercialSettingService spercialSettingService;

    @PostMapping("/create")
    @Operation(summary = "创建机构用户自定义操作配置")
    @PreAuthorize("@ss.hasPermission('bus:spercial-setting:create')")
    public CommonResult<Long> createSpercialSetting(@Valid @RequestBody SpercialSettingSaveReqVO createReqVO) {
        return success(spercialSettingService.createSpercialSetting(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新机构用户自定义操作配置")
    @PreAuthorize("@ss.hasPermission('bus:spercial-setting:update')")
    public CommonResult<Boolean> updateSpercialSetting(@Valid @RequestBody SpercialSettingSaveReqVO updateReqVO) {
        spercialSettingService.updateSpercialSetting(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除机构用户自定义操作配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:spercial-setting:delete')")
    public CommonResult<Boolean> deleteSpercialSetting(@RequestParam("id") Long id) {
        spercialSettingService.deleteSpercialSetting(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得机构用户自定义操作配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:spercial-setting:query')")
    public CommonResult<SpercialSettingRespVO> getSpercialSetting(@RequestParam("id") Long id) {
        SpercialSettingDO spercialSetting = spercialSettingService.getSpercialSetting(id);
        return success(BeanUtils.toBean(spercialSetting, SpercialSettingRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得机构用户自定义操作配置分页")
    @PreAuthorize("@ss.hasPermission('bus:spercial-setting:query')")
    public CommonResult<PageResult<SpercialSettingRespVO>> getSpercialSettingPage(@Valid SpercialSettingPageReqVO pageReqVO) {
        PageResult<SpercialSettingDO> pageResult = spercialSettingService.getSpercialSettingPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SpercialSettingRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出机构用户自定义操作配置 Excel")
    @PreAuthorize("@ss.hasPermission('bus:spercial-setting:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSpercialSettingExcel(@Valid SpercialSettingPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SpercialSettingDO> list = spercialSettingService.getSpercialSettingPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "机构用户自定义操作配置.xls", "数据", SpercialSettingRespVO.class,
                        BeanUtils.toBean(list, SpercialSettingRespVO.class));
    }

    @PostMapping("/saveOrUpdate")
    @Operation(summary = "新增或更新机构用户自定义操作配置")
    @PreAuthorize("@ss.hasPermission('bus:spercial-setting:create')")
    public CommonResult<SpercialSettingDO> saveOrUpdate(@Valid @RequestBody SpercialSettingSaveReqVO createReqVO) {
        if(createReqVO.getId() == null){
            Long id = spercialSettingService.createSpercialSetting( createReqVO );
            return success(spercialSettingService.getSpercialSetting(id));
        }else {
            spercialSettingService.updateSpercialSetting(createReqVO);
            return success(spercialSettingService.getSpercialSetting(createReqVO.getId()));
        }
    }

    @GetMapping("/getList")
    @Operation(summary = "获得机构用户自定义操作配置列表")
    @PreAuthorize("@ss.hasPermission('bus:spercial-setting:query')")
    public CommonResult<List<SpercialSettingRespVO>> getList(@Valid SpercialSettingPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SpercialSettingDO> list = spercialSettingService.getSpercialSettingPage(pageReqVO).getList();
        return success(BeanUtils.toBean(list, SpercialSettingRespVO.class));
    }
}