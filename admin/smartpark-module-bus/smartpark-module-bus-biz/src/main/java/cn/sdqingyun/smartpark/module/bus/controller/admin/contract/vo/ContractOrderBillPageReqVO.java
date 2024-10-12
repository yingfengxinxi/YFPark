package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.*;

import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 机构合同账单明细分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractOrderBillPageReqVO extends PageParam {

    @Schema(description = "合同id", example = "30854")
    private Long contractId;

    @Schema(description = "明细编号")
    private String orderNumber;

    @Schema(description = "还款期数")
    private Integer number;

    @Schema(description = "还款周期开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payStartDate;

    @Schema(description = "还款周期结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payEndDate;

    @Schema(description = "费用类型0=保证金1=租金3=物业费保证金4=物业费", example = "2")
    private String feeType;

    @Schema(description = "付款日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payDate;

    @Schema(description = "开始付款日期")
    private String startPayDate;

    @Schema(description = "结束付款日期")
    private String endPayDate;

    @Schema(description = "最终单价(含税)", example = "335")
    private String finalUnitPrice;

    @Schema(description = "应收(含税)")
    private String receivable;

    @Schema(description = "税额")
    private String taxAmount;

    @Schema(description = "实收金额")
    private String receivablePayment;

    @Schema(description = "状态0=未支付1=已支付2=逾期", example = "2")
    private String billStatus;

    @Schema(description = "支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date payTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date createTime;

    @Schema(description = "账单类型;1=收款(机构收款);2=付款(机构付款);")
    private String billType;

    @Schema(description = "滞纳金起算天数")
    private Integer startingLateFeeDay;

    @Schema(description = "滞纳金比例%")
    private String lateFeeRatio;

    @Schema(description = "滞纳金上限%")
    private String upperLimitLateFee;

    @Schema(description = "账单条款类型1=租赁条款;2=物业条款")
    private String clauseType;

    @Schema(description = "账单来源")
    private String billSource;

    @Schema(description = "税率")
    private String taxRate;

    @Schema(description = "账单备注")
    private String remark;

    @Schema(description = "账单关闭状态;0=关闭;1=开启;")
    private String closeStatus;
}