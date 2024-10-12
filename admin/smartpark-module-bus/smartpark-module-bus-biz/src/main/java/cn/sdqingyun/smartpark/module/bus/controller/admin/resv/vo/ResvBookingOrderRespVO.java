package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 预约订单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ResvBookingOrderRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31413")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20698")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应用标识")
    private String appSign;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14585")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("订单编号")
    private String orderNo;

    @Schema(description = "订单总额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("订单总额")
    private BigDecimal orderTotal;

    @Schema(description = "优惠金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("优惠金额")
    private BigDecimal discountAmount;

    @Schema(description = "已退款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("已退款金额")
    private BigDecimal refundAmount;

    @Schema(description = "支付订单号")
    @ExcelProperty("支付订单号")
    private String payOrderNo;

    @Schema(description = "实际支付金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("实际支付金额")
    private BigDecimal payAmount;

    @Schema(description = "支付方式")
    @ExcelProperty("支付方式")
    private String payMethod;

    @Schema(description = "支付方式文本")
    @ExcelProperty("支付方式文本")
    private String payMethodTxt;

    @Schema(description = "支付时间")
    @ExcelProperty("支付时间")
    private LocalDateTime payTime;

    @Schema(description = "有效支付时间")
    @ExcelProperty("有效支付时间")
    private LocalDateTime effectivePayTime;

    @Schema(description = "支付状态 0支付中 1支付成功 2已退款", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("支付状态 0支付中 1支付成功 2已退款")
    private Integer payStatus;

    @Schema(description = "第三方订单号")
    @ExcelProperty("第三方订单号")
    private String thirdOrderNo;

    @Schema(description = "订单状态 0待支付 1已支付 2已核销 3已取消 4已过期", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("订单状态 0待支付 1已支付 2已核销 3已取消 4已过期")
    private Integer orderStatus;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13171")
    @ExcelProperty("用户ID")
    private Integer userId;

    @Schema(description = "租客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19764")
    @ExcelProperty("租客ID")
    private Integer ownerId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}