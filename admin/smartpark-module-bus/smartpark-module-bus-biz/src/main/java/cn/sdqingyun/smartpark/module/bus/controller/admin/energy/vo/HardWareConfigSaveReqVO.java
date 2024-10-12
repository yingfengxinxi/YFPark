package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 智能硬件配置新增/修改 Request VO")
@Data
public class HardWareConfigSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "16044")
    private Long id;

    @Schema(description = "机构id", example = "16415")
    private Long orgId;

    @Schema(description = "项目id", example = "16294")
    private Long villageId;

    @Schema(description = "楼宇id", example = "1423")
    private Long buildId;

    @Schema(description = "是否自动断电 1是 0否", example = "1")
    private String electricityType;

    @Schema(description = "电费可逾期天数")
    private Integer electricityLimitDay;

    @Schema(description = "是否自动断水 1是 0否", example = "1")
    private String waterType;

    @Schema(description = "1是已电控水 2直接断水", example = "2")
    private String waterCutType;

    @Schema(description = "水费可逾期天数")
    private Integer waterLimitDay;

}