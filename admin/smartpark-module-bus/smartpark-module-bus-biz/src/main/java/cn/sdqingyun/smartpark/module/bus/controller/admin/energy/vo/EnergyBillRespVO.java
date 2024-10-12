package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 自定义账单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyBillRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4810")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "设备表种类", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("设备表种类")
    private String type;

    @Schema(description = "账单编号")
    @ExcelProperty("账单编号")
    private String billNumber;

    @Schema(description = "机构ID", example = "24166")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9948")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "楼宇ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3653")
    @ExcelProperty("楼宇ID")
    private Long buildId;

    @Schema(description = "自定义关联ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15261")
    @ExcelProperty("自定义关联ID")
    private Long energyId;

    @Schema(description = "租客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17580")
    @ExcelProperty("租客ID")
    private Long ownerId;

    @Schema(description = "涉及抄表记录ids")
    @ExcelProperty("涉及抄表记录ids")
    private String energyRecordIds;

    @Schema(description = "合同信息", example = "24400")
    @ExcelProperty("合同信息")
    private Long contractId;

    @Schema(description = "起始时间")
    @ExcelProperty("起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date startTime;

    @Schema(description = "结束时间")
    @ExcelProperty("结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date endTime;

    @Schema(description = "分表用量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分表用量")
    private BigDecimal partUse;

    @Schema(description = "分表金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分表金额")
    private BigDecimal partAmount;

    @Schema(description = "分摊用量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分摊用量")
    private BigDecimal publicUse;

    @Schema(description = "分摊金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分摊金额")
    private BigDecimal publicAmount;

    @Schema(description = "税率", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("税率")
    private Integer taxRate;

    @Schema(description = "账单应付时间")
    @ExcelProperty("账单应付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date receivablePaymentTime;

    @Schema(description = "总计金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("总计金额")
    private BigDecimal totalAmount;

    @Schema(description = "房间ids")
    @ExcelProperty("房间ids")
    private String roomIds;

    @Schema(description = "状态，1是，0否", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("状态，1是，0否")
    private String isNewOwner;

    @Schema(description = "滞纳金;{\"startDay\":10,\"ratio\":50,\"toplimit\":80}")
    @ExcelProperty("滞纳金;{\"startDay\":10,\"ratio\":50,\"toplimit\":80}")
    private String lateFee;

    @Schema(description = "状态，1已生成，2待生成，3禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态，1已生成，2待生成，3禁用")
    private String status;

    @Schema(description = "生成流水状态，1已产生，2未产成，3禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("生成流水状态，1已产生，2未产成，3禁用")
    private String businessStatus;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}