package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 应用巡检任务变更执行人日志新增/修改 Request VO")
@Data
public class TaskUserLogSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11668")
    private Long id;

    @Schema(description = "机构ID", example = "1167")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    private String application;

    @Schema(description = "任务id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26464")
    @NotNull(message = "任务id不能为空")
    private Long taskId;

    @Schema(description = "旧执勤人id", requiredMode = Schema.RequiredMode.REQUIRED, example = "13253")
    @NotNull(message = "旧执勤人id不能为空")
    private Long oldUid;

    @Schema(description = "旧执勤人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "旧执勤人姓名不能为空")
    private String oldName;

    @Schema(description = "新执勤人id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26820")
    @NotNull(message = "新执勤人id不能为空")
    private Long newUid;

    @Schema(description = "新执勤人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "新执勤人姓名不能为空")
    private String newName;

}