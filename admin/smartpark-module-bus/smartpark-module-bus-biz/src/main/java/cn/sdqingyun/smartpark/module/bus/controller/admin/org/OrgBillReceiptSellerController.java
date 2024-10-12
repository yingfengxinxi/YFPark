package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptSellerPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptSellerRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.OrgBillReceiptSellerSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptSellerDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillReceiptSellerService;
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
@Tag(name = "管理后台 - 收据收款方信息")
@RestController
@RequestMapping("/bus/orgBillReceiptSeller")
@Validated
public class OrgBillReceiptSellerController {

    @Resource
    private OrgBillReceiptSellerService Service;

    @PostMapping("/create")
    @Operation(summary = "创建收据收款方信息")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptSeller:create')")
    public CommonResult<Long> create(@Valid @RequestBody OrgBillReceiptSellerSaveReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新收据收款方信息")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptSeller:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody OrgBillReceiptSellerSaveReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除收据收款方信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptSeller:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得收据收款方信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptSeller:query')")
    public CommonResult<OrgBillReceiptSellerRespVO> get(@RequestParam("id") Long id) {
        OrgBillReceiptSellerDO billReceiptSellerDO = Service.get(id);
        return success(BeanUtils.toBean(billReceiptSellerDO, OrgBillReceiptSellerRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得收据收款方信息分页")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceiptSeller:query')")
    public CommonResult<PageResult<OrgBillReceiptSellerRespVO>> getPage(@Valid OrgBillReceiptSellerPageReqVO pageReqVO) {
        PageResult<OrgBillReceiptSellerDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgBillReceiptSellerRespVO.class));
    }


    @GetMapping("/getByBuildsList")
    @Operation(summary = "开据收据企业下拉")
    public CommonResult<List<OrgBillReceiptSellerDO>> getByBuildsList(List<Long>buildBinds) {
        List<OrgBillReceiptSellerDO> receiptSellerDOList = Service.getByBuildsList(buildBinds);
        return success(receiptSellerDOList);
    }


}