package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 预约订单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResvBookingOrderPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "20698")
    private Long orgId;

    @Schema(description = "应用标识")
    private String appSign;

    @Schema(description = "项目ID", example = "14585")
    private Long villageId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "订单总额")
    private BigDecimal orderTotal;

    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;

    @Schema(description = "已退款金额")
    private BigDecimal refundAmount;

    @Schema(description = "支付订单号")
    private String payOrderNo;

    @Schema(description = "实际支付金额")
    private BigDecimal payAmount;

    @Schema(description = "支付方式")
    private String payMethod;

    @Schema(description = "支付方式文本")
    private String payMethodTxt;

    @Schema(description = "支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] payTime;

    @Schema(description = "有效支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] effectivePayTime;

    @Schema(description = "支付状态 0支付中 1支付成功 2已退款", example = "1")
    private Integer payStatus;

    @Schema(description = "第三方订单号")
    private String thirdOrderNo;

    @Schema(description = "订单状态 0待支付 1已支付 2已核销 3已取消 4已过期", example = "1")
    private Integer orderStatus;

    @Schema(description = "用户ID", example = "13171")
    private Integer userId;

    @Schema(description = "租客ID", example = "19764")
    private Integer ownerId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}