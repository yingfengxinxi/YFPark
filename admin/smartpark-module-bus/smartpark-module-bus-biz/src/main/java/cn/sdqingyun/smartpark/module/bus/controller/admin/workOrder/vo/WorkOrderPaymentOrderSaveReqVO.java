package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 付款单记录新增/修改 Request VO")
@Data
public class WorkOrderPaymentOrderSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14625")
    private Long id;

    @Schema(description = "机构id", example = "16461")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "应用标识不能为空")
    private String application;

    @Schema(description = "账单id", example = "10988")
    private String billId;

    @Schema(description = "账单号")
    private String billNumber;

    @Schema(description = "单据编号")
    private String approveNumber;

    @Schema(description = "收款方、租客id", example = "500")
    private Long ownerId;

    @Schema(description = "金额", example = "29504")
    private BigDecimal totalPrice;

    @Schema(description = "审核时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date auditTime;

    @Schema(description = "付款时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date payTime;

    @Schema(description = "工单id", example = "3089")
    private String orderId;

    @Schema(description = "0待审核  1同意（已生成）  2拒绝  3 撤销  4已付款", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "0待审核  1同意（已生成）  2拒绝  3 撤销  4已付款不能为空")
    private String status;

    @Schema(description = "费用类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "费用类型不能为空")
    private String costType;

}