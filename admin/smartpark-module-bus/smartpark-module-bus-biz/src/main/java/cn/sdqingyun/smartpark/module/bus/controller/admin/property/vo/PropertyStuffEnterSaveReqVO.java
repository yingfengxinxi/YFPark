package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 耗材入库记录新增/修改 Request VO")
@Data
public class PropertyStuffEnterSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14652")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6534")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "入库仓库id", example = "31128")
    private Long depositoryId;

    @Schema(description = "入库处理人uid", example = "32101")
    private Long enterUid;

    @Schema(description = "入库时间")
    private LocalDateTime enterTime;

    @Schema(description = "供应商")
    private String supplier;

    @Schema(description = "合计金额", example = "23127")
    private BigDecimal totalPrice;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结不能为空")
    private Short status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "15999")
    @NotNull(message = "操作人uid不能为空")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "23291")
    @NotNull(message = "修改人uid不能为空")
    private Long muserUid;

}