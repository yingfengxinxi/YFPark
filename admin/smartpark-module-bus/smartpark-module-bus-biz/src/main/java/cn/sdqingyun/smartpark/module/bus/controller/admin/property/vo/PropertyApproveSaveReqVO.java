package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产单据审批记录新增/修改 Request VO")
@Data
public class PropertyApproveSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17873")
    private Long id;

    @Schema(description = "机构id", example = "7510")
    private Long orgId;

    @Schema(description = "项目id", example = "14153")
    private Long villageId;

    @Schema(description = "楼宇id", example = "2597")
    private Long buildId;

    @Schema(description = "房间id", example = "10603")
    private String roomId;

    @Schema(description = "审批单据编号")
    private String approveNumber;

    @Schema(description = "关联单据编号")
    private String relationNumber;

    @Schema(description = "关联单据类型", example = "2")
    private String relationType;

    @Schema(description = "1=待审批(审批中);2=审批通过(已完结);3=审批不通过;4=已撤回(关闭申请);", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "1=待审批(审批中);2=审批通过(已完结);3=审批不通过;4=已撤回(关闭申请);不能为空")
    private Integer status;

    @Schema(description = "审批业务类型", example = "1")
    private String processType;

    @Schema(description = "审批内容")
    private String content;

    @Schema(description = "发起时间")
    private LocalDateTime launchTime;

    @Schema(description = "审批完结时间")
    private LocalDateTime approveOvertime;

    @Schema(description = "预计归还时间")
    private LocalDateTime expectRevertTime;

    @Schema(description = "原因备注", example = "你说的对")
    private String remark;

    @Schema(description = "申请部门id", example = "21028")
    private Long departmentId;

    @Schema(description = "交接后使用人uid", example = "24634")
    private Long handoverUid;

    @Schema(description = "交接后使用部门id", example = "26866")
    private Long handoverDepartmentId;

    @Schema(description = "审批应用流程id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12932")
    @NotNull(message = "审批应用流程id不能为空")
    private Long approvalId;

    @Schema(description = "提交人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "16222")
    @NotNull(message = "提交人uid不能为空")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "9663")
    @NotNull(message = "修改人uid不能为空")
    private Long muserUid;

    @Schema(description = "结束盘点之后的操作1已盘资产更新内容、2盘盈资产自动入库、3未盘资产自动盘亏")
    private String endInventoryAfter;

}