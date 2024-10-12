package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产派发仓库信息新增/修改 Request VO")
@Data
public class PropertyHandoutDepositorySaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12595")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20387")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "资产id", example = "3730")
    private Long propertyId;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "入库处理人", requiredMode = Schema.RequiredMode.REQUIRED, example = "7698")
    @NotNull(message = "入库处理人不能为空")
    private Long operateUid;

    @Schema(description = "入库处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "入库备注", example = "你猜")
    private String remark;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "13191")
    @NotNull(message = "操作人uid不能为空")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "31199")
    @NotNull(message = "修改人uid不能为空")
    private Long muserUid;

}