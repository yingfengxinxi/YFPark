package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 耗材业务调拨新增/修改 Request VO")
@Data
public class PropertyStuffTransferSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "16719")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9169")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    private String processNumber;

    @Schema(description = "调出管理员", requiredMode = Schema.RequiredMode.REQUIRED, example = "11997")
    @NotNull(message = "调出管理员不能为空")
    private Long outAdminUid;

    @Schema(description = "调入管理员", requiredMode = Schema.RequiredMode.REQUIRED, example = "32348")
    @NotNull(message = "调入管理员不能为空")
    private Long inAdminUid;

    @Schema(description = "调出仓库", requiredMode = Schema.RequiredMode.REQUIRED, example = "29406")
    @NotNull(message = "调出仓库不能为空")
    private Long outDepositoryId;

    @Schema(description = "调入仓库", requiredMode = Schema.RequiredMode.REQUIRED, example = "23490")
    @NotNull(message = "调入仓库不能为空")
    private Long inDepositoryId;

    @Schema(description = "合计数量")
    private BigDecimal totalNum;

    @Schema(description = "合计金额", example = "31682")
    private BigDecimal totalPrice;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结不能为空")
    private Short status;

    @Schema(description = "调拨备注", example = "你猜")
    private String remark;

    @Schema(description = "处理人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "17329")
    @NotNull(message = "处理人uid不能为空")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "14488")
    @NotNull(message = "修改人uid不能为空")
    private Long muserUid;

}