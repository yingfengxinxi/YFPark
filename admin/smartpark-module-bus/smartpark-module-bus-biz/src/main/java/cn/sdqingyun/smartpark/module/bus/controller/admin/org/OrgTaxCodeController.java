package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxCodePageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxCodeRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgTaxCodeSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgTaxCodeDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgTaxCodeService;
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

import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.error;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;

import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.*;

@Tag(name = "管理后台 - 发票设置->相关设置->税收分类编码配置")
@RestController
@RequestMapping("bus/orgTaxCode")
@Validated
public class OrgTaxCodeController {

    @Resource
    private OrgTaxCodeService Service;

    @PostMapping("/create")
    @Operation(summary = "创建税收分类编码配置")
    @PreAuthorize("@ss.hasPermission('bus:orgTaxCode:create')")
    @Parameters({
            @Parameter(name = "name", description = "商品或服务名称", required = true, example = "1024"),
            @Parameter(name = "taxCode", description = "税收编号", required = true, example = "1"),
            @Parameter(name = "taxRate", description = "税率", required = true, example = "1")
    })
    public CommonResult<Long> create(@Valid @RequestBody OrgTaxCodeSaveReqVO createReqVO) {
        Boolean checkTaxCode = Service.isCheckTaxCode(createReqVO.getTaxCode(), null);
        if (checkTaxCode) {
            throw new ServiceException(406, "该服务税收编码已添加,请勿重复操作");
        }
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新税收分类编码配置")
    @PreAuthorize("@ss.hasPermission('bus:orgTaxCode:update')")
    @Parameters({
            @Parameter(name = "name", description = "商品或服务名称", required = true, example = "1024"),
            @Parameter(name = "taxCode", description = "税收编号", required = true, example = "1"),
            @Parameter(name = "taxRate", description = "税率", required = true, example = "1"),
            @Parameter(name = "id", description = "列表id", required = true, example = "1")
    })
    public CommonResult<Boolean> update(@Valid @RequestBody OrgTaxCodeSaveReqVO updateReqVO) {
        Boolean checkTaxCode = Service.isCheckTaxCode(updateReqVO.getTaxCode(), updateReqVO.getId());
        if (checkTaxCode) {
            throw new ServiceException(406, "该服务税收编码已添加,请勿重复操作");
        }
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除税收分类编码配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgTaxCode:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得税收分类编码配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgTaxCode:query')")
    public CommonResult<OrgTaxCodeRespVO> get(@RequestParam("id") Long id) {
        OrgTaxCodeDO orgTaxCodeDO = Service.get(id);
        return success(BeanUtils.toBean(orgTaxCodeDO, OrgTaxCodeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得税收分类编码配置分页")
    @PreAuthorize("@ss.hasPermission('bus:orgTaxCode:query')")
    public CommonResult<PageResult<OrgTaxCodeRespVO>> getPage(@Valid OrgTaxCodePageReqVO pageReqVO) {
        PageResult<OrgTaxCodeDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgTaxCodeRespVO.class));
    }


    @GetMapping("/getList")
    @Operation(summary = "获得税收分类编码配置下拉列表")
    @PreAuthorize("@ss.hasPermission('bus:orgTaxCode:getList')")
    public CommonResult<List<OrgTaxCodeDO>> getList() {
        OrgTaxCodePageReqVO pageReqVO = new OrgTaxCodePageReqVO();
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<OrgTaxCodeDO> pageResult = Service.getPage(pageReqVO);
        return success(pageResult.getList());
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出税收分类编码配置 Excel")
    @PreAuthorize("@ss.hasPermission('bus:orgTaxCode:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExcel(@Valid OrgTaxCodePageReqVO pageReqVO,
                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrgTaxCodeDO> list = Service.getPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "税收分类编码配置.xls", "数据", OrgTaxCodeRespVO.class,
                BeanUtils.toBean(list, OrgTaxCodeRespVO.class));
    }

}