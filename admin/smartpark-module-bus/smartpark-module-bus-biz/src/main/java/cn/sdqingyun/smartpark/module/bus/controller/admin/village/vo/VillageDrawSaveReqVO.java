package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 项目绘制数据新增/修改 Request VO")
@Data
public class VillageDrawSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11936")
    private Long id;

    @Schema(description = "机构id", example = "10948")
    private Long orgId;

    @Schema(description = "项目id", example = "14290")
    private Long villageId;

    @Schema(description = "绘制内容")
    private String drawInfo;

    @Schema(description = "设置")
    private String settingInfo;

}