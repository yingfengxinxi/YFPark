package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 耗材业务耗材退还新增/修改 Request VO")
@Data
public class PropertyStuffReturnSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18929")
    private Long id;

    @Schema(description = "机构id")
    private Long orgId;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    private String processNumber;

    @Schema(description = "关联单据类型", example = "1")
    private String relationType;

    @Schema(description = "关联单据编号")
    private String relationNumber;

    @Schema(description = "申请部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5775")
    @NotNull(message = "申请部门id不能为空")
    private Long departmentId;

    @Schema(description = "申请部门", example = "芋艿")
    private String departmentName;

    @Schema(description = "归还仓库id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5855")
    @NotNull(message = "归还仓库id不能为空")
    private Long depositoryId;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结")
    private Integer status;

    @Schema(description = "归还原因", example = "你说的对")
    private String remark;

    @Schema(description = "申请人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid")
    private Long muserUid;

}