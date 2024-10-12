package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 分摊记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyPublicRecordPageReqVO extends PageParam {

    @Schema(description = "类型 1分表 2总表", example = "2")
    private String type;

    @Schema(description = "机构ID", example = "11549")
    private Long orgId;

    @Schema(description = "项目ID", example = "29293")
    private Long villageId;

    @Schema(description = "楼宇ID", example = "30034")
    private Long buildId;

    @Schema(description = "关联自定义表ID", example = "2877")
    private Long energyId;

    @Schema(description = "父级ID", example = "22164")
    private Long parentEnergyId;

    @Schema(description = "表名称", example = "王五")
    private String name;

    @Schema(description = "起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date startTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date endTime;

    @Schema(description = "分摊用量")
    private BigDecimal publicUse;

    @Schema(description = "分摊金额")
    private BigDecimal publicAmount;

    @Schema(description = "状态，1启动，0审核中，4禁用", example = "2")
    private String status;

    @Schema(description = "绑定账单")
    private String bindBill;

    @Schema(description = "账单ID", example = "25916")
    private Long billId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}