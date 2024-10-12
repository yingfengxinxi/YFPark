package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产借出新增/修改 Request VO")
@Data
public class PropertyLendoutSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号")
    private Long id;

    @Schema(description = "机构id")
    private Long orgId;

    @Schema(description = "项目id", example = "18099")
    private Long villageId;

    @Schema(description = "楼宇id", example = "25285")
    private Long buildId;

    @Schema(description = "房间id", example = "30850")
    private Long roomId;

    @Schema(description = "资产id集合json")
    private String propertyIds;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "数据类型;1=借出;2=归还", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "数据类型不能为空")
    private Short type;

    @Schema(description = "单据状态", example = "2")
    private Integer status;

    @Schema(description = "借用人uid")
    private Long lendUid;

    @Schema(description = "借用部门id", example = "2325")
    private Long departmentId;

    @Schema(description = "借出时间")
    private LocalDateTime lendTime;

    @Schema(description = "预计归还时间")
    private LocalDateTime expectRevertTime;

    @Schema(description = "处理人")
    private Long operateUid;

    @Schema(description = "处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "借出备注", example = "你猜")
    private String remark;

    @Schema(description = "操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid")
    private Long muserUid;

}