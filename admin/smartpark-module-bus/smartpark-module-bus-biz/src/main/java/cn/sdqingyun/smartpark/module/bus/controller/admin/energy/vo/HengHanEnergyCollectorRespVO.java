package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 采集器电表关联表（一对多的关系） Response VO")
@Data
@ExcelIgnoreUnannotated
public class HengHanEnergyCollectorRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12712")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构iD", requiredMode = Schema.RequiredMode.REQUIRED, example = "29633")
    @ExcelProperty("机构iD")
    private Long orgId;

    @Schema(description = "采集器id", requiredMode = Schema.RequiredMode.REQUIRED, example = "16013")
    @ExcelProperty("采集器id")
    private Long collectorId;

    @Schema(description = "水电表id、关联energy表id", example = "31107")
    @ExcelProperty("水电表id、关联energy表id")
    private Long energyId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}