package cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.alibaba.excel.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 车的收费订单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ParkCarOrderRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7096")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "订单号", example = "26591")
    @ExcelProperty("订单号")
    private String orderId;

    @Schema(description = "机构ID", example = "29220")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12765")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "停车场ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14710")
    @ExcelProperty("停车场ID")
    private Long parkId;

    /**
     * 项目名称
     */
    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "14710")
    @TableField(exist = false)
    private String villageName;
    /**
     * 停车场名称
     */
    @TableField(exist = false)
    @Schema(description = "停车场名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "14710")
    private String parkName;

    @Schema(description = "用户服务的ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19928")
    @ExcelProperty("用户服务的ID")
    private Long uid;

    @Schema(description = "用户支付时的openid", example = "13135")
    @ExcelProperty("用户支付时的openid")
    private String userOpenid;

    @Schema(description = "车牌号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("车牌号")
    private String carNumber;

    @Schema(description = "订单类别（1月租车，2储值车，3临时车）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("订单类别（1月租车，2储值车，3临时车）")
    private String orderType;

    @Schema(description = "出入记录ID", example = "32190")
    @ExcelProperty("出入记录ID")
    private Long logId;

    @Schema(description = "入场时间")
    @ExcelProperty("入场时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date enterTime;

    @Schema(description = "离场时间")
    @ExcelProperty("离场时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date outTime;

    @Schema(description = "订单名称", example = "芋艿")
    @ExcelProperty("订单名称")
    private String orderName;

    @Schema(description = "订单详情数据")
    @ExcelProperty("订单详情数据")
    private String orderInfo;

    @Schema(description = "订单金额")
    @ExcelProperty("订单金额")
    private BigDecimal orderMoney;

    @Schema(description = "订单状态（0未支付，1已支付，2退款中，3已退款，4订单取消）", example = "1")
    @ExcelProperty("订单状态（0未支付，1已支付，2退款中，3已退款，4订单取消）")
    private String orderStatus;

    @Schema(description = "是否直付码（0预付码，1直付码）")
    @ExcelProperty("是否直付码（0预付码，1直付码）")
    private String isDirect;

    @Schema(description = "父级车道ID", example = "26739")
    @ExcelProperty("父级车道ID")
    private Long parentGateId;

    @Schema(description = "直付码的出场出入口", example = "13075")
    @ExcelProperty("直付码的出场出入口")
    private Long gateId;

    @Schema(description = "订单实付金额")
    @ExcelProperty("订单实付金额")
    private BigDecimal payMoney;

    @Schema(description = "订单支付时间")
    @ExcelProperty("订单支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date payTime;

    @Schema(description = "收款用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30037")
    @ExcelProperty("收款用户ID")
    private Long cashUid;

    @Schema(description = "支付服务的订单号，用于退款、查询", requiredMode = Schema.RequiredMode.REQUIRED, example = "20069")
    @ExcelProperty("支付服务的订单号，用于退款、查询")
    private String payOrderId;

    @Schema(description = "支付方式")
    @ExcelProperty("支付方式")
    private String payMethod;

    @Schema(description = "支付方式文本")
    @ExcelProperty("支付方式文本")
    private String payMethodTxt;

    @Schema(description = "第三方订单号")
    @ExcelProperty("第三方订单号")
    private String thirdOrderNo;

    @Schema(description = "收费标准ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31200")
    @ExcelProperty("收费标准ID")
    private Long chargeId;

    @Schema(description = "发票状态0未开具1已开具2开具中3开票失败", example = "1")
    @ExcelProperty("发票状态0未开具1已开具2开具中3开票失败")
    private String invoiceStatus;

    @Schema(description = "发票链接", example = "https://xxx")
    @ExcelProperty("发票链接")
    private String invoiceUrl;

    @Schema(description = "开票失败原因")
    @ExcelProperty("开票失败原因")
    private String invoiceError;

    @Schema(description = "退款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("退款金额")
    private BigDecimal refundMoney;

    @Schema(description = "退款时间")
    @ExcelProperty("退款时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date refundTime;

    @Schema(description = "优惠券id", example = "21458")
    @ExcelProperty("优惠券id")
    private Long couponId;

    @Schema(description = "优惠券金额")
    @ExcelProperty("优惠券金额")
    private BigDecimal couponAmount;

    @Schema(description = "退款理由", example = "不对")
    @ExcelProperty("退款理由")
    private String reason;

    @Schema(description = "预计支付金额")
    @ExcelProperty("预计支付金额")
    private BigDecimal preMoney;

    @Schema(description = "充电抵扣金额")
    @ExcelProperty("充电抵扣金额")
    private BigDecimal underCharge;

    @Schema(description = "免费出场原因id", example = "26826")
    @ExcelProperty("免费出场原因id")
    private Long freeReasonId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}