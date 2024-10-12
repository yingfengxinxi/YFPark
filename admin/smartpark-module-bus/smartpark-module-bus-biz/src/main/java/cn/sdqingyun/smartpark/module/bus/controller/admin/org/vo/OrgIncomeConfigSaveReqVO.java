package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 机构收支确认配置新增/修改 Request VO")
@Data
public class OrgIncomeConfigSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32201")
    private Long id;

    @Schema(description = "确认天数,超过此天数不可取消确认", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "确认天数,超过此天数不可取消确认不能为空")
    private Integer lmtDay;

    @Schema(description = "附加信息")
    private String extra;

}