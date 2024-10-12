package cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 车的收费订单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ParkCarOrderPageReqVO extends PageParam {

    @Schema(description = "订单号", example = "26591")
    private String orderId;

    @Schema(description = "机构ID", example = "29220")
    private Long orgId;

    @Schema(description = "项目ID", example = "12765")
    private Long villageId;

    @Schema(description = "停车场ID", example = "14710")
    private Long parkId;

    @Schema(description = "用户服务的ID", example = "19928")
    private Long uid;

    @Schema(description = "用户支付时的openid", example = "13135")
    private String userOpenid;

    @Schema(description = "车牌号")
    private String carNumber;

    @Schema(description = "订单类别（1月租车，2储值车，3临时车）", example = "2")
    private String orderType;

    @Schema(description = "出入记录ID", example = "32190")
    private Long logId;

    @Schema(description = "入场时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date enterTime;

    @Schema(description = "离场时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date outTime;

    @Schema(description = "订单名称", example = "芋艿")
    private String orderName;

    @Schema(description = "订单详情数据")
    private String orderInfo;

    @Schema(description = "订单金额")
    private BigDecimal orderMoney;

    @Schema(description = "订单状态（0未支付，1已支付，2退款中，3已退款，4订单取消）", example = "1")
    private String orderStatus;

    @Schema(description = "是否直付码（0预付码，1直付码）")
    private String isDirect;

    @Schema(description = "父级车道ID", example = "26739")
    private Long parentGateId;

    @Schema(description = "直付码的出场出入口", example = "13075")
    private Long gateId;

    @Schema(description = "订单实付金额")
    private BigDecimal payMoney;

    @Schema(description = "订单支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date payTime;

    @Schema(description = "收款用户ID", example = "30037")
    private Long cashUid;

    @Schema(description = "支付服务的订单号，用于退款、查询", example = "20069")
    private String payOrderId;

    @Schema(description = "支付方式")
    private String payMethod;

    @Schema(description = "支付方式文本")
    private String payMethodTxt;

    @Schema(description = "第三方订单号")
    private String thirdOrderNo;

    @Schema(description = "收费标准ID", example = "31200")
    private Long chargeId;

    @Schema(description = "发票状态0未开具1已开具2开具中3开票失败", example = "1")
    private String invoiceStatus;

    @Schema(description = "发票链接", example = "https://xxx")
    private String invoiceUrl;

    @Schema(description = "开票失败原因")
    private String invoiceError;

    @Schema(description = "退款金额")
    private BigDecimal refundMoney;

    @Schema(description = "退款时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date refundTime;

    @Schema(description = "优惠券id", example = "21458")
    private Long couponId;

    @Schema(description = "优惠券金额")
    private BigDecimal couponAmount;

    @Schema(description = "退款理由", example = "不对")
    private String reason;

    @Schema(description = "预计支付金额")
    private BigDecimal preMoney;

    @Schema(description = "充电抵扣金额")
    private BigDecimal underCharge;

    @Schema(description = "免费出场原因id", example = "26826")
    private Long freeReasonId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}