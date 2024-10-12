package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import cn.sdqingyun.smartpark.framework.excel.core.annotations.DictFormat;
import cn.sdqingyun.smartpark.framework.excel.core.annotations.ExcelColumnSelect;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;
import static cn.sdqingyun.smartpark.module.bus.enums.DictTypeConstants.CONTRACT_UNIT_PRICE;
import static cn.sdqingyun.smartpark.module.bus.enums.DictTypeConstants.REMIT_TYPE;

/**
 * @Author lvzy
 * @Date 2024/7/25 9:52
 */
@Data
public class ReceiptImportVO {


    @NotNull(message = "Excel 文件不能为空")
    private MultipartFile file;

    @Schema(description = "收据编号")
    @ExcelProperty("收据编号")
    private String receiptNumber;

    @Schema(description = "开据时间")
    @ExcelProperty("开据时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date issuerTime;

    @Schema(description = "可开据金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("可开据金额")
    private BigDecimal canReceiptAmount;

    @Schema(description = "交款单位（租客名称）")
    @ExcelProperty("交款单位（租客名称）")
    private String paymentCompany;

    @Schema(description = "交款人", example = "王五")
    @ExcelProperty("交款人")
    private String paymentUname;

    @Schema(description = "交款方地址")
    @ExcelProperty("交款方地址")
    private String paymentAddress;

    @Schema(description = "交款方电话")
    @ExcelProperty("交款方电话")
    private String paymentPhone;

    @Schema(description = "收款单位")
    @ExcelProperty("收款单位")
    private String collectionCompany;

    @Schema(description = "收款方收款人", example = "王五")
    @ExcelProperty("收款方收款人")
    private String collectionUname;

    @Schema(description = "收款方地址")
    @ExcelProperty("收款方地址")
    private String collectionAddress;

    @Schema(description = "收款方电话")
    @ExcelProperty("收款方电话")
    private String collectionPhone;

    @Schema(description = "开据账单（账单编号）", example = "1088")
    @ExcelProperty("开据账单（账单编号）")
    private String billNumber;

    @Schema(description = "汇款方式", example = "1")
    @ExcelProperty("汇款方式")
    @DictFormat(REMIT_TYPE)
    @ExcelColumnSelect(dictType = REMIT_TYPE)
    private String remitType;

    @Schema(description = "费用名称", example = "赵六")
    @ExcelProperty("费用名称")
    private String costName;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "开据人", example = "12288")
    @ExcelProperty("开据人")
    private String issuerUid;
}
