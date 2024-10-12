package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 机构账单收支流水附件新增/修改 Request VO")
@Data
public class OrgBillAnnexSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22340")
    private Long id;

    @Schema(description = "附件唯一标识", example = "8246")
    private String objectId;

    @Schema(description = "流水id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10068")
    @NotNull(message = "流水id不能为空")
    private Long sourceId;

    @Schema(description = "父级id", example = "16007")
    private Long parentId;

    @Schema(description = "置顶排序", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sort;

    @Schema(description = "文件名/文件夹", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "文件名/文件夹不能为空")
    private String name;

    @Schema(description = "账单操作类型;1=账单附件;2=流水附件", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "账单操作类型")
    private String type;

    @Schema(description = "1=pc;2=phone", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "1=pc;2=phone不能为空")
    private String annexSource;

    @Schema(description = "文件路径", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "文件路径不能为空")
    private String filePath;

}