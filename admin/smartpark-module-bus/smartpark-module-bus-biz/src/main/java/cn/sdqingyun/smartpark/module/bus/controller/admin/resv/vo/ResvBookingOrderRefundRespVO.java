package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 预约订单退款 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ResvBookingOrderRefundRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19584")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10699")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应用标识")
    private String appSign;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16681")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "预约ID", example = "3063")
    @ExcelProperty("预约ID")
    private Long bookingId;

    @Schema(description = "预约订单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "790")
    @ExcelProperty("预约订单ID")
    private Long orderId;

    @Schema(description = "退款订单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("退款订单号")
    private String orderNo;

    @Schema(description = "退款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("退款金额")
    private BigDecimal amount;

    @Schema(description = "实际退款金额")
    @ExcelProperty("实际退款金额")
    private BigDecimal actualAmount;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27831")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "租客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "998")
    @ExcelProperty("租客ID")
    private Long ownerId;

    @Schema(description = "处理人")
    @ExcelProperty("处理人")
    private Long handler;

    @Schema(description = "处理人名称", example = "赵六")
    @ExcelProperty("处理人名称")
    private String handlerName;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private LocalDateTime handleTime;

    @Schema(description = "退款类型 1 全部退款 2 部分退款", example = "1")
    @ExcelProperty("退款类型 1 全部退款 2 部分退款")
    private Integer refundType;

    @Schema(description = "0未处理 1已处理 2驳回", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("0未处理 1已处理 2驳回")
    private Integer status;

    @Schema(description = "退款错误日志")
    @ExcelProperty("退款错误日志")
    private String errorMsg;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "订单总额")
    private BigDecimal orderTotal;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "场地名称")
    private String placeName;

    @Schema(description = "预约人姓名")
    private String userName;

    @Schema(description = "用户手机号")
    private String userMobile;

    @Schema(description = "租客名称")
    private String ownerName;
}