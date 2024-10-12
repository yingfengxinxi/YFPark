package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 付费工单订单新增/修改 Request VO")
@Data
public class WorkOrderProposeOrderSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20578")
    private Long id;

    @Schema(description = "机构ID", example = "21856")
    private Long orgId;

    @Schema(description = "工单id", example = "13685")
    private Long workorderId;

    @Schema(description = "项目id", example = "27754")
    private Long villageId;

    @Schema(description = "楼宇id", example = "23034")
    private Long buildId;

    @Schema(description = "房号", example = "31103")
    private String roomId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "来源标识")
    private String from;

    @Schema(description = "租客id", example = "16069")
    private Long ownerId;

    @Schema(description = "订单名称", example = "芋艿")
    private String orderName;

    @Schema(description = "订单编号")
    private String orderNumber;

    @Schema(description = "订单状态;1=待支付;2=已支付;3=已退款;4=已取消;", example = "1")
    private String orderStatus;

    @Schema(description = "退款状态;1=部分;2=全额;", example = "1")
    private String refundStatus;

    @Schema(description = "工单大类id", example = "8890")
    private Long categoryId;

    @Schema(description = "工单子类id", example = "6164")
    private Long subcatId;

    @Schema(description = "订单金额(应付金额)")
    private BigDecimal amount;

    @Schema(description = "订单需付金额")
    private BigDecimal needAmount;

    @Schema(description = "支付金额")
    private BigDecimal paymentAmount;

    @Schema(description = "支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date paymentTime;

    @Schema(description = "退款金额")
    private BigDecimal refundAmount;

    @Schema(description = "退款时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date refundTime;

    @Schema(description = "订单类型", example = "1")
    private String orderType;

    @Schema(description = "支付方式")
    private String payMethod;

    @Schema(description = "第三方订单号")
    private String thirdOrderNo;

    @Schema(description = "第三方信息")
    private String thirdExtra;

    @Schema(description = "extra信息")
    private String extraData;

    @Schema(description = "资产单据信息json")
    private String stuffData;

    @Schema(description = "订单流程状态;1=正常;0=关闭", example = "2")
    private String status;

    @Schema(description = "是否生成 1未生产 2已生成", example = "2")
    private String generateStatus;

}