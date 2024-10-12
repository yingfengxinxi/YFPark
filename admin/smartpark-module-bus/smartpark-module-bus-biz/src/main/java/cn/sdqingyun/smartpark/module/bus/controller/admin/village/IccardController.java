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
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.IccardDO;
import cn.sdqingyun.smartpark.module.bus.service.village.IccardService;

@Tag(name = "管理后台 - 项目IC卡表（可能会绑定人员，因不同设备需要而定）")
@RestController
@RequestMapping("/bus/iccard")
@Validated
public class IccardController {

    @Resource
    private IccardService iccardService;

    @PostMapping("/create")
    @Operation(summary = "创建项目IC卡表（可能会绑定人员，因不同设备需要而定）")
    @PreAuthorize("@ss.hasPermission('bus:iccard:create')")
    public CommonResult<Long> createIccard(@Valid @RequestBody IccardSaveReqVO createReqVO) {
        return success(iccardService.createIccard(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目IC卡表（可能会绑定人员，因不同设备需要而定）")
    @PreAuthorize("@ss.hasPermission('bus:iccard:update')")
    public CommonResult<Boolean> updateIccard(@Valid @RequestBody IccardSaveReqVO updateReqVO) {
        iccardService.updateIccard(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目IC卡表（可能会绑定人员，因不同设备需要而定）")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:iccard:delete')")
    public CommonResult<Boolean> deleteIccard(@RequestParam("id") Long id) {
        iccardService.deleteIccard(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目IC卡表（可能会绑定人员，因不同设备需要而定）")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:iccard:query')")
    public CommonResult<IccardRespVO> getIccard(@RequestParam("id") Long id) {
        IccardDO iccard = iccardService.getIccard(id);
        return success(BeanUtils.toBean(iccard, IccardRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目IC卡表（可能会绑定人员，因不同设备需要而定）分页")
    @PreAuthorize("@ss.hasPermission('bus:iccard:query')")
    public CommonResult<PageResult<IccardRespVO>> getIccardPage(@Valid IccardPageReqVO pageReqVO) {
        PageResult<IccardDO> pageResult = iccardService.getIccardPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, IccardRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目IC卡表（可能会绑定人员，因不同设备需要而定） Excel")
    @PreAuthorize("@ss.hasPermission('bus:iccard:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportIccardExcel(@Valid IccardPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<IccardDO> list = iccardService.getIccardPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目IC卡表（可能会绑定人员，因不同设备需要而定）.xls", "数据", IccardRespVO.class,
                        BeanUtils.toBean(list, IccardRespVO.class));
    }

}