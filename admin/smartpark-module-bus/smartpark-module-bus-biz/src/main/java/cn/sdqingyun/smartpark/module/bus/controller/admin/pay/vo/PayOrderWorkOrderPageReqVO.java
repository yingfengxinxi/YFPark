package cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 支付订单和工单订单记录中间分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayOrderWorkOrderPageReqVO extends PageParam {

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "付款记录id", example = "31793")
    private Long workorderProposeOrderId;

    @Schema(description = "商户订单编号", example = "18348")
    private String merchantOrderId;

    @Schema(description = "支付金额，单位：分", example = "19403")
    private Integer price;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "订单类型0=支付1=退款", example = "1")
    private String orderType;

}