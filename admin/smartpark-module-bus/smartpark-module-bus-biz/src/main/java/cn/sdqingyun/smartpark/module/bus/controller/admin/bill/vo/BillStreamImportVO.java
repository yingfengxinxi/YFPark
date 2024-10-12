package cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo;

import cn.sdqingyun.smartpark.framework.excel.core.annotations.DictFormat;
import cn.sdqingyun.smartpark.framework.excel.core.annotations.ExcelColumnSelect;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;
import static cn.sdqingyun.smartpark.module.bus.enums.DictTypeConstants.*;
import static cn.sdqingyun.smartpark.module.crm.enums.DictTypeConstants.*;

/**
 * @Author lvzy
 * @Date 2024/6/21 16:24
 */
@Data
@ExcelIgnoreUnannotated
public class BillStreamImportVO {

    @NotNull(message = "Excel 文件不能为空")
    private MultipartFile file;

//    @NotNull(message = "是否自动匹配账单0=否1=是")
//    private Boolean updateSupport;

    @NotNull(message = "园区id不能为空")
    private Long villageId;

    @NotNull(message = "楼宇id不能为空")
    private Long buildId;

    @NotNull(message = "房屋id不能为空")
    private Long roomNumber;


    @ExcelProperty(value = "借贷标")
    @NotNull(message = "借贷标不能为空")
    @DictFormat(LOAN_TYPE)
    @ExcelColumnSelect(dictType = LOAN_TYPE)
    private String loanType;

    @ExcelProperty(value = "币种")
    @NotNull(message = "币种不能为空")
    @DictFormat(CURRENCY)
    @ExcelColumnSelect(dictType = CURRENCY)
    private String currency;

    @ExcelProperty(value = "发生额")
    @NotNull(message = "发生额不能为空")
    private BigDecimal amount;

    @ExcelProperty(value = "入账时间")
    @NotNull(message = "入账时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date incomeDate;

    @ExcelProperty(value = "流水账户名称")
    @NotNull(message = "流水账户名称不能为空")
    private String accountName;

    @ExcelProperty(value = "凭证号")
    private String voucherNo;

    @ExcelProperty(value = "对方单位名称")
    @NotNull(message = "对方单位名称不能为空")
    private String companyName;


    @ExcelProperty(value = "联系人")
    private String linkName;

//    @ExcelProperty(value = "对方账户")
//    private String otherAccount;
//
//    @ExcelProperty(value = "开户行")
//    private String streamAccount;

    @ExcelProperty(value = "汇款方式")
    @NotNull(message = "汇款方式不能为空")
    @DictFormat(REMIT_TYPE)
    @ExcelColumnSelect(dictType = REMIT_TYPE)
    private String remitType;

    @ExcelProperty(value = "摘要")
    private String abstractc;

    @ExcelProperty(value = "备注")
    private String remark;
}
