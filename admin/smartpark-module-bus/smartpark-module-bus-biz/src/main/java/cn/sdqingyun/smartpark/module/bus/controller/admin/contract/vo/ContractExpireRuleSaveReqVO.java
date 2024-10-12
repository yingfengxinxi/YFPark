package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 合同到期提醒规则新增/修改 Request VO")
@Data
public class ContractExpireRuleSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19099")
    private Long id;

    @Schema(description = "合同到期规则名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "合同到期规则名称不能为空")
    private String ruleName;

    @Schema(description = "关联楼宇id，多个id用逗号隔开", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "关联楼宇id，多个id用逗号隔开不能为空")
    private String relationBuilds;

    @Schema(description = "提醒方式0=站内1=公众号【数据字典:REMINDER_METHOD】")
    private String reminderMethod;


    /**
     *
     */
    @Schema(description = "提前几天提醒(天)")
    private Integer reminderDay;

}