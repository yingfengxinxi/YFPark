package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.util.*;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 机构合同账单明细新增/修改 Request VO")
@Data
public class ContractOrderBillSaveReqVO extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15150")
    private Long id;

    @Schema(description = "合同id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30854")
    private Long contractId;

    @Schema(description = "明细编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String orderNumber;

    @Schema(description = "还款期数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer number;

    @Schema(description = "还款周期开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payStartDate;

    @Schema(description = "还款周期结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payEndDate;

    @Schema(description = "费用类型0=保证金1=租金3=物业费保证金4=物业费", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String feeType;

    @Schema(description = "付款日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payDate;

    @Schema(description = "实收金额")
    private String receivablePayment;

    @Schema(description = "最终单价(含税)", requiredMode = Schema.RequiredMode.REQUIRED, example = "335")
    private String finalUnitPrice;

    @Schema(description = "应收(含税)", requiredMode = Schema.RequiredMode.REQUIRED)
    private String receivable;

    @Schema(description = "税额", requiredMode = Schema.RequiredMode.REQUIRED)
    private String taxAmount;

    @Schema(description = "状态0=未支付1=已支付2=逾期", example = "2")
    private String billStatus;


    @Schema(description = "第几次账单", example = "1")
    private Integer count;

    @Schema(description = "支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date payTime;

    @Schema(description = "账单类型;1=收款(机构收款);2=付款(机构付款);")
    private String billType;

    @Schema(description = "滞纳金")
    private String lateFee;

    @Schema(description = "逾期天数")
    private Integer overdueDay;

    @Schema(description = "账单来源0=系统生成1=手动添加")
    private String dataSource;

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