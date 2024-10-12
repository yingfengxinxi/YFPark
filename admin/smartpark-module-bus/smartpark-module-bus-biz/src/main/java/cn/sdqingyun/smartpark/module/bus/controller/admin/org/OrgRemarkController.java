package cn.sdqingyun.smartpark.module.bus.controller.admin.org;


import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgRemarkPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgRemarkRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgRemarkSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgRemarkDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgRemarkService;
import io.swagger.v3.oas.annotations.Parameters;
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

@Tag(name = "管理后台 - 机构业务备注")
@RestController
@RequestMapping("/bus/orgRemark/")
@Validated
public class OrgRemarkController {

    @Resource
    private OrgRemarkService Service;

    @PostMapping("/create")
    @Operation(summary = "创建机构业务备注")
    @PreAuthorize("@ss.hasPermission('bus:orgRemark:create')")
    @Parameters({
            @Parameter(name = "businessId", description = "业务id;合同id/账单id/等等", required = true),
            @Parameter(name = "remark", description = "备注", required = true),
            @Parameter(name = "type", description = "类型1账单备注", required = true)
    })
    public CommonResult<Long> create(@Valid @RequestBody OrgRemarkSaveReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新机构业务备注")
    @PreAuthorize("@ss.hasPermission('bus:orgRemark:update')")
    @Parameters({
            @Parameter(name = "id", description = "列表id", required = true),
            @Parameter(name = "businessId", description = "业务id;合同id/账单id/等等", required = true),
            @Parameter(name = "remark", description = "备注", required = true),
            @Parameter(name = "type", description = "类型1账单备注", required = true)
    })
    public CommonResult<Boolean> update(@Valid @RequestBody OrgRemarkSaveReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除机构业务备注")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgRemark:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得机构业务备注")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgRemark:query')")
    public CommonResult<OrgRemarkRespVO> get(@RequestParam("id") Long id) {
        OrgRemarkDO orgRemarkDO = Service.get(id);
        return success(BeanUtils.toBean(orgRemarkDO, OrgRemarkRespVO.class));
    }


    @GetMapping("/getList")
    @Operation(summary = "获得机构业务备注列表")
    @PreAuthorize("@ss.hasPermission('bus:orgRemark:query')")
    public CommonResult<List<OrgRemarkRespVO>> getList(@Valid OrgRemarkPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrgRemarkDO> list = Service.getPage(pageReqVO).getList();
        return success(BeanUtils.toBean(list, OrgRemarkRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得机构业务备注分页")
    @PreAuthorize("@ss.hasPermission('bus:orgRemark:query')")
    @Parameters({
            @Parameter(name = "businessId", description = "业务id;合同id/账单id/等等", required = true),
            @Parameter(name = "type", description = "类型1账单备注", required = true)
    })
    public CommonResult<PageResult<OrgRemarkRespVO>> getPage(@Valid OrgRemarkPageReqVO pageReqVO) {
        PageResult<OrgRemarkDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgRemarkRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出机构业务备注 Excel")
    @PreAuthorize("@ss.hasPermission('bus:orgRemark:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExcel(@Valid OrgRemarkPageReqVO pageReqVO,
                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrgRemarkDO> list = Service.getPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "机构业务备注.xls", "数据", OrgRemarkRespVO.class,
                BeanUtils.toBean(list, OrgRemarkRespVO.class));
    }

}