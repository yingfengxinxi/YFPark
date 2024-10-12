package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 耗材业务领用新增/修改 Request VO")
@Data
public class PropertyStuffReceiveSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9167")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28156")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    private String processNumber;

    @Schema(description = "申领仓库id", example = "23869")
    private Long depositoryId;

    @Schema(description = "申请部门id", example = "510")
    private Long departmentId;

    @Schema(description = "部门名称", example = "李四")
    private String departmentName;

    @Schema(description = "申请总数量")
    private BigDecimal totalNum;

    @Schema(description = "关联单据类型", example = "2")
    private String relationType;

    @Schema(description = "关联单据编号;派发单据,支持多个")
    private String relationNumber;

    @Schema(description = "已派发的总数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "已派发的总数量不能为空")
    private BigDecimal handoutNum;

    @Schema(description = "申请时间")
    private LocalDateTime applyTime;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结不能为空")
    private Short status;

    @Schema(description = "申请原因", example = "随便")
    private String remark;

    @Schema(description = "申请人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "6879")
    @NotNull(message = "申请人uid不能为空")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "13729")
    @NotNull(message = "修改人uid不能为空")
    private Long muserUid;

}