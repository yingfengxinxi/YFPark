package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

/**
 * @Author lvzy
 * @Date 2024/8/15 10:10
 */
@Data
public class BillInformationVO {


    @Schema(description = "账单Id", example = "1")
    private Long billId;

    @Schema(description = "付款方id")
    private Long ownerId;

    @Schema(description = "付款方")
    private String ownerName;

    /**
     *
     */
    @Schema(description = "是否个人，1个人，0公司")
    private Integer isPersonal;

    @Schema(description = "账单关闭状态;0=关闭;1=开启;")
    private String closeStatus;

    @Schema(description = "账单状态")
    private String billStatus;

    @Schema(description = "楼宇id")
    private Long buildId;

    @Schema(description = "房间号")
    private String roomNumber;

    @Schema(description = "账单金额")
    private BigDecimal receivable;

    @Schema(description = "需收金额")
    private BigDecimal amountToBeCollected;

    @Schema(description = "产生滞纳金")
    private BigDecimal generateLateFee;

    @Schema(description = "应收/需收滞纳金")
    private BigDecimal lateFee;

    @Schema(description = "滞纳金付款状态0=未产生滞纳金1=未支付2=已结清")
    private String lateFeePayStatus;

    @Schema(description = "应收时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payDate;

    @Schema(description = "费用类型", example = "1")
    private String feeType;

    @Schema(description = "计费周期", example = "1")
    private String billingCycle;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime createTime;

    @Schema(description = "合同Id")
    private Long contractId;

    @Schema(description = "合同编号")
    private String contractNumber;

    @Schema(description = "账单编号")
    private String orderNumber;

    @Schema(description = "滞纳金起算天数")
    private Integer startingLateFeeDay;

    @Schema(description = "滞纳金比例%")
    private String lateFeeRatio;

    @Schema(description = "滞纳金上限%")
    private String upperLimitLateFee;

    @Schema(description = "经办人")
    private String operatorName;

    @Schema(description = "账单备注")
    private String remark;

    @Schema(description = "园区Id")
    private Long villageId;
}

