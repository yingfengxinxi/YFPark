package cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 车辆操作日志新增/修改 Request VO")
@Data
public class ParkCarsOperatorLogSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7226")
    private Long id;

    @Schema(description = "0添加、1修改、2删除、3缴费，关联订单、4导入", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String type;

    @Schema(description = "车辆id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17587")
    private Long carsId;

    @Schema(description = "订单id", example = "22830")
    private String orderId;

    @Schema(description = "修改前", requiredMode = Schema.RequiredMode.REQUIRED)
    private String before;

    @Schema(description = "修改后", requiredMode = Schema.RequiredMode.REQUIRED)
    private String after;

}