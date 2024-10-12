package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

/**
 * @Author lvzy
 * @Date 2024/8/28 10:52
 */
@Data
public class CreateBillVO {

    @Schema(description = "账单类型;1=收款账单;2=付款付款账单", requiredMode = Schema.RequiredMode.REQUIRED, example = "30854")
    private String billType;

    @Schema(description = "合同id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30854")
    private Long contractId;


    @Schema(description = "费用类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String feeType;

    @Schema(description = "币种")
    private String currency;

    @Schema(description = "还款周期开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payStartDate;

    @Schema(description = "还款周期结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payEndDate;

    @Schema(description = "应收(含税)", requiredMode = Schema.RequiredMode.REQUIRED)
    private String receivable;

    @Schema(description = "税额", requiredMode = Schema.RequiredMode.REQUIRED)
    private String taxAmount;

    @Schema(description = "付款日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payDate;

    @Schema(description = "滞纳金起算天数")
    private Integer startingLateFeeDay;

    @Schema(description = "滞纳金比例%")
    private String lateFeeRatio;

    @Schema(description = "滞纳金上限%")
    private String upperLimitLateFee;

    @Schema(description = "账单备注")
    private String remark;

    @Schema(description = "税率")
    private String taxRate;

    @Schema(description = "借贷类型", example = "1")
    private String loanType;

    @Schema(description = "收款(业主)流水账号id", example = "21095")
    private Long accountId;
    private String accountName;

    @Schema(description = "入账日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date incomeDate;

    @Schema(description = "对账收付款公司id", example = "16314")
    private Long ownerId;
    private String ownerName;

    @Schema(description = "发生额")
    private BigDecimal amount;


    @Schema(description = "联系人", example = "赵六")
    private String linkName;

    @Schema(description = "汇款方式", example = "1")
    private String remitType;

    @Schema(description = "租客开户银行名称", example = "24266")
    private String streamAccount;

    @Schema(description = "对方账号", example = "27002")
    private String otherAccount;

    @Schema(description = "凭证号")
    private String voucherNo;

    @Schema(description = "摘要")
    private String abstractc;

    @Schema(description = "流水备注")
    private String streamRemark;

    @Schema(description = "是否创建收支流水0=否1=是")
    private String isCreateStream;
}
