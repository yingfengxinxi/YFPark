package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产派发/退库新增/修改 Request VO")
@Data
public class PropertyHandoutSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4478")
    private Long id;

    @Schema(description = "机构id")
    private Long orgId;

    @Schema(description = "项目id", example = "30963")
    private Long villageId;

    @Schema(description = "楼宇id", example = "25748")
    private Long buildId;

    @Schema(description = "房间id", example = "4303")
    private Long roomId;

    @Schema(description = "资产id集合json", example = "1,2,3")
    @NotNull(message = "资产未选择，请选择后提交")
    private String propertyIds;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "数据类型;1=派发;2=退库", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "数据类型不能为空")
    private Short type;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "单据状态", example = "1")
    private Integer status;

    @Schema(description = "所选部门id", example = "13761")
    private Long departmentId;

    @Schema(description = "领用人", example = "29669")
    private Long receiveUid;

    @Schema(description = "派发日期")
    private LocalDateTime handoutTime;

    @Schema(description = "退库日期")
    private LocalDateTime returnTime;

    @Schema(description = "处理人", requiredMode = Schema.RequiredMode.REQUIRED, example = "31773")
    @NotNull(message = "处理人不能为空")
    private Long operateUid;

    @Schema(description = "处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "处理备注", example = "你说的对")
    private String remark;

    @Schema(description = "操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid")
    private Long muserUid;

}