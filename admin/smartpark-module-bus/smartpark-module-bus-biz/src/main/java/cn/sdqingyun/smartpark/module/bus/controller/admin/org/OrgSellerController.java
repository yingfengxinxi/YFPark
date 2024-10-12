package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgSellerPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgSellerRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgSellerSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgSellerDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgSellerService;
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

@Tag(name = "管理后台 - 机构楼宇发票设置->相关设置->售方信息")
@RestController
@RequestMapping("/bus/orgSeller")
@Validated
public class OrgSellerController {

    @Resource
    private OrgSellerService Service;


    /**
     * {
     * "companyName": "公司名称",
     * "taxpayerNumber": "识别号",
     * "bank": "建设银行",
     * "bankAccount":"123213213",
     * "phone": "18606341932",
     * "address":"开票地址"
     * }
     *
     * @param createReqVO
     * @return
     */
    @PostMapping("/create")
    @Operation(summary = "创建机构楼宇售方信息(发票设置)")
    @PreAuthorize("@ss.hasPermission('bus:orgSeller:create')")
    @Parameters({
            @Parameter(name = "companyName", description = "公司名称", required = true, example = "1024"),
            @Parameter(name = "taxpayerNumber", description = "纳税人识别号", required = true, example = "1"),
            @Parameter(name = "bank", description = "开户行", required = true, example = "1"),
            @Parameter(name = "bankAccount", description = "开户行账号", required = true, example = "1"),
            @Parameter(name = "phone", description = "手机号", required = true, example = "1"),
            @Parameter(name = "address", description = "开票地址", required = true, example = "1")
    })
    public CommonResult<Long> create(@Valid @RequestBody OrgSellerSaveReqVO createReqVO) {
        Boolean checkName = Service.isCheckName(createReqVO.getCompanyName(), null);
        if (checkName) {
            throw new ServiceException(406, "该售方公司和账户信息已添加,请勿重复操作");
        }
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新机构楼宇售方信息(发票设置)")
    @PreAuthorize("@ss.hasPermission('bus:orgSeller:update')")
    @Parameters({
            @Parameter(name = "companyName", description = "公司名称", required = true, example = "1024"),
            @Parameter(name = "taxpayerNumber", description = "纳税人识别号", required = true, example = "1"),
            @Parameter(name = "bank", description = "开户行", required = true, example = "1"),
            @Parameter(name = "bankAccount", description = "开户行账号", required = true, example = "1"),
            @Parameter(name = "phone", description = "手机号", required = true, example = "1"),
            @Parameter(name = "address", description = "开票地址", required = true, example = "1"),
            @Parameter(name = "id", description = "列表id", required = true, example = "1")
    })
    public CommonResult<Boolean> update(@Valid @RequestBody OrgSellerSaveReqVO updateReqVO) {
        Boolean checkName = Service.isCheckName(updateReqVO.getCompanyName(), updateReqVO.getId());
        if (checkName) {
            throw new ServiceException(406, "该售方公司和账户信息已添加,请勿重复操作");
        }
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除机构楼宇售方信息(发票设置)")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgSeller:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得机构楼宇售方信息(发票设置)")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgSeller:query')")
    public CommonResult<OrgSellerRespVO> get(@RequestParam("id") Long id) {
        OrgSellerDO orgSellerDO = Service.get(id);
        return success(BeanUtils.toBean(orgSellerDO, OrgSellerRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得机构楼宇售方信息(发票设置)分页")
    @PreAuthorize("@ss.hasPermission('bus:orgSeller:query')")
    @Parameters({
            @Parameter(name = "pageSize", description = "显示条数", required = true, example = "1024"),
            @Parameter(name = "pageNo", description = "显示页数", required = true, example = "1")
    })
    public CommonResult<PageResult<OrgSellerRespVO>> getPage(@Valid OrgSellerPageReqVO pageReqVO) {
        PageResult<OrgSellerDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgSellerRespVO.class));
    }


    @GetMapping("/getList")
    @Operation(summary = "获得机构楼宇售方信息(发票设置)下拉列表")
    @PreAuthorize("@ss.hasPermission('bus:orgSeller:query')")
    public CommonResult<PageResult<OrgSellerRespVO>> getList() {
        OrgSellerPageReqVO pageReqVO = new OrgSellerPageReqVO();
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<OrgSellerDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgSellerRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出机构楼宇售方信息(发票设置) Excel")
    @PreAuthorize("@ss.hasPermission('bus:orgSeller:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExcel(@Valid OrgSellerPageReqVO pageReqVO,
                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrgSellerDO> list = Service.getPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "机构楼宇售方信息(发票设置).xls", "数据", OrgSellerRespVO.class,
                BeanUtils.toBean(list, OrgSellerRespVO.class));
    }

}