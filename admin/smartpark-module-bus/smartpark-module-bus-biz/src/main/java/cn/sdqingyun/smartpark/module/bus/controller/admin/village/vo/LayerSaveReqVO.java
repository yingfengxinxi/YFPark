package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 项目楼层新增/修改 Request VO")
@Data
public class LayerSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26108")
    private Long id;

    @Schema(description = "楼层编号")
    private Long layerNumber;

    @Schema(description = "楼层名称", example = "王五")
    private String layerName;

    @Schema(description = "单元ID", example = "31381")
    private Long unitId;

    @Schema(description = "楼栋ID", example = "5016")
    private Long buildId;

    @Schema(description = "分区ID", example = "16890")
    private Long zoneId;

    @Schema(description = "项目ID", example = "20265")
    private Long villageId;

    @Schema(description = "排序，越大越前", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sort;

    @Schema(description = "数据状态(1使用，0隐藏)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "数据状态(1使用，0隐藏)不能为空")
    private Integer status;

    @Schema(description = "3D模型")
    private String threeDimensionalFile;

    @Schema(description = "3D模型物体ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26630")
    private String threeDimensionalId;

}