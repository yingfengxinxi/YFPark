package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 合同操作日志新增/修改 Request VO")
@Data
public class ContractOperateLogSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7748")
    private Long id;

    @Schema(description = "基础合同,合同主表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27256")
    @NotNull(message = "基础合同,合同主表id不能为空")
    private Integer contractId;

    /**
     * 合同类型
     */
    @Schema(description = "操作类型")
    private String operateType;

    @Schema(description = "操作内容")
    private String operateContent;

}