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

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 分摊记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyPublicRecordRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19762")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "类型 1分表 2总表", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("类型 1分表 2总表")
    private String type;

    @Schema(description = "机构ID", example = "11549")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "项目ID", example = "29293")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "楼宇ID", example = "30034")
    @ExcelProperty("楼宇ID")
    private Long buildId;

    @Schema(description = "关联自定义表ID", example = "2877")
    @ExcelProperty("关联自定义表ID")
    private Long energyId;

    @Schema(description = "父级ID", example = "22164")
    @ExcelProperty("父级ID")
    private Long parentEnergyId;

    @Schema(description = "表名称", example = "王五")
    @ExcelProperty("表名称")
    private String name;

    @Schema(description = "起始时间")
    @ExcelProperty("起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date startTime;

    @Schema(description = "结束时间")
    @ExcelProperty("结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date endTime;

    @Schema(description = "分摊用量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分摊用量")
    private BigDecimal publicUse;

    @Schema(description = "分摊金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分摊金额")
    private BigDecimal publicAmount;

    @Schema(description = "状态，1启动，0审核中，4禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态，1启动，0审核中，4禁用")
    private String status;

    @Schema(description = "绑定账单")
    @ExcelProperty("绑定账单")
    private String bindBill;

    @Schema(description = "账单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25916")
    @ExcelProperty("账单ID")
    private Long billId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}