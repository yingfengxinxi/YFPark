package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 订单支付 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ResvPayOrderRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25747")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28784")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应用标识")
    private String appSign;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16162")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("订单编号")
    private String orderNo;

    @Schema(description = "订单ID(支持多个订单)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("订单ID(支持多个订单)")
    private String orderIds;

    @Schema(description = "实际支付金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("实际支付金额")
    private BigDecimal payAmount;

    @Schema(description = "支付订单号")
    @ExcelProperty("支付订单号")
    private String payOrderNo;

    @Schema(description = "支付方式")
    @ExcelProperty("支付方式")
    private String payMethod;

    @Schema(description = "支付方式文本")
    @ExcelProperty("支付方式文本")
    private String payMethodTxt;

    @Schema(description = "支付时间")
    @ExcelProperty("支付时间")
    private LocalDateTime payTime;

    @Schema(description = "支付状态 0待支付 1支付成功", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("支付状态 0待支付 1支付成功")
    private Integer payStatus;

    @Schema(description = "第三方订单号")
    @ExcelProperty("第三方订单号")
    private String thirdOrderNo;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19672")
    @ExcelProperty("用户ID")
    private Integer userId;

    @Schema(description = "租客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17834")
    @ExcelProperty("租客ID")
    private Long ownerId;

    @Schema(description = "pay服务响应冗余")
    @ExcelProperty("pay服务响应冗余")
    private String responseParams;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}