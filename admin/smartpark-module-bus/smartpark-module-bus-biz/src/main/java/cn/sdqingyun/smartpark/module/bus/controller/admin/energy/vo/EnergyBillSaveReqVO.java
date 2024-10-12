package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

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

@Schema(description = "管理后台 - 自定义账单新增/修改 Request VO")
@Data
public class EnergyBillSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4810")
    private Long id;

    @Schema(description = "设备表种类", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "设备表种类不能为空")
    private String type;

    @Schema(description = "账单编号")
    private String billNumber;

    @Schema(description = "机构ID", example = "24166")
    private Long orgId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9948")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "楼宇ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3653")
    @NotNull(message = "楼宇ID不能为空")
    private Long buildId;

    @Schema(description = "自定义关联ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15261")
    @NotNull(message = "自定义关联ID不能为空")
    private Long energyId;

    @Schema(description = "租客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17580")
    @NotNull(message = "租客ID不能为空")
    private Long ownerId;

    @Schema(description = "涉及抄表记录ids")
    private String energyRecordIds;

    @Schema(description = "合同信息", example = "24400")
    private Long contractId;

    @Schema(description = "起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date startTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date endTime;

    @Schema(description = "分表用量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分表用量不能为空")
    private BigDecimal partUse;

    @Schema(description = "分表金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分表金额不能为空")
    private BigDecimal partAmount;

    @Schema(description = "分摊用量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分摊用量不能为空")
    private BigDecimal publicUse;

    @Schema(description = "分摊金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分摊金额不能为空")
    private BigDecimal publicAmount;

    @Schema(description = "税率", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "税率不能为空")
    private Integer taxRate;

    @Schema(description = "账单应付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date receivablePaymentTime;

    @Schema(description = "总计金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总计金额不能为空")
    private BigDecimal totalAmount;

    @Schema(description = "房间ids")
    private String roomIds;

    @Schema(description = "状态，1是，0否", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "状态，1是，0否不能为空")
    private String isNewOwner;

    @Schema(description = "滞纳金;{\"startDay\":10,\"ratio\":50,\"toplimit\":80}")
    private String lateFee;

    @Schema(description = "状态，1已生成，2待生成，3禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "状态，1已生成，2待生成，3禁用不能为空")
    private String status;

    @Schema(description = "生成流水状态，1已产生，2未产成，3禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "生成流水状态，1已产生，2未产成，3禁用不能为空")
    private String businessStatus;

}