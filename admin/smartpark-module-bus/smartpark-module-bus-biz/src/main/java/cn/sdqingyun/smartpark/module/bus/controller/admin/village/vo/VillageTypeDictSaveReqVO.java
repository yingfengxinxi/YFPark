package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 项目类型字典新增/修改 Request VO")
@Data
public class VillageTypeDictSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30744")
    private Long id;

    @Schema(description = "类型别名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "类型别名不能为空")
    private String typeAlias;

    @Schema(description = "原字", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "原字不能为空")
    private String words;

    @Schema(description = "中文")
    private String zhCn;

    @Schema(description = "繁体中文（中国香港）")
    private String zhHk;

    @Schema(description = "繁体中文（中国台湾）")
    private String zhTw;

    @Schema(description = "英文")
    private String en;

    @Schema(description = "日本语")
    private String ja;

}