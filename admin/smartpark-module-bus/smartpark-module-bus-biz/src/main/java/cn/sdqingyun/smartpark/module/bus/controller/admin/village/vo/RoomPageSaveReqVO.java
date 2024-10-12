package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 招商中心装修页新增/修改 Request VO")
@Data
public class RoomPageSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9729")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "32210")
    @NotNull(message = "机构ID不能为空")
    private Long orgId;

    @Schema(description = "内容信息json")
    private String content;

    @Schema(description = "最近修改的时间")
    private LocalDateTime lastTime;

    @Schema(description = "来源 0 pc端 1 移动端", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "来源 0 pc端 1 移动端不能为空")
    private Integer isMobile;

    @Schema(description = "启用状态;0=关闭;1=开启", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "启用状态;0=关闭;1=开启不能为空")
    private Integer status;

    @Schema(description = "创建人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "25171")
    @NotNull(message = "创建人uid不能为空")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "22910")
    @NotNull(message = "修改人uid不能为空")
    private Long muserUid;

}