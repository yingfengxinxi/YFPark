package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 巡检点位数据新增/修改 Request VO")
@Data
public class PatrolPositionSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10598")
    private Long id;

    @Schema(description = "机构Id", example = "28237")
    private Long orgId;

    @Schema(description = "应用标识EQUIPMENT_INSPECTION=巡检;SECURITY_INSPECTION=安防")
    private String application;

    @Schema(description = "巡检点编码")
    private String number;

    @Schema(description = "巡检点名称", example = "李四")
    private String name;

    @Schema(description = "资产位置id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30735")
    @NotNull(message = "资产位置id不能为空")
    private Long positionId;

    @Schema(description = "资产位置父级id集合（多个值使用逗号(,)隔开）", example = "1,2,3,4")
    private String positionParentIds;

    @Schema(description = "nfc卡号ID", example = "22491")
    private String nfcCardId;

    @Schema(description = "巡检表单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7330")
    @NotNull(message = "巡检表单id不能为空")
    private Long formId;

    @Schema(description = "巡检点图片")
    private String images;

    @Schema(description = "绑定的资产Id")
    private String propertyId;

    @Schema(description = "启用状态;0=禁用,1=启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "启用状态;0=禁用,1=启用不能为空")
    private String status;

}