package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.tenant.core.context.TenantContextHolder;
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

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.BusOrgDO;
import cn.sdqingyun.smartpark.module.bus.service.org.BusOrgService;

@Tag(name = "管理后台 - 机构")
@RestController
@RequestMapping("/bus/org")
@Validated
public class BusOrgController {

    @Resource
    private BusOrgService orgService;

    @PostMapping("/create")
    @Operation(summary = "创建机构")
    @PreAuthorize("@ss.hasPermission('bus:org:create')")
    public CommonResult<Long> createOrg(@Valid @RequestBody BusOrgSaveReqVO createReqVO) {
        //保持保持机构与系统租户绑定
        createReqVO.setId( TenantContextHolder.getTenantId() );
        return success(orgService.createOrg(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新机构")
    @PreAuthorize("@ss.hasPermission('bus:org:update')")
    public CommonResult<Boolean> updateOrg(@Valid @RequestBody BusOrgSaveReqVO updateReqVO) {
        orgService.updateOrg(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除机构")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:org:delete')")
    public CommonResult<Boolean> deleteOrg(@RequestParam("id") Long id) {
        orgService.deleteOrg(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得机构")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:org:query')")
    public CommonResult<BusOrgRespVO> getOrg(@RequestParam("id") Long id) {
        BusOrgDO org = orgService.getOrg(id);
        return success(BeanUtils.toBean(org, BusOrgRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得机构分页")
    @PreAuthorize("@ss.hasPermission('bus:org:query')")
    public CommonResult<PageResult<BusOrgRespVO>> getOrgPage(@Valid BusOrgPageReqVO pageReqVO) {
        PageResult<BusOrgDO> pageResult = orgService.getOrgPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BusOrgRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出机构 Excel")
    @PreAuthorize("@ss.hasPermission('bus:org:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOrgExcel(@Valid BusOrgPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BusOrgDO> list = orgService.getOrgPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "机构.xls", "数据", BusOrgRespVO.class,
                        BeanUtils.toBean(list, BusOrgRespVO.class));
    }

    @GetMapping("/getOrgByCode")
    @Operation(summary = "根据租户code获得机构")
    @PreAuthorize("@ss.hasPermission('bus:org:query')")
    public CommonResult<BusOrgRespVO> getOrgByCode() {
        BusOrgDO org = orgService.getOrgByCode();
        return success(BeanUtils.toBean(org, BusOrgRespVO.class));
    }
}