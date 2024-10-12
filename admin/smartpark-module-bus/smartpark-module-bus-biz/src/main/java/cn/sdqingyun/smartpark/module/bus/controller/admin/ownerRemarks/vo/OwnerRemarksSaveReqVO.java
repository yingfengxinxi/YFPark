package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerRemarks.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 租客备注信息新增/修改 Request VO")
@Data
public class OwnerRemarksSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20488")
    private Long id;

    @Schema(description = "租客id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19772")
    @NotNull(message = "租客id不能为空")
    private Long ownId;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21676")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "备注内容", example = "你猜")
    private String remark;

    @Schema(description = "操作人uid", example = "13731")
    private Long operationUid;

    @Schema(description = "操作人姓名", example = "张三")
    private Long operationName;

    @Schema(description = "操作时间")
    private LocalDateTime operationTime;

}