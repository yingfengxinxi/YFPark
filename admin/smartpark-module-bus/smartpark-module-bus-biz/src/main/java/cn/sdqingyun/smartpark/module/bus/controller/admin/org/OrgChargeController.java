package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgChargePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgChargeRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgChargeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgChargeDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgChargeService;
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
@Tag(name = "管理后台 - 收费标准")
@RestController
@RequestMapping("/bus/orgCharge")
@Validated
public class OrgChargeController {

    @Resource
    private OrgChargeService Service;

    @PostMapping("/create")
    @Operation(summary = "创建收费标准")
    @PreAuthorize("@ss.hasPermission('bus:orgCharge:create')")
    public CommonResult<Long> create(@Valid @RequestBody OrgChargeSaveReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新收费标准")
    @PreAuthorize("@ss.hasPermission('bus:orgCharge:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody OrgChargeSaveReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除收费标准")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgCharge:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得收费标准")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgCharge:query')")
    public CommonResult<OrgChargeRespVO> get(@RequestParam("id") Long id) {
        OrgChargeDO orgChargeDO = Service.get(id);
        return success(BeanUtils.toBean(orgChargeDO, OrgChargeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得收费标准分页")
    @PreAuthorize("@ss.hasPermission('bus:orgCharge:query')")
    public CommonResult<PageResult<OrgChargeRespVO>> getPage(@Valid OrgChargePageReqVO pageReqVO) {
        PageResult<OrgChargeDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgChargeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出收费标准 Excel")
    @PreAuthorize("@ss.hasPermission('bus:orgCharge:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExcel(@Valid OrgChargePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrgChargeDO> list = Service.getPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "收费标准.xls", "数据", OrgChargeRespVO.class,
                        BeanUtils.toBean(list, OrgChargeRespVO.class));
    }

}