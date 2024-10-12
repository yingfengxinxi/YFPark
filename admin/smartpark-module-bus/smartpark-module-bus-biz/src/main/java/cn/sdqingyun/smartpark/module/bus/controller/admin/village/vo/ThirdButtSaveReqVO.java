package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 第三方数据对接（目前用于智慧社区系统，全功能版）新增/修改 Request VO")
@Data
public class ThirdButtSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28608")
    private Long id;

    @Schema(description = "第三方业务类别", example = "2")
    private String businessType;

    @Schema(description = "第三方业务ID", example = "424")
    private String businessId;

    @Schema(description = "自有数据ID", example = "17673")
    private String selfId;

    @Schema(description = "数据原有返回格式")
    private String originalData;

}