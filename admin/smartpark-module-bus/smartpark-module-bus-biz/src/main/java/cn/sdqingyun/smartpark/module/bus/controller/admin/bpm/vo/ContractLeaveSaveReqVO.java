package cn.sdqingyun.smartpark.module.bus.controller.admin.bpm.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 合同审批新增/修改 Request VO")
@Data
public class ContractLeaveSaveReqVO  extends TenantBaseDO {

    @Schema(description = "合同审批单主键", example = "1813")
    private Long id;

    @Schema(description = "申请人的用户编号", example = "23063")
    private Long userId;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "审批结果 0未提交，10：审批中，20：审核通过，30：审核不通过, 40:已取消")
    private Integer status;

    @Schema(description = "合同id", example = "20791")
    private String contractId;

    @Schema(description = "合同编号")
    private String contractNumber;

    @Schema(description = "流程实例的编号", example = "7931")
    private String processInstanceId;

    @Schema(description = "审批流程标识", example = "crm-contract-audit")
    private String processDefinitionKey;

    @Schema(description = "审批状态 0 查询当前审批 1查询历史审批", example = "0")
    private Integer processSelectStatus;
}