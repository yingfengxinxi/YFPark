package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 耗材业务退库新增/修改 Request VO")
@Data
public class PropertyStuffRetreatSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11495")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31583")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    private String processNumber;

    @Schema(description = "退库人uid", example = "7917")
    private Long retreatUid;

    @Schema(description = "部门id", example = "14623")
    private Long departmentId;

    @Schema(description = "部门名称", example = "赵六")
    private String departmentName;

    @Schema(description = "入库仓库id", example = "12122")
    private Long depositoryId;

    @Schema(description = "退库时间")
    private LocalDateTime retreatDate;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结不能为空")
    private Short status;

    @Schema(description = "退库备注", example = "随便")
    private String remark;

    @Schema(description = "处理人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "5743")
    @NotNull(message = "处理人uid不能为空")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "26278")
    @NotNull(message = "修改人uid不能为空")
    private Long muserUid;

}