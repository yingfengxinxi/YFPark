package cn.sdqingyun.smartpark.module.bus.controller.admin.pay;

import cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo.PayOrderBillPageReqVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo.PayOrderBillRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo.PayOrderBillSaveReqVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.pay.PayOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.service.pay.PayOrderBillService;
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
@Tag(name = "管理后台 - 支付订单和账单表中间表")
@RestController
@RequestMapping("/bus/payOrderBill")
@Validated
public class PayOrderBillController {

    @Resource
    private PayOrderBillService Service;

    @PostMapping("/create")
    @Operation(summary = "创建支付订单和账单表中间表	")
    @PreAuthorize("@ss.hasPermission('bus:bus:payOrderBillcreate')")
    public CommonResult<Long> create(@Valid @RequestBody PayOrderBillSaveReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新支付订单和账单表中间表	")
    @PreAuthorize("@ss.hasPermission('bus:payOrderBill:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PayOrderBillSaveReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除支付订单和账单表中间表	")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:payOrderBill:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得支付订单和账单表中间表	")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:payOrderBill:query')")
    public CommonResult<PayOrderBillRespVO> get(@RequestParam("id") Long id) {
        PayOrderBillDO payOrderBillDO = Service.get(id);
        return success(BeanUtils.toBean(payOrderBillDO, PayOrderBillRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得支付订单和账单表中间表	分页")
    @PreAuthorize("@ss.hasPermission('bus:payOrderBill:query')")
    public CommonResult<PageResult<PayOrderBillRespVO>> getPage(@Valid PayOrderBillPageReqVO pageReqVO) {
        PageResult<PayOrderBillDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PayOrderBillRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出支付订单和账单表中间表	 Excel")
    @PreAuthorize("@ss.hasPermission('bus:payOrderBill:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExcel(@Valid PayOrderBillPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PayOrderBillDO> list = Service.getPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "支付订单和账单表中间表	.xls", "数据", PayOrderBillRespVO.class,
                        BeanUtils.toBean(list, PayOrderBillRespVO.class));
    }

}