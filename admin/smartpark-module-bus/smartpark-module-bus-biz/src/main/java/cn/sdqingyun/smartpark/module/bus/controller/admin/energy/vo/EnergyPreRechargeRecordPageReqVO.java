package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 水电表预充值记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyPreRechargeRecordPageReqVO extends PageParam {

    @Schema(description = "机构iD", example = "22388")
    private Long orgId;

    @Schema(description = "水电表id", example = "987")
    private Long energyId;

    @Schema(description = "充值金额", example = "14820")
    private BigDecimal rechargePrice;

    @Schema(description = "充值度数")
    private BigDecimal rechargeDegree;

    @Schema(description = "账单id", example = "13315")
    private Long billId;

    @Schema(description = "流水id", example = "8400")
    private Long billStreamId;

    @Schema(description = "流水号")
    private String streamNumber;

    @Schema(description = "账单编号")
    private String billNumber;

    @Schema(description = "设备上报返回的数据")
    private String result;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}