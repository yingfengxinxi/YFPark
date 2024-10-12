package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 项目单元新增/修改 Request VO")
@Data
public class UnitSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17252")
    private Long id;

    @Schema(description = "单元编号")
    private Long unitNumber;

    @Schema(description = "单元名称", example = "李四")
    private String unitName;

    @Schema(description = "楼栋ID", example = "7648")
    private Long buildId;

    @Schema(description = "分区ID", example = "13430")
    private Long zoneId;

    @Schema(description = "项目ID", example = "23241")
    private Long villageId;

    @Schema(description = "排序，越大越前", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序，越大越前不能为空")
    private Integer sort;

    @Schema(description = "数据状态(1使用，0隐藏)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "数据状态(1使用，0隐藏)不能为空")
    private Integer status;

}