package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * @Author lvzy
 * @Date 2024/9/3 11:28
 */
@Data
public class IncomeDetailVO {

    @Schema(description = "账单状态")
    private String billStatus;

    @Schema(description = "应收金额/账单金额")
    private String receivable;

    @Schema(description = "需收金额【应收+违约金-实收=需收】")
    private String amountToBeCollected;

    /**
     * 应收日期
     */
    @Schema(description = "应收日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payDate;

    @Schema(description = "应收/需收滞纳金")
    private String lateFee;

    @Schema(description = "产生滞纳金")
    private String generateLateFee;


    @Schema(description = "费用类型")
    private String feeTypeName;
    private String feeType;

    @Schema(description = "合同编号")
    private String contractNumber;

    @Schema(description = "合同Id")
    private Long contractId;

    @Schema(description = "滞纳金起算天数")
    private Integer startingLateFeeDay;

    @Schema(description = "滞纳金比例%")
    private String lateFeeRatio;

    @Schema(description = "滞纳金上限%")
    private String upperLimitLateFee;

    @Schema(description = "付款人")
    private String payName;

    @Schema(description = "付款人Id")
    private Long ownerId;

    @Schema(description = "是否个人，1个人，0公司")
    private Integer isPersonal;

    @Schema(description = "经办人")
    private String operatorName;

    @Schema(description = "账单编号")
    private String orderNumber;

    @Schema(description = "账单备注")
    private String remark;

    /**
     * 还款周期开始时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    @Schema(description = "入账时间")
    private Date payTime;

    /**
     * 还款周期开始时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    @Schema(description = "还款周期开始时间")
    private Date payStartDate;

    /**
     * 还款周期结束时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    @Schema(description = "还款周期结束时间")
    private Date payEndDate;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    @Schema(description = "匹配日期")
    private Date matchDate;
}
