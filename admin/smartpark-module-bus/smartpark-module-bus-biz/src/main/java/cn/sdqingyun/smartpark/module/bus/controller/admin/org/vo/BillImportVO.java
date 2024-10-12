package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * @Author lvzy
 * @Date 2024/8/28 14:52
 */
@Data
public class BillImportVO {

    @ExcelProperty("对应合同(合同编号)")
    private String contractNumber;

    @Schema(description = "账单类型【收款、付款】")
    @ExcelProperty("账单类型【收款、付款】")
    private String billType;

    @ExcelProperty("费用类型")
    private String feeTypeName;

    @ExcelProperty("应收金额")
    private BigDecimal receivable;

    @ExcelProperty("已收金额")
    private BigDecimal receivablePayment;

    @ExcelProperty("计费周期【内容格式yyyy-MM-dd~yyyy-MM-dd】")
    private String billingCycle;

    @ExcelProperty(value = "应交日期【内容格式yyyy-MM-dd】")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private String payDate;

    @ExcelProperty(value = "备注")
    private String remarks;

    @Schema(description = "导入结果说明")
    @ExcelProperty(value = "导入结果说明")
    private String importResult;
}
