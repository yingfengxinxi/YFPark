package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 预约场地分类新增/修改 Request VO")
@Data
public class ResvPlaceCategorySaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "8475")
    private Long id;

    @Schema(description = "机构ID",example = "11652")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "应用标识不能为空")
    private String appSign;

    @Schema(description = "所属项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4008")
    @NotNull(message = "所属项目ID不能为空")
    private Long villageId;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "分类名称不能为空")
    private String name;

    @Schema(description = "分类图片", example = "https://xxx")
    private String imageUrl;

    @Schema(description = "预约限制")
    private String reservationRule;

    @Schema(description = "场地可容纳人数")
    private Integer capacity;

    @Schema(description = "联系人电话")
    private String contactMobile;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @Schema(description = "详细介绍", example = "你说的对")
    private String description;

    @Schema(description = "冗余字段")
    private String cache;

    @Schema(description = "状态: 1为开启 0为关闭", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态: 1为开启 0为关闭不能为空")
    private Integer status;

}