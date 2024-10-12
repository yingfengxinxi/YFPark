package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 耗材业务处置新增/修改 Request VO")
@Data
public class PropertyStuffHandleSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24238")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9336")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    private String processNumber;

    @Schema(description = "发起部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30395")
    @NotNull(message = "发起部门id不能为空")
    private Long departmentId;

    @Schema(description = "发起部门名称", example = "张三")
    private String departmentName;

    @Schema(description = "处置仓库id", example = "2738")
    private Long depositoryId;

    @Schema(description = "发起时间")
    private LocalDateTime launchTime;

    @Schema(description = "合计金额", example = "12235")
    private BigDecimal totalPrice;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结不能为空")
    private Short status;

    @Schema(description = "处置原因", example = "你说的对")
    private String remark;

    @Schema(description = "发起人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "4075")
    @NotNull(message = "发起人uid不能为空")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "4792")
    @NotNull(message = "修改人uid不能为空")
    private Long muserUid;

}