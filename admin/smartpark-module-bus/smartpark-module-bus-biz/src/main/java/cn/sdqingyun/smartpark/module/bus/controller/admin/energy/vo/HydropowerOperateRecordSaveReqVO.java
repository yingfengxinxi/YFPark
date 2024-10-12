package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 水电表开关记录新增/修改 Request VO")
@Data
public class HydropowerOperateRecordSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12677")
    private Long id;

    @Schema(description = "机构ID", example = "13278")
    private Long orgId;

    @Schema(description = "水电表id", example = "21409")
    private Long energyId;

    @Schema(description = "操作人、自动处理为空", example = "1535")
    private Long operateUid;

    @Schema(description = "操作状态、0关闸、1开闸 2、重置", example = "2")
    private String status;

    @Schema(description = "操作原因", example = "不香")
    private String reason;

    @Schema(description = "设备类型", example = "1")
    private String deviceType;

    @Schema(description = "如果是以电控水、对应的水表id", example = "32701")
    private Long deviceWaterId;

}