package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 项目电话类型新增/修改 Request VO")
@Data
public class PhoneCategorySaveReqVO {

    @Schema(description = "编号")
    private Long id;

    @Schema(description = "机构ID")
    private Long orgId;

    @Schema(description = "项目ID")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "分类名称")
    @NotEmpty(message = "分类名称不能为空")
    private String catName;

    @Schema(description = "排序值")
    private Integer sort;

    @Schema(description = "状态（1正常，0隐藏）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态（1正常，0隐藏）不能为空")
    private Integer status;

}