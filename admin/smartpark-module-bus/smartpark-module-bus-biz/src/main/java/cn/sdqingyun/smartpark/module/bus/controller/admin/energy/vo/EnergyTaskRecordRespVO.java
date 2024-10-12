package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 自定义抄表任务记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyTaskRecordRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15308")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "30135")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15672")
    @ExcelProperty("计划id")
    private Long planId;

    @Schema(description = "任务id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31056")
    @ExcelProperty("任务id")
    private Long taskId;

    @Schema(description = "自定义表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31340")
    @ExcelProperty("自定义表id")
    private Long energyId;

    @Schema(description = "第三方任务id", example = "8056")
    @ExcelProperty("第三方任务id")
    private Long thirdTaskId;

    @Schema(description = "抄录数据json")
    @ExcelProperty("抄录数据json")
    private String extraData;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}