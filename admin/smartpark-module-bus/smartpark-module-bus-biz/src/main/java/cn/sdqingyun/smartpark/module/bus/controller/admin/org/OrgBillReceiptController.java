package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.*;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptDO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillReceiptService;
import com.alibaba.nacos.common.utils.CollectionUtils;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.Lists;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;

import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 机构账单收据")
@RestController
@RequestMapping("/bus/orgBillReceipt")
@Validated
public class OrgBillReceiptController {

    @Resource
    private OrgBillReceiptService Service;


    /**
     * {
     * "receiptRuleId": 14,
     * "receiptRuleName": "1",
     * "build": 156,
     * "billId": 21,
     * "receiptNumber": "11001",
     * "issuerTime": "2024-07-22",
     * "remitType": "0",
     * "invoicedAmount": 0.90,
     * "applicationInvoicedAmount": 0.90,
     * "costType": "0",
     * "costTypeName": null,
     * "costName": "租金",
     * "collectionCompanyId":7 ,
     * "remark": "我是备注",
     * "paymentCompanyId": null,
     * "paymentCompany": null,
     * "paymentUname": "123",
     * "paymentAddress": "地址",
     * "paymentPhone": "1213213",
     * "collectionUname": "请问",
     * "collectionAddress": "qwe",
     * "collectionPhone": "123qwe",
     * "numberType": 1
     * }
     *
     * @param lssueVO
     * @return
     */
    @PostMapping("/create")
    @Operation(summary = "编辑->保存收据")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceipt:create')")
    @Parameters({
            @Parameter(name = "receiptRuleId", description = "编号规则id", required = true),
            @Parameter(name = "receiptRuleName", description = "编号规则名称", required = true),
            @Parameter(name = "build", description = "楼宇id", required = true),
            @Parameter(name = "billId", description = "账单id", required = true),
            @Parameter(name = "receiptNumber", description = "收据编号【两种生成方式1、调用生成编号接口生成2、在编辑中手动输入】", required = true),
            @Parameter(name = "issuerTime", description = "开据时间", required = false),
            @Parameter(name = "remitType", description = "汇款方式【字典值REMIT_TYPE】", required = false),
            @Parameter(name = "invoicedAmount", description = "可开据金额", required = true),
            @Parameter(name = "applicationInvoicedAmount", description = "申请可开据金额", required = true),
            @Parameter(name = "costType", description = "费用类型", required = true),
            @Parameter(name = "costName", description = "费用名称", required = true),
            @Parameter(name = "collectionCompanyId", description = "收款单位", required = true),
            @Parameter(name = "remark", description = "备注", required = false),
            @Parameter(name = "paymentCompanyId", description = "交款方-交款单位Id", required = true),
            @Parameter(name = "paymentCompany", description = "交款方-交款单位名称", required = true),
            @Parameter(name = "paymentUname", description = "交款方-交款人", required = false),
            @Parameter(name = "paymentAddress", description = "交款方-交款地址", required = false),
            @Parameter(name = "paymentPhone", description = "交款方-电话", required = false),
            @Parameter(name = "collectionUname", description = "收款方-收款人", required = false),
            @Parameter(name = "collectionAddress", description = "收款方-地址", required = false),
            @Parameter(name = "collectionPhone", description = "收款方-电话", required = false),
            @Parameter(name = "numberType", description = "生成编号方式;1=规则生成;2=自定义【调用接口后生成编号是1，从编辑输入编号是2】", required = true)
    })
    public CommonResult<Long> create(@Valid @RequestBody LssueVO lssueVO) {
        try {
            return success(Service.create(lssueVO));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(406,e.getMessage());
        }
    }

    @DeleteMapping("/isCheckReceipt")
    @Operation(summary = "账单详情->开具收据按钮校验")
    @Parameter(name = "billId", description = "账单id编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceipt:isCheckReceipt')")
    public CommonResult<Boolean> isCheckReceipt(@RequestParam("billId") Long billId) {
        Service.isCheckReceipt(billId);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除机构账单收据")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceipt:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "收据记录详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceipt:query')")
    public CommonResult<OrgBillReceiptRespVO> get(@RequestParam("id") Long id) {
        OrgBillReceiptDO billReceiptDO = Service.get(id);
        return success(BeanUtils.toBean(billReceiptDO, OrgBillReceiptRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "财务-收据记录分页")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceipt:query')")
    @Parameters({
            @Parameter(name = "pageSize", description = "显示条数", required = true),
            @Parameter(name = "pageNo", description = "显示页数", required = true),
            @Parameter(name = "paymentCompany", description = "交款单位", required = false),
            @Parameter(name = "receiptNumber", description = "收据编号", required = false),
            @Parameter(name = "collectionCompany", description = "收款单位", required = false),
            @Parameter(name = "status", description = "收据状态【数据字典】", required = false),
            @Parameter(name = "costName", description = "费用名称", required = false),
            @Parameter(name = "startCreateTime", description = "创建起始时间", required = false),
            @Parameter(name = "endCreateTime", description = "创建结束时间", required = false),
            @Parameter(name = "issuerUid", description = "开据人【下拉】", required = false),
    })
    public CommonResult<PageResult<OrgBillReceiptRespVO>> getPage(@Valid OrgBillReceiptPageReqVO pageReqVO) {
        PageResult<OrgBillReceiptDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgBillReceiptRespVO.class));
    }


    @GetMapping("/getTopMoney")
    @Operation(summary = "财务-收据记录分页-顶部统计")
    @PreAuthorize("@ss.hasPermission('bus:orgBillReceipt:query')")
    @Parameters({
            @Parameter(name = "paymentCompany", description = "交款单位", required = false),
            @Parameter(name = "receiptNumber", description = "收据编号", required = false),
            @Parameter(name = "collectionCompany", description = "收款单位", required = false),
            @Parameter(name = "status", description = "收据状态【数据字典】", required = false),
            @Parameter(name = "costName", description = "费用名称", required = false),
            @Parameter(name = "startCreateTime", description = "创建起始时间", required = false),
            @Parameter(name = "endCreateTime", description = "创建结束时间", required = false),
    })
    public CommonResult<?> getTopMoney(@Valid OrgBillReceiptPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<OrgBillReceiptDO> pageResult = Service.getPage(pageReqVO);
        List<OrgBillReceiptDO> list = pageResult.getList();
        Map<String, Object> map = new HashMap<>();
        //开据金额
        BigDecimal receiptAmount = new BigDecimal("0.00");
        Long wreceiptNum = 0L;
        if (CollectionUtils.isNotEmpty(list)) {
            receiptAmount = list.stream().
                    map(aa -> aa.getApplyReceiptAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);

            //未发出收据数量
            wreceiptNum = list.stream().filter(aa -> aa.getStatus().equals("6")).count();
            wreceiptNum = list.stream().filter(aa -> aa.getStatus().equals("6")).count();
        }
        //开据金额
        map.put("receiptAmount", receiptAmount);
        //未发出数量
        map.put("wreceiptNum", wreceiptNum);
        //开据数量
        map.put("receiptNum", list.size());
        return success(map);
    }

    @GetMapping("/getReceiptRecord")
    @Operation(summary = "账单详情中收据记录")
    @Parameters({
            @Parameter(name = "billId", description = "账单id", required = true)
    })
    public CommonResult<PageResult<OrgBillReceiptRespVO>> getReceiptRecord(
            @RequestParam("billId") Long billId
    ) {
        OrgBillReceiptPageReqVO pageReqVO = new OrgBillReceiptPageReqVO();
        pageReqVO.setBillId(billId);
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<OrgBillReceiptDO> pageResult = Service.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrgBillReceiptRespVO.class));
    }

    /**
     * 账单列表-开据收据
     */
    @Operation(summary = "账单列表-开据收据内容【申请开据的收据】")
    @GetMapping("lssue")
    @Parameter(name = "billIds", description = "账单id集合", required = true, example = "1")
    public CommonResult<?> lssue(@RequestParam("billIds") List<Long> billIds) {

        return Service.lssue(billIds);
    }

    /**
     * 账单列表-开据收据
     */
    @Operation(summary = "账单列表-开据收据-生成编号")
    @GetMapping("getReceiptNumber")
    @Parameter(name = "buildId", description = "楼宇id", required = true, example = "1")
    public CommonResult<?> getReceiptNumber(@RequestParam("buildId") Long buildId) {

        return Service.getReceiptNumber(buildId);
    }

    /**
     * 账单列表-开据收据
     */
    @Operation(summary = "账单列表-开据收据-发出")
    @GetMapping("send")
    @Parameter(name = "id", description = "id", required = true, example = "1")
    public CommonResult<?> send(@RequestParam("id") Long id) {
        Service.send(id);
        return CommonResult.success("发出成功");
    }


    /**
     * 账单列表-开据收据
     */
    @Operation(summary = "账单列表-开据收据-收回")
    @GetMapping("recovery")
    @Parameter(name = "id", description = "id", required = true, example = "1")
    public CommonResult<?> recovery(@RequestParam("id") Long id) {
        Service.recovery(id);
        return CommonResult.success("收回成功");
    }


    /**
     * 账单列表-开据收据
     */
    @Operation(summary = "账单列表-开据收据-作废")
    @GetMapping("toVoid")
    @Parameter(name = "id", description = "id", required = true, example = "1")
    public CommonResult<?> toVoid(@RequestParam("id") Long id) {
        Service.toVoid(id);
        return CommonResult.success("作废成功");
    }

    /**
     * 账单列表-开据收据
     */
    @Operation(summary = "账单列表-开据收据-生成")
    @GetMapping("generate")
    @Parameters({
            @Parameter(name = "id", description = "收据id", required = true, example = "1"),
            @Parameter(name = "applyTemplateId", description = "收据模板id", required = true, example = "1")
    })
    public CommonResult<?> generate(@RequestParam("id") Long id, @RequestParam("applyTemplateId") Long applyTemplateId) {

        try {
            return CommonResult.success(Service.generate(id, applyTemplateId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<ReceiptImportVO> receiptImportList = Lists.newArrayList();
        // 输出
        ExcelUtils.write(response, "收据导入模板.xls", "收据列表", ReceiptImportVO.class, receiptImportList);
    }

    @PostMapping("/import")
    @Operation(summary = "导入收据")
    public CommonResult<ReceiptImportVO> importExcel(@Valid ReceiptImportVO receiptImportVO) {
        try {
            List<ReceiptImportVO> list = ExcelUtils.read(receiptImportVO.getFile(), ReceiptImportVO.class);
            Service.importReceiptList(list);
            return CommonResult.success(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}