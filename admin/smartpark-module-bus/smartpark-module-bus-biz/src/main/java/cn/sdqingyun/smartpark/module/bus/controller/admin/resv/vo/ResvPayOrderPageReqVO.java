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

@Schema(description = "管理后台 - 订单支付分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResvPayOrderPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "28784")
    private Long orgId;

    @Schema(description = "应用标识")
    private String appSign;

    @Schema(description = "项目ID", example = "16162")
    private Long villageId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "订单ID(支持多个订单)")
    private String orderIds;

    @Schema(description = "实际支付金额")
    private BigDecimal payAmount;

    @Schema(description = "支付订单号")
    private String payOrderNo;

    @Schema(description = "支付方式")
    private String payMethod;

    @Schema(description = "支付方式文本")
    private String payMethodTxt;

    @Schema(description = "支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] payTime;

    @Schema(description = "支付状态 0待支付 1支付成功", example = "1")
    private Integer payStatus;

    @Schema(description = "第三方订单号")
    private String thirdOrderNo;

    @Schema(description = "用户ID", example = "19672")
    private Integer userId;

    @Schema(description = "租客ID", example = "17834")
    private Long ownerId;

    @Schema(description = "pay服务响应冗余")
    private String responseParams;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}