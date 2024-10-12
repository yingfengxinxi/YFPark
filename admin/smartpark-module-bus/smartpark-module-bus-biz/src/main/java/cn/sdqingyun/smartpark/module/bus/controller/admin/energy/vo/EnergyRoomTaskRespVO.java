package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 房间对应抄表任务 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyRoomTaskRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20114")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14024")
    @ExcelProperty("房间ID")
    private Long roomId;

    @Schema(description = "关联抄表任务ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22420")
    @ExcelProperty("关联抄表任务ID")
    private Long energyTaskId;

    @Schema(description = "状态，1启动，0审核中，4禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态，1启动，0审核中，4禁用")
    private String status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}