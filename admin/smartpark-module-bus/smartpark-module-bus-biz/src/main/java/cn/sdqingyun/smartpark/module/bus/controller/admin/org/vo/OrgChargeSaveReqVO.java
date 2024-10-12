package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 收费标准新增/修改 Request VO")
@Data
public class OrgChargeSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7332")
    private Long id;

    @Schema(description = "标准名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "标准名称不能为空")
    private String name;

    @Schema(description = "计费模式：1-单价，2-单价*计量单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "计费模式：1-单价，2-单价*计量单位不能为空")
    private String mode;

    @Schema(description = "收费金额、单价(根据 mode 来决定)", requiredMode = Schema.RequiredMode.REQUIRED, example = "14473")
    @NotNull(message = "收费金额、单价(根据 mode 来决定)不能为空")
    private BigDecimal unitPrice;

    @Schema(description = "标准生效时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "标准生效时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date validTime;

    @Schema(description = "标准失效时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "标准失效时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date expireTime;

    @Schema(description = "状态：0-已失效，1-生效中", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "状态：0-已失效，1-生效中不能为空")
    private String status;

    @Schema(description = "计量单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "计量单位不能为空")
    private String unit;

    @Schema(description = "违约金起算天数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "违约金起算天数不能为空")
    private Integer afterDaysStartBill;

    @Schema(description = "违约金收取比例（天/%）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "违约金收取比例（天/%）不能为空")
    private Integer everyDayPre;

    @Schema(description = "违约金上限(元)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "违约金上限(元)不能为空")
    private BigDecimal upperLimitFee;

    @Schema(description = "绑定对象： 1-房间，2-车位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "绑定对象： 1-房间，2-车位不能为空")
    private String bindObject;

    @Schema(description = "园区Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17371")
    @NotNull(message = "园区Id不能为空")
    private Long villageId;

    @Schema(description = "账单费用类型表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4364")
    @NotNull(message = "账单费用类型表id不能为空")
    private Long billCostTypeId;

    @Schema(description = "计费单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "计费单位不能为空")
    private String unitCharging;

    @Schema(description = "计费规则(天单价换算、月划分)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "计费规则(天单价换算、月划分)不能为空")
    private String dailyUnitPriceAndMonthRule;

    @Schema(description = "账单(应)付款时间-动作", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "账单(应)付款时间-动作不能为空")
    private String payDateAction;

    @Schema(description = "天数（日）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "天数（日）不能为空")
    private Integer payDateDays;

    @Schema(description = "账单(应)付款时间-方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "账单(应)付款时间-方式不能为空")
    private String payDateActionType;

    @Schema(description = "付款周期（N月一付）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "付款周期（N月一付）不能为空")
    private Integer payCycle;

    @Schema(description = "划分方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "划分方式不能为空")
    private String divideMethod;

}