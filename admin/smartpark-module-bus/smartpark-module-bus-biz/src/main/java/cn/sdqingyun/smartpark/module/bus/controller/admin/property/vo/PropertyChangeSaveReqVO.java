package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产变更领用人新增/修改 Request VO")
@Data
public class PropertyChangeSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号")
    private Long id;

    @Schema(description = "机构id")
    private Long orgId;

    @Schema(description = "资产id")
    private String propertyIds;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据状态", example = "2")
    private Integer status;

    @Schema(description = "变更后使用人uid")
    private Long afterUseUid;

    @Schema(description = "变更后使用部门id")
    private Long afterUseDepartmentId;

    @Schema(description = "变更时间")
    private LocalDateTime afterTime;

    @Schema(description = "处理人")
    private Long operateUid;

    @Schema(description = "处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "变更说明", example = "随便")
    private String remark;

    @Schema(description = "操作人uid")
    private Long cuserUid;

    @Schema(description = "选择的派发单据编号property_handout中的id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30010")
    @NotNull(message = "选择的派发单据编号id不能为空")
    private Long muserUid;

}