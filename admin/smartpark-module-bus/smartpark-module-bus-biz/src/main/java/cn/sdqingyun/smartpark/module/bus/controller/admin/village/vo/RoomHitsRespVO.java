package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 房间点击量 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RoomHitsRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25389")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30962")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "32409")
    @ExcelProperty("房间ID")
    private Long roomId;

    @Schema(description = "日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("日期")
    private LocalDateTime day;

    @Schema(description = "点击量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("点击量")
    private Integer hits;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}