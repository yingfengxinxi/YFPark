package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 机构合同附件新增/修改 Request VO")
@Data
public class AnnexSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25017")
    private Long id;

    @Schema(description = "基础合同,合同主表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3510")
    @NotNull(message = "基础合同,合同主表id不能为空")
    private Long contractId;

    @Schema(description = "文件名/文件夹", example = "赵六")
    private String name;

    @Schema(description = "1=pc;2=phone", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "1=pc;2=phone不能为空")
    private Integer annexSource;

    @Schema(description = "文件路径")
    private String filePath;

}