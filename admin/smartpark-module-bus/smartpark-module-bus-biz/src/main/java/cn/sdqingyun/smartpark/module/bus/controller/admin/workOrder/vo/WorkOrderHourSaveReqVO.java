package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 工单工时配置新增/修改 Request VO")
@Data
public class WorkOrderHourSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24279")
    private Long id;

    @Schema(description = "机构id", example = "351")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "工时名称", example = "李四")
    private String name;

    @Schema(description = "工时费用")
    private BigDecimal hourFee;

    @Schema(description = "工时计算规则;1=保留两位小数;2=去除小数部分;3=四舍五入去除小数;", requiredMode = Schema.RequiredMode.REQUIRED)
    private String computeRule;

    @Schema(description = "部门id", example = "4416")
    private Long departmentId;

    @Schema(description = "岗位信息json", example = "10182")
    private String stationId;

    @Schema(description = "岗位员工信息json")
    private String postUids;

    @Schema(description = "启用状态0=禁用1=启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String status;

    @Schema(description = "是否为默认配置0=否1=是", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isDefault;

}