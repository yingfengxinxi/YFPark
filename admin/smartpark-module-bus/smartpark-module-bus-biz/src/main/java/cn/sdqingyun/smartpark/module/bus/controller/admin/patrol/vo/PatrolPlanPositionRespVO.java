package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 应用巡检计划绑定巡检点 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PatrolPlanPositionRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25001")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "29430")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10478")
    @ExcelProperty("计划id")
    private Long planId;

    @Schema(description = "巡检点id", requiredMode = Schema.RequiredMode.REQUIRED, example = "66")
    @ExcelProperty("巡检点id")
    private Long positionId;

    @Schema(description = "是否扫码打卡0=否1=是")
    @ExcelProperty("是否扫码打卡0=否1=是")
    private String isScanCodeCheck;

    @Schema(description = "是否NFC打卡0=否1=是")
    @ExcelProperty("是否NFC打卡0=否1=是")
    private String isNfcClock;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}