package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 预约应用新增/修改 Request VO")
@Data
public class ResvApplicationSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", example = "14034")
    private Long id;

    @Schema(description = "应用名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "应用名称不能为空")
    private String name;

    @Schema(description = "应用简称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "应用简称不能为空")
    private String shortName;

    @Schema(description = "应用标志", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "应用标志不能为空")
    private String sign;

    @Schema(description = "图标", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "图标不能为空")
    private String icon;

    @Schema(description = "取消订单规则",example = "{\"unpaid_timeout_cancel\": {\"number\": 10, \"time_unit\": \"minute\"}}")
    private String cancelOrderRule;

    @Schema(description = "是否支持退款 0:不支持，1:支持", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "是否支持退款不能为空")
    private Integer refundSupported;

    @Schema(description = "退款规则",example = "{\"reviewer\": [1], \"start_time_limit\": {\"number\": 2, \"time_unit\": \"hour\"}, \"is_notice_enabled\": 1, \"is_review_enabled\": 2, \"refund_deadline_day\": 7}")
    private String refundRule;

}