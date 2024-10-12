package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 预约订单新增/修改 Request VO")
@Data
public class ResvBookingOrderSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号")
    private Long id;

    @Schema(description = "机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "应用标识不能为空")
    private String appSign;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14585")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "订单总额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单总额不能为空")
    private BigDecimal orderTotal;

    @Schema(description = "优惠金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "优惠金额不能为空")
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
    private LocalDateTime payTime;

    @Schema(description = "有效支付时间")
    private LocalDateTime effectivePayTime;

    @Schema(description = "支付状态 0支付中 1支付成功 2已退款", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "支付状态 0支付中 1支付成功 2已退款不能为空")
    private Integer payStatus;

    @Schema(description = "第三方订单号")
    private String thirdOrderNo;

    @Schema(description = "订单状态 0待支付 1已支付 2已核销 3已取消 4已过期", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "订单状态 0待支付 1已支付 2已核销 3已取消 4已过期不能为空")
    private Integer orderStatus;

    @Schema(description = "用户ID", example = "13171")
    private Long userId;

    @Schema(description = "租客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19764")
    @NotNull(message = "租客ID不能为空")
    private Long ownerId;

}