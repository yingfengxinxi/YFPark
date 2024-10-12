package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产维修新增/修改 Request VO")
@Data
public class PropertyRepairSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20214")
    private Long id;

    @Schema(description = "机构id")
    private Long orgId;

    @Schema(description = "资产id")
    private String propertyIds;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据状态", example = "1")
    private Integer status;

    @Schema(description = "报修人uid")
    private Long repairUid;

    @Schema(description = "报修部门id")
    private Long repairDepartmentId;

    @Schema(description = "报修时间")
    private LocalDateTime repairTime;

    @Schema(description = "报修原因", example = "不好")
    private String repairReason;

    @Schema(description = "处理人")
    private Long operateUid;

    @Schema(description = "预计维修金额", example = "8161")
    private String expectRepairPrice;

    @Schema(description = "处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "维修内容")
    private String repairContent;

    @Schema(description = "工单信息")
    private String workorderInfo;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid")
    private Long muserUid;

}