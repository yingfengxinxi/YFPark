package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 应用巡检任务变更执行人日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TaskUserLogRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11668")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "1167")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应用标识")
    private String application;

    @Schema(description = "任务id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26464")
    @ExcelProperty("任务id")
    private Long taskId;

    @Schema(description = "旧执勤人id", requiredMode = Schema.RequiredMode.REQUIRED, example = "13253")
    @ExcelProperty("旧执勤人id")
    private Long oldUid;

    @Schema(description = "旧执勤人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("旧执勤人姓名")
    private String oldName;

    @Schema(description = "新执勤人id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26820")
    @ExcelProperty("新执勤人id")
    private Long newUid;

    @Schema(description = "新执勤人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("新执勤人姓名")
    private String newName;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}