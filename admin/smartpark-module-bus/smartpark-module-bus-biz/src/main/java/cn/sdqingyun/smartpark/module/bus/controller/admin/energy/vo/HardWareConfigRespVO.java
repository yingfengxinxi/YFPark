package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 智能硬件配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class HardWareConfigRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "16044")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", example = "16415")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "项目id", example = "16294")
    @ExcelProperty("项目id")
    private Long villageId;

    @Schema(description = "楼宇id", example = "1423")
    @ExcelProperty("楼宇id")
    private Long buildId;

    @Schema(description = "是否自动断电 1是 0否", example = "1")
    @ExcelProperty("是否自动断电 1是 0否")
    private String electricityType;

    @Schema(description = "电费可逾期天数")
    @ExcelProperty("电费可逾期天数")
    private Integer electricityLimitDay;

    @Schema(description = "是否自动断水 1是 0否", example = "1")
    @ExcelProperty("是否自动断水 1是 0否")
    private String waterType;

    @Schema(description = "1是已电控水 2直接断水", example = "2")
    @ExcelProperty("1是已电控水 2直接断水")
    private String waterCutType;

    @Schema(description = "水费可逾期天数")
    @ExcelProperty("水费可逾期天数")
    private Integer waterLimitDay;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}