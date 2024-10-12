package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 自定义抄表任务日志新增/修改 Request VO")
@Data
public class EnergyTaskLogSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13340")
    private Long id;

    @Schema(description = "机构ID", example = "25383")
    private Long orgId;

    @Schema(description = "计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9130")
    @NotNull(message = "计划id不能为空")
    private Long planId;

    @Schema(description = "计划key")
    private String planKey;

    @Schema(description = "周期")
    private String crontab;

    @Schema(description = "规则")
    private String rule;

    @Schema(description = "生成时间")
    private LocalDateTime buildTime;

    @Schema(description = "任务执行时间")
    private LocalDateTime taskTime;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "状态不能为空")
    private String status;

    @Schema(description = "执行顺序")
    private Integer sort;

    @Schema(description = "累计执行", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "累计执行不能为空")
    private Integer total;

}