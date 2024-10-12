package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 水电表开关记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class HydropowerOperateRecordRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12677")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "13278")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "水电表id", example = "21409")
    @ExcelProperty("水电表id")
    private Long energyId;

    @Schema(description = "操作人、自动处理为空", example = "1535")
    @ExcelProperty("操作人、自动处理为空")
    private Long operateUid;
    private String operateName;

    @Schema(description = "操作状态、0关闸、1开闸 2、重置", example = "2")
    @ExcelProperty("操作状态、0关闸、1开闸 2、重置")
    private String status;
    private String statusName;

    @Schema(description = "操作原因", example = "不香")
    @ExcelProperty("操作原因")
    private String reason;

    @Schema(description = "设备类型", example = "1")
    @ExcelProperty("设备类型")
    private String deviceType;

    @Schema(description = "如果是以电控水、对应的水表id", example = "32701")
    @ExcelProperty("如果是以电控水、对应的水表id")
    private Long deviceWaterId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}