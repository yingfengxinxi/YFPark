package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerContacts.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 租客联系人新增/修改 Request VO")
@Data
public class OwnerContactsSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13542")
    private Long id;

    @Schema(description = "机构ID", example = "21504")
    private Long orgId;

    @Schema(description = "联系人姓名", example = "王五")
    private String name;

    @Schema(description = "手机")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "所属公司id", example = "4221")
    private Long ownerId;

    @Schema(description = "通讯地址")
    private String address;

    @Schema(description = "是否默认联系人", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否默认联系人不能为空")
    private Integer isDefault;

}