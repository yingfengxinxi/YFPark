package cn.sdqingyun.smartpark.module.bus.controller.admin.user.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 机构用户自定义操作配置新增/修改 Request VO")
@Data
public class SpercialSettingSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号",  example = "17588")
    private Long id;

    @Schema(description = "用户ID", example = "13108")
    private Long uid;

    @Schema(description = "机构ID", example = "243")
    private Long orgId;

    @Schema(description = "自定义类型", example = "2")
    private String type;

    @Schema(description = "用户自定义配置内容json，JSON串({buildId: [1011, 1021, 1031], villageId: [101, 102, 103]})")
    private String content;

}