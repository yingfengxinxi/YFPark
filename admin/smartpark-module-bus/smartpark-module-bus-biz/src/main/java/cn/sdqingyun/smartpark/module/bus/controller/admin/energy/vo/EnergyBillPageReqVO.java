package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 自定义账单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyBillPageReqVO extends PageParam {

    @Schema(description = "设备表种类", example = "2")
    private String type;

    @Schema(description = "账单编号")
    private String billNumber;

    @Schema(description = "机构ID", example = "24166")
    private Long orgId;

    @Schema(description = "项目ID", example = "9948")
    private Long villageId;

    @Schema(description = "楼宇ID", example = "3653")
    private Long buildId;

    @Schema(description = "自定义关联ID", example = "15261")
    private Long energyId;

    @Schema(description = "租客ID", example = "17580")
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

    @Schema(description = "分表用量")
    private BigDecimal partUse;

    @Schema(description = "分表金额")
    private BigDecimal partAmount;

    @Schema(description = "分摊用量")
    private BigDecimal publicUse;

    @Schema(description = "分摊金额")
    private BigDecimal publicAmount;

    @Schema(description = "税率")
    private Integer taxRate;

    @Schema(description = "账单应付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date receivablePaymentTime;

    @Schema(description = "总计金额")
    private BigDecimal totalAmount;

    @Schema(description = "房间ids")
    private String roomIds;

    @Schema(description = "状态，1是，0否")
    private String isNewOwner;

    @Schema(description = "滞纳金;{\"startDay\":10,\"ratio\":50,\"toplimit\":80}")
    private String lateFee;

    @Schema(description = "状态，1已生成，2待生成，3禁用", example = "1")
    private String status;

    @Schema(description = "生成流水状态，1已产生，2未产成，3禁用", example = "2")
    private String businessStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}