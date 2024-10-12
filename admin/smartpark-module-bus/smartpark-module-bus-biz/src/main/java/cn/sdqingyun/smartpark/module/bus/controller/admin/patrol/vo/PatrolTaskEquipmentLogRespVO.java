package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 应用巡检任务日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PatrolTaskEquipmentLogRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10900")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "4168")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识")
    @ExcelProperty("应用标识")
    private String application;

    @Schema(description = "计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12957")
    @ExcelProperty("计划id")
    private Long planId;

    @Schema(description = "计划key")
    @ExcelProperty("计划key")
    private String planKey;

    @Schema(description = "周期")
    @ExcelProperty("周期")
    private String crontab;

    @Schema(description = "规则")
    @ExcelProperty("规则")
    private Integer rule;

    @Schema(description = "生成时间")
    @ExcelProperty("生成时间")
    private LocalDateTime buildTime;

    @Schema(description = "任务执行时间")
    @ExcelProperty("任务执行时间")
    private LocalDateTime taskTime;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "执行顺序")
    @ExcelProperty("执行顺序")
    private Integer sort;

    @Schema(description = "累计执行", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("累计执行")
    private Integer total;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}