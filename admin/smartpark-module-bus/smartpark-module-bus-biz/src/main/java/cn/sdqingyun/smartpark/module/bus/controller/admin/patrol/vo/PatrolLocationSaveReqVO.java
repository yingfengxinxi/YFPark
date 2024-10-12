package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 位置新增/修改 Request VO")
@Data
public class PatrolLocationSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26137")
    private Long id;

    @Schema(description = "机构ID", example = "25119")
    private Long orgId;

    @Schema(description = "位置编码")
    @NotNull(message = "位置编码不能为空")
    private String number;

    @Schema(description = "位置名称", example = "王五")
    @NotNull(message = "位置名称不能为空")
    private String name;

    @Schema(description = "位置级别字符串，逗号拼接上级id")
    private String level;

    @Schema(description = "位置级别字符串，斜杠拼接上级name")
    private String levelName;

    @Schema(description = "父级id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31662")
    @NotNull(message = "父级id不能为空")
    private Long parentId;

    @Schema(description = "参数")
    private String param;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sort;

    @Schema(description = "显示状态，2禁用 1显示", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String status;

}