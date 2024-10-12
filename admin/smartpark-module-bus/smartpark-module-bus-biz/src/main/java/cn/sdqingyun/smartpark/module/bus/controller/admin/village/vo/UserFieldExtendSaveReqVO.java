package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 用户扩展信息自定义系统设置新增/修改 Request VO")
@Data
public class UserFieldExtendSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26660")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7430")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6702")
    @NotNull(message = "项目id不能为空")
    private Long villageId;

    @Schema(description = "关联 village_user_system_field_extend id", requiredMode = Schema.RequiredMode.REQUIRED, example = "16431")
    @NotNull(message = "关联 village_user_system_field_extend id不能为空")
    private Long sysFieldId;

    @Schema(description = "字段名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "字段名称不能为空")
    private String fieldName;

    @Schema(description = "表单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "表单名称不能为空")
    private String inputName;

    @Schema(description = "表单名称自定义", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "表单名称自定义不能为空")
    private String inputNameCus;

    @Schema(description = "输入框类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "输入框类型不能为空")
    private String inputType;

    @Schema(description = "表单默认值 ")
    private String defaultInputValue;

    @Schema(description = "表单默认值 ")
    private String defaultFieldValue;

    @Schema(description = "是否启用，1-启用,0-禁用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否启用，1-启用,0-禁用不能为空")
    private Integer isEnable;

    @Schema(description = "是否必填 1-必填", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否必填 1-必填不能为空")
    private Integer isRequired;

    @Schema(description = "是否允许修改表单名称，1允许，0不允许", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否允许修改表单名称，1允许，0不允许不能为空")
    private Integer isAllowModifie;

    @Schema(description = "JSON 字段集合")
    private String jsonFields;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer sort;

}