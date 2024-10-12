package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 电表操作id关联表、回调用 Response VO")
@Data
@ExcelIgnoreUnannotated
public class HengHanEnergyOprRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17994")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构iD", example = "22160")
    @ExcelProperty("机构iD")
    private Long orgId;

    @Schema(description = "操作ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18341")
    @ExcelProperty("操作ID")
    private Long oprId;

    @Schema(description = "电表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14502")
    @ExcelProperty("电表id")
    private Long energyId;

    @Schema(description = "操作类型、read（抄表） open（开闸） close(关闸)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("操作类型、read（抄表） open（开闸） close(关闸)")
    private String type;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}