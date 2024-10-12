package cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 退租原因新增/修改 Request VO")
@Data
public class TagTerminationSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26504")
    private Long id;

    @Schema(description = "标签名称", example = "芋艿")
    private String name;

    @Schema(description = "标签描述")
    private String remarks;

    @Schema(description = "标签样式")
    private String color;

    @Schema(description = "状态，1启用，0禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态，1启用，0禁用不能为空")
    private Integer status;

}