package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产归还新增/修改 Request VO")
@Data
public class PropertyRevertSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24566")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5112")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "项目id", example = "25094")
    private Long villageId;

    @Schema(description = "楼宇id", example = "168")
    private Long buildId;

    @Schema(description = "房间id", example = "18820")
    private Long roomId;

    @Schema(description = "资产id集合json")
    private String propertyIds;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据状态", example = "2")
    private Integer status;

    @Schema(description = "归还处理人", requiredMode = Schema.RequiredMode.REQUIRED, example = "28958")
    @NotNull(message = "归还处理人不能为空")
    private Long revertUid;

    @Schema(description = "归还后使用部门", example = "26702")
    private Long departmentId;

    @Schema(description = "归还时间")
    private LocalDateTime revertTime;

    @Schema(description = "处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "归还备注", example = "随便")
    private String remark;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "16543")
    @NotNull(message = "操作人uid不能为空")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "5768")
    @NotNull(message = "修改人uid不能为空")
    private Long muserUid;

}