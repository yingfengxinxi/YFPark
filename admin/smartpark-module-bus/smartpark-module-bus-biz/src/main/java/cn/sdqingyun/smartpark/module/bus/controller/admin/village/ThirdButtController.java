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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.ThirdButtDO;
import cn.sdqingyun.smartpark.module.bus.service.village.ThirdButtService;

@Tag(name = "管理后台 - 第三方数据对接（目前用于智慧社区系统，全功能版）")
@RestController
@RequestMapping("/bus/third-butt")
@Validated
public class ThirdButtController {

    @Resource
    private ThirdButtService thirdButtService;

    @PostMapping("/create")
    @Operation(summary = "创建第三方数据对接（目前用于智慧社区系统，全功能版）")
    @PreAuthorize("@ss.hasPermission('bus:third-butt:create')")
    public CommonResult<Long> createThirdButt(@Valid @RequestBody ThirdButtSaveReqVO createReqVO) {
        return success(thirdButtService.createThirdButt(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新第三方数据对接（目前用于智慧社区系统，全功能版）")
    @PreAuthorize("@ss.hasPermission('bus:third-butt:update')")
    public CommonResult<Boolean> updateThirdButt(@Valid @RequestBody ThirdButtSaveReqVO updateReqVO) {
        thirdButtService.updateThirdButt(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除第三方数据对接（目前用于智慧社区系统，全功能版）")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:third-butt:delete')")
    public CommonResult<Boolean> deleteThirdButt(@RequestParam("id") Long id) {
        thirdButtService.deleteThirdButt(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得第三方数据对接（目前用于智慧社区系统，全功能版）")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:third-butt:query')")
    public CommonResult<ThirdButtRespVO> getThirdButt(@RequestParam("id") Long id) {
        ThirdButtDO thirdButt = thirdButtService.getThirdButt(id);
        return success(BeanUtils.toBean(thirdButt, ThirdButtRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得第三方数据对接（目前用于智慧社区系统，全功能版）分页")
    @PreAuthorize("@ss.hasPermission('bus:third-butt:query')")
    public CommonResult<PageResult<ThirdButtRespVO>> getThirdButtPage(@Valid ThirdButtPageReqVO pageReqVO) {
        PageResult<ThirdButtDO> pageResult = thirdButtService.getThirdButtPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ThirdButtRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出第三方数据对接（目前用于智慧社区系统，全功能版） Excel")
    @PreAuthorize("@ss.hasPermission('bus:third-butt:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportThirdButtExcel(@Valid ThirdButtPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ThirdButtDO> list = thirdButtService.getThirdButtPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "第三方数据对接（目前用于智慧社区系统，全功能版）.xls", "数据", ThirdButtRespVO.class,
                        BeanUtils.toBean(list, ThirdButtRespVO.class));
    }

}