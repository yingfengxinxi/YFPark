package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 表种类管理新增/修改 Request VO")
@Data
public class EnergyTypeSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2038")
    private Long id;

    @Schema(description = "机构ID", example = "6410")
    private Long orgId;

    @Schema(description = "表种类名称", example = "张三")
    private String name;

    @Schema(description = "费用类型", example = "1")
    private String costType;

    @Schema(description = "费用类型文本")
    private String costTypeTxt;

    @Schema(description = "设备类型", example = "1")
    private String equipType;

    @Schema(description = "计费单位 如度、方等")
    private String unit;

    @Schema(description = "是否自动断电 0否 1是")
    private String isBroken;

    @Schema(description = "可逾期天数")
    private Integer overdueDay;

    @Schema(description = "是否已电控水 0否 1是", example = "2")
    private String cutType;

    @Schema(description = "提醒值")
    private BigDecimal remindValue;

}