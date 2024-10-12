package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 账单缴费通知单模板新增/修改 Request VO")
@Data
public class OrgBillNoticeTemplateSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13483")
    private Long id;

    @Schema(description = "通知单模板名称", example = "赵六")
    private String name;

    @Schema(description = "模板上传地址")
    private String templatePath;

    @Schema(description = "应用楼宇")
    private String buildBind;

}