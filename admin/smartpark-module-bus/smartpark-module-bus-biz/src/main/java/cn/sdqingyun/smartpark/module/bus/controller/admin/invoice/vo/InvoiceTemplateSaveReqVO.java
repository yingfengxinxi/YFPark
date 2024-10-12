package cn.sdqingyun.smartpark.module.bus.controller.admin.invoice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 发票模板配置新增/修改 Request VO")
@Data
public class InvoiceTemplateSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2652")
    private Long id;

    @Schema(description = "发票模板名称", example = "李四")
    private String name;

    @Schema(description = "售方id", example = "5249")
    private Long sellerId;

    @Schema(description = "税率规则id;多个id使用逗号隔开(1,2,3)")
    private String taxRule;

    @Schema(description = "是否开通电子发票 0不开通1=开通", requiredMode = Schema.RequiredMode.REQUIRED)
    private String eInvoice;

    @Schema(description = "税票通配置信息-appid", example = "14490")
    private String sptAppid;

    @Schema(description = "税票通配置信息-appsecret")
    private String sptAppsecret;

    @Schema(description = "税票通配置信息-税号")
    private String sptNumber;

    @Schema(description = "后台账单缴费自动开票（1是/0否）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String orgAuto;

    @Schema(description = "用户账单缴费自动开票（1是/0否）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userAuto;

    @Schema(description = "应用楼宇id;多个id使用逗号隔开(1,2,3)")
    private String buildBind;

    @Schema(description = "状态;0=禁用；1=启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String status;

    @Schema(description = "模板是否开启审批;0=否1=是", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isApproval;

}