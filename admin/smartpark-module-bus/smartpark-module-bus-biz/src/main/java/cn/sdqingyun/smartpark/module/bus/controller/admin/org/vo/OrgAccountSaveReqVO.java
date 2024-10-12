package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 收支账户配置新增/修改 Request VO")
@Data
public class OrgAccountSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18788")
    private Long id;

    @Schema(description = "条目名称", example = "王五")
    private String name;

    @Schema(description = "收款公司")
    private String company;

    @Schema(description = "开户银行")
    private String bank;

    @Schema(description = "银行账号", example = "10688")
    private String bankAccount;

    @Schema(description = "总分类账科目")
    private String subject;

    @Schema(description = "应用的楼宇")
    private String build;

    @Schema(description = "是否启用0=否1=是", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "是否启用0=否1=是不能为空")
    private String status;

}