package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 项目分区新增/修改 Request VO")
@Data
public class ZoneSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13949")
    private Long id;

    @Schema(description = "分区编号")
    private Long zoneNumber;

    @Schema(description = "分区名称", example = "李四")
    private String zoneName;

    @Schema(description = "项目ID", example = "21041")
    private Long villageId;

    @Schema(description = "排序，越大越前", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序，越大越前不能为空")
    private Integer sort;

    @Schema(description = "数据状态，0隐藏，1展示", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "数据状态，0隐藏，1展示不能为空")
    private Integer status;

}