package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产调拨新增/修改 Request VO")
@Data
public class PropertyTransferSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号")
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

    @Schema(description = "调出管理员uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "27333")
    @NotNull(message = "调出管理员uid不能为空")
    private Long outAdminUid;

    @Schema(description = "调入管理员uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "22111")
    @NotNull(message = "调入管理员uid不能为空")
    private Long inAdminUid;

    @Schema(description = "调入管理员姓名", example = "22111")
    @ExcelProperty("调入管理员姓名")
    private String inAdminUidName;

    @Schema(description = "调入位置id", example = "18013")
    private Long inLocationId;

    @Schema(description = "处理人", requiredMode = Schema.RequiredMode.REQUIRED, example = "22115")
    @NotNull(message = "处理人不能为空")
    private Long operateUid;

    @Schema(description = "处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "借出备注", example = "你说的对")
    private String remark;

    @Schema(description = "操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid")
    private Long muserUid;

}