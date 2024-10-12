package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 预约订单退款分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResvBookingOrderRefundPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "10699")
    private Long orgId;

    @Schema(description = "应用标识")
    private String appSign;

    @Schema(description = "项目ID", example = "16681")
    private Long villageId;

    @Schema(description = "预约ID", example = "3063")
    private Long bookingId;

    @Schema(description = "预约订单ID", example = "790")
    private Long orderId;

    @Schema(description = "退款订单号")
    private String orderNo;

    @Schema(description = "退款金额")
    private BigDecimal amount;

    @Schema(description = "实际退款金额")
    private BigDecimal actualAmount;

    @Schema(description = "用户ID", example = "27831")
    private Long userId;

    @Schema(description = "租客ID", example = "998")
    private Long ownerId;

    @Schema(description = "处理人")
    private Long handler;

    @Schema(description = "处理人名称", example = "赵六")
    private String handlerName;

    @Schema(description = "处理时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] handleTime;

    @Schema(description = "退款类型 1 全部退款 2 部分退款", example = "1")
    private Integer refundType;

    @Schema(description = "0未处理 1已处理 2驳回", example = "2")
    private Integer status;

    @Schema(description = "退款错误日志")
    private String errorMsg;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}