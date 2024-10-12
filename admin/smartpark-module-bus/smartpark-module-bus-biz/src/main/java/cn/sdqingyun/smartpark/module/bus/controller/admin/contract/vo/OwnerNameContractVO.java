package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/8/28 10:35
 */
@Data
public class OwnerNameContractVO {

    @Schema(description = "合同id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29323")
    private Long contractId;

    @Schema(description = "合同编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "29323")
    private String contractNumber;

    @Schema(description = "选中楼宇", requiredMode = Schema.RequiredMode.REQUIRED, example = "29323")
    private String checkedBuild;

    @Schema(description = "付款方id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29323")
    private Long ownerId;

    @Schema(description = "付款方", requiredMode = Schema.RequiredMode.REQUIRED, example = "29323")
    private String ownerName;
}
