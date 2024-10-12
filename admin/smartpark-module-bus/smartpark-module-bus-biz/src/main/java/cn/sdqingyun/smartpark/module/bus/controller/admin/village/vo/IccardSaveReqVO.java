package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 项目IC卡表（可能会绑定人员，因不同设备需要而定）新增/修改 Request VO")
@Data
public class IccardSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31252")
    private Long id;

    @Schema(description = "卡ID（128位加密）", example = "10600")
    private String cardId;

    @Schema(description = "房间ID", example = "12701")
    private Long roomId;

    @Schema(description = "楼层ID", example = "14109")
    private Long layerId;

    @Schema(description = "单元ID", example = "1251")
    private Long unitId;

    @Schema(description = "楼栋ID", example = "2632")
    private Long buildId;

    @Schema(description = "分区ID", example = "20095")
    private Long zoneId;

    @Schema(description = "项目ID", example = "22149")
    private Long villageId;

    @Schema(description = "用户ID（可能为空）", example = "27406")
    private Long roomUserId;

    @Schema(description = "数据状态(1使用，0禁用)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "数据状态(1使用，0禁用)不能为空")
    private Integer status;

}