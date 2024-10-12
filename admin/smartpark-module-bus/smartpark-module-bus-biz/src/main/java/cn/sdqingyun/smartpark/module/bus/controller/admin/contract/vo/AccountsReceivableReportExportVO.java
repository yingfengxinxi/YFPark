package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/9/5 9:59
 */
@Data
public class AccountsReceivableReportExportVO {


    @Schema(description = "租客名称", example = "1")
    @ExcelProperty("租客名称")
    private String ownerName;

    @Schema(description = "默认联系人", example = "1")
    @ExcelProperty("默认联系人")
    private String ownerContactName;

    @Schema(description = "联系方式", example = "1")
    @ExcelProperty("联系方式")
    private String phone;

    @Schema(description = "楼宇名称", example = "1")
    @ExcelProperty("楼宇名称")
    private String buildName;

    @ExcelProperty("房间号")
    private String roomNumberName;

    @Schema(description = "有效期开始日期", example = "1")
    @ExcelProperty("有效期开始日期")
    private String payStartDate;

    @Schema(description = "有效期结束日期", example = "1")
    @ExcelProperty("有效期结束日期")
    private String payEndDate;

    @Schema(description = "费用类型", example = "1")
    @ExcelProperty("费用类型")
    private String costTypeName;

    @Schema(description = "应收金额", example = "1")
    @ExcelProperty("应收金额")
    private BigDecimal receivable;

    @Schema(description = "应收时间", example = "1")
    @ExcelProperty("应收时间")
    private String payDate;

    @Schema(description = "滞纳金", example = "1")
    @ExcelProperty("滞纳金")
    private BigDecimal lateFee;

    @Schema(description = "实收金额", example = "1")
    @ExcelProperty("实收金额")
    private BigDecimal receivablePayment;

    @Schema(description = "账单状态", example = "1")
    @ExcelProperty("账单状态")
    private String billStatus;

    @Schema(description = "付款方式", example = "1")
    @ExcelProperty("付款方式")
    private String remitType;
}
