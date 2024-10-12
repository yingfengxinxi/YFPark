package cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.util.*;

import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 工单子类费用设置新增/修改 Request VO")
@Data
public class CategoryFeeSetSaveReqVO extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25196")
    private Long id;

    @Schema(description = "机构id", example = "9721")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "工单大类id", example = "866")
    private Long categoryId;

    @Schema(description = "工单子类id", example = "17139")
    private Long subcatId;

    @Schema(description = "费用设置")
    private String feeSet;

    @Schema(description = "耗材设置")
    private String stuffSet;

    @Schema(description = "服务项设置")
    private String serviceSet;

    @Schema(description = "启用时间段模式0=否1=是")
    private String isUse;

    @Schema(description = "预约时段设置")
    private CategoryFeeSetReservationDateSaveReqVO reservationDateSave;

}