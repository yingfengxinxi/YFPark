package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 项目租控管理菜单排序新增/修改 Request VO")
@Data
public class ControlMenuSortSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23297")
    private Long id;

    @Schema(description = "菜单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18964")
    @NotNull(message = "菜单ID不能为空")
    private Long menuId;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27310")
    @NotNull(message = "机构ID不能为空")
    private Long orgId;

    @Schema(description = "排序值，值越高越前", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序值，值越高越前不能为空")
    private Integer sort;

}