package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产操作日志新增/修改 Request VO")
@Data
public class PropertyOperateLogSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "465")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7070")
    @NotNull(message = "机构ID不能为空")
    private Long orgId;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "12977")
    @NotNull(message = "操作人uid不能为空")
    private Long operateUid;

    @Schema(description = "资产id信息json")
    private String propertyJson;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "操作类型", example = "1")
    private String operateType;

    @Schema(description = "日志类型", example = "1")
    private String logType;

    @Schema(description = "操作人", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "操作人不能为空")
    private String operateUser;

    @Schema(description = "操作时间")
    private LocalDateTime operateTime;

    @Schema(description = "操作内容")
    private String operateContent;

    @Schema(description = "其他")
    private String otherContent;

    @Schema(description = "操作权限")
    private String operate;

}