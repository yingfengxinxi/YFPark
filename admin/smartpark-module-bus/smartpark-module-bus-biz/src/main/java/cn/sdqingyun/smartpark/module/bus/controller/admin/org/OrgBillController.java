package cn.sdqingyun.smartpark.module.bus.controller.admin.org;

import cn.sdqingyun.smartpark.framework.apilog.core.annotation.ApiAccessLog;
import cn.sdqingyun.smartpark.framework.common.exception.ServiceException;
import cn.sdqingyun.smartpark.framework.common.pojo.CommonResult;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import cn.sdqingyun.smartpark.framework.common.pojo.PageResult;
import cn.sdqingyun.smartpark.framework.common.util.object.BeanUtils;
import cn.sdqingyun.smartpark.framework.excel.core.util.ExcelUtils;
import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.ContractListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.BillCollectionAllListVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo.BillImportVO;
import cn.sdqingyun.smartpark.module.bus.service.org.OrgBillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.sdqingyun.smartpark.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.sdqingyun.smartpark.framework.common.pojo.CommonResult.success;

/**
 * @Author lvzy
 * @Date 2024/7/15 15:17
 */
@RestController
@RequestMapping("/bus/bill")
public class OrgBillController {

    @Autowired
    private OrgBillService orgBillService;

    /**
     * @param pageReqVO
     * @return
     */
    @GetMapping("/getBillCollectionAllListPage")
    @Operation(summary = "财务所有账单列表")
    @Parameters({
            @Parameter(name = "contractNumber", description = "合同编号", required = true, example = "1024"),
            @Parameter(name = "billType", description = "账单类型(1=收款账单2=付款账单)", required = true, example = "1024"),
            @Parameter(name = "isShow", description = "已显示未到应收期账单(false隐藏true显示)", required = true, example = "1024"),
            @Parameter(name = "startPayDate", description = "开始应收/付时间", required = false, example = "1024"),
            @Parameter(name = "endPayDate", description = "结束应收/付时间", required = false, example = "1"),
            @Parameter(name = "billStatusList", description = "账单状态集合【数据字典:BILL_STATUS】", required = false, example = "1"),
            @Parameter(name = "contractStatusList", description = "合同状态集合【数据字典:CONTRACT_STATUS】", required = false, example = "1"),
            @Parameter(name = "billSource", description = "账单来源【数据字典:BILL_SOURCE】", required = false, example = "1"),
            @Parameter(name = "feeType", description = "费用类型", required = false, example = "1"),
            @Parameter(name = "startCreateTime", description = "开始创建时间", required = false, example = "1"),
            @Parameter(name = "endCreateTime", description = "结束创建时间", required = false, example = "1"),
            @Parameter(name = "invoiceStatusList", description = "发票状态集合【数据字典:INVOICE_STATUS】", required = false, example = "1"),
            @Parameter(name = "parkIdList", description = "园区id集合", required = false, example = "1"),
            @Parameter(name = "buildIdList", description = "楼宇id集合", required = false, example = "1"),
            @Parameter(name = "pageSize", description = "显示条数", required = true, example = "1"),
            @Parameter(name = "pageNo", description = "页数", required = true, example = "1")
    })
    public CommonResult<PageResult<BillCollectionAllListVO>> getBillCollectionAllListPage(@Valid BillCollectionAllListVO pageReqVO) {
        PageResult<BillCollectionAllListVO> pageResult = orgBillService.getBillCollectionAllListPage(pageReqVO);
        return success(pageResult);
    }

    /**
     * @param pageReqVO
     * @return
     */
    @GetMapping("/getBillCollectionAllTotalMoney")
    @Operation(summary = "财务所有账单顶部统计")
    @Parameters({
            @Parameter(name = "billType", description = "账单类型(1=收款账单2=付款账单)", required = true, example = "1024"),
            @Parameter(name = "isShow", description = "已显示未到应收期账单(false隐藏true显示)", required = true, example = "1024"),
            @Parameter(name = "startPayDate", description = "开始应收/付时间", required = false, example = "1024"),
            @Parameter(name = "endPayDate", description = "结束应收/付时间", required = false, example = "1"),
            @Parameter(name = "billStatusList", description = "账单状态集合【数据字典:BILL_STATUS】", required = false, example = "1"),
            @Parameter(name = "contractStatusList", description = "合同状态集合【数据字典:CONTRACT_STATUS】", required = false, example = "1"),
            @Parameter(name = "billSource", description = "账单来源【数据字典:BILL_SOURCE】", required = false, example = "1"),
            @Parameter(name = "feeType", description = "费用类型", required = false, example = "1"),
            @Parameter(name = "startCreateTime", description = "开始创建时间", required = false, example = "1"),
            @Parameter(name = "endCreateTime", description = "结束创建时间", required = false, example = "1"),
            @Parameter(name = "invoiceStatusList", description = "发票状态集合【数据字典:INVOICE_STATUS】", required = false, example = "1"),
            @Parameter(name = "parkIdList", description = "园区id集合", required = false, example = "1"),
            @Parameter(name = "buildIdList", description = "楼宇id集合", required = false, example = "1")
    })
    public CommonResult<Map<String, Object>> getBillCollectionAllTotalMoney(@Valid BillCollectionAllListVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        return success(orgBillService.getBillCollectionAllTotalMoney(pageReqVO));
    }

    /**
     * @param response
     * @throws IOException
     */
    @GetMapping("/get-import-template")
    @Operation(summary = "所有账单->获得导入所有账单列表模板")
    @ApiAccessLog(operateType = EXPORT)
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 输出
        ExcelUtils.write(response, "账单列表导入模板.xls", "账单列表", BillImportVO.class, new ArrayList<>());
    }

    /**
     * @param file
     * @param response
     * @return
     */
    @PostMapping("/import")
    @Operation(summary = "所有账单->导入账单列表")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
    })
    public CommonResult<Boolean> importExcel(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        List<BillImportVO> list = new ArrayList<>();
        try {
            list = ExcelUtils.read(file, BillImportVO.class);
        } catch (Exception e) {
            throw new ServiceException(406, "获取文件失败，请稍后重试");
        }
        return success(orgBillService.importExcel(list, response));
    }

    /**
     * @param pageReqVO
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportBillCollectionAll")
    @Operation(summary = "导出财务所有账单 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBillCollectionAll(@Valid BillCollectionAllListVO pageReqVO,
                                        HttpServletResponse response) {
        try {
            pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
            List<BillCollectionAllListVO> list = orgBillService.getBillCollectionAllListPage(pageReqVO).getList();
            String fileName = "所有付款账单";
            if (pageReqVO.getBillType().equals("1")) {
                fileName = "所有收款账单";
            }
            // 导出 Excel
            ExcelUtils.write(response, fileName + ".xls", fileName,BillCollectionAllListVO.class, list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(406, e.getMessage());
        }
    }


    /******************************************逾期账单************************************************/


    /**
     * @param pageReqVO
     * @return
     */
    @GetMapping("/getBillBeOverdueListPage")
    @Operation(summary = "财务逾期账单列表")
    @Parameters({
            @Parameter(name = "pageSize", description = "显示条数", required = true, example = "1"),
            @Parameter(name = "pageNo", description = "页数", required = true, example = "1"),
            @Parameter(name = "billType", description = "账单类型(1=收款账单2=付款账单)", required = true, example = "1024"),
            @Parameter(name = "billSource", description = "账单来源【数据字典:BILL_SOURCE】", required = false, example = "1"),
            @Parameter(name = "feeType", description = "费用类型", required = false, example = "1"),
            @Parameter(name = "startPayDate", description = "开始应收时间", required = false, example = "1024"),
            @Parameter(name = "endPayDate", description = "结束应收时间", required = false, example = "1"),
            @Parameter(name = "startCreateTime", description = "开始创建时间", required = false, example = "1"),
            @Parameter(name = "endCreateTime", description = "结束创建时间", required = false, example = "1"),
            @Parameter(name = "parkIdList", description = "园区id集合", required = false, example = "1"),
            @Parameter(name = "buildIdList", description = "楼宇id集合", required = false, example = "1"),
            @Parameter(name = "ownerName", description = "租客名称", required = false, example = "1")

    })
    public CommonResult<PageResult<BillCollectionAllListVO>> getBillBeOverdueListPage(@Valid BillCollectionAllListVO pageReqVO) {
        PageResult<BillCollectionAllListVO> pageResult = orgBillService.getBillBeOverdueListPage(pageReqVO);
        return success(pageResult);
    }


    /**
     * @param pageReqVO
     * @return
     */
    @GetMapping("/getBillBeOverdueTotalMoney")
    @Operation(summary = "财务逾期账单顶部统计")
    @Parameters({
            @Parameter(name = "billType", description = "账单类型(1=收款账单2=付款账单)", required = true, example = "1024"),
            @Parameter(name = "billSource", description = "账单来源【数据字典:BILL_SOURCE】", required = false, example = "1"),
            @Parameter(name = "feeType", description = "费用类型", required = false, example = "1"),
            @Parameter(name = "startPayDate", description = "开始应收时间", required = false, example = "1024"),
            @Parameter(name = "endPayDate", description = "结束应收时间", required = false, example = "1"),
            @Parameter(name = "startCreateTime", description = "开始创建时间", required = false, example = "1"),
            @Parameter(name = "endCreateTime", description = "结束创建时间", required = false, example = "1"),
            @Parameter(name = "parkIdList", description = "园区id集合", required = false, example = "1"),
            @Parameter(name = "buildIdList", description = "楼宇id集合", required = false, example = "1"),
            @Parameter(name = "ownerName", description = "租客名称", required = false, example = "1")

    })
    public CommonResult<Map<String, Object>> getBillBeOverdueTotalMoney(@Valid BillCollectionAllListVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        return success(orgBillService.getBillBeOverdueTotalMoney(pageReqVO));
    }

}
