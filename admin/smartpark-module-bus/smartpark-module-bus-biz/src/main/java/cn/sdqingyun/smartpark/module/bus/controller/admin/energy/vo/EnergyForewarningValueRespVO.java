package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 误抄表预警 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyForewarningValueRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25618")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "5441")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "值")
    @TableField("`value`")
    private BigDecimal value;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}