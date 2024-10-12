package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.math.BigDecimal;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 误抄表预警新增/修改 Request VO")
@Data
public class EnergyForewarningValueSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25618")
    private Long id;

    @Schema(description = "机构ID", example = "5441")
    private Long orgId;

    @Schema(description = "值")
    @TableField("`value`")
    private BigDecimal value;

}