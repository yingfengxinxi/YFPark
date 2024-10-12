package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 网关管理新增/修改 Request VO")
@Data
public class HengHanCollectorSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "5370")
    private Long id;

    @Schema(description = "机构iD", example = "26575")
    private Long orgId;

    @Schema(description = "网关设备号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18444")
    @NotEmpty(message = "网关设备号不能为空")
    private String cid;

    @Schema(description = "项目id", example = "4822")
    private Long villageId;

    @Schema(description = "网关名称", example = "赵六")
    private String name;

    @Schema(description = "网关型号", example = "1")
    private String type;

    @Schema(description = "最后心跳时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date lastHeartTime;

}