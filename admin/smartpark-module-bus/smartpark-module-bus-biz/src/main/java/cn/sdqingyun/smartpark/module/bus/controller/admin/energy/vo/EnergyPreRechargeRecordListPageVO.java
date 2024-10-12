package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * @Author lvzy
 * @Date 2024/10/9 16:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyPreRechargeRecordListPageVO extends PageParam {

    private Long tenantId;
    private Long villageId;
    private Long buildId;
    private Long energyId;
    private String startDate;
    private String endDate;

    @Schema(description = "表id", example = "22388")
    private Long id;

    @Schema(description = "表名称", example = "22388")
    private String name;

    @Schema(description = "表编号", example = "22388")
    private String number;

    @Schema(description = "充值金额", example = "22388")
    private BigDecimal rechargePrice;

    @Schema(description = "充值度数", example = "22388")
    private BigDecimal rechargeDegree;


    @Schema(description = "充值时间", example = "22388")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date createTime;

    @Schema(description = "充值状态", example = "22388")
    private String result;

    @Schema(description = "收支流水id", example = "22388")
    private Long billStreamId;


    @Schema(description = "流水号", example = "22388")
    private String billNumber;


    @Schema(description = "账单号", example = "22388")
    private String orderNumber;


    @Schema(description = "账单id", example = "22388")
    private Long billId;
}
