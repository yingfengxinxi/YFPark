package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 工单规则设置新增/修改 Request VO")
@Data
public class WorkOrderRuleSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23572")
    private Long id;

    @Schema(description = "机构ID", example = "1517")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "规则名称", example = "李四")
    private String name;

    @Schema(description = "抢单数上限;/个", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer snatchLimit;

    @Schema(description = "抢单前置时长;/分钟", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer preposeTime;

    @Schema(description = "抢单限制时长;单位/分钟", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer robTime;

    @Schema(description = "可退款时长;/天", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer refundTime;

    @Schema(description = "取消订单时长;/分钟", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer cancelTime;

    @Schema(description = "重新打开时长;/天", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer restartTime;

    @Schema(description = "绑定的楼宇信息json")
    private String buildBind;

    @Schema(description = "是否为默认配置", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isDefault;

}