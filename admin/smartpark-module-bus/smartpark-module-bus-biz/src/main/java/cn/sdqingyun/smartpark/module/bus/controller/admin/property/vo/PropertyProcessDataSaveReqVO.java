package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 业务流程单据关联资产新增/修改 Request VO")
@Data
public class PropertyProcessDataSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号")
    private Long id;

    @Schema(description = "机构ID", example = "11914")
    private Long orgId;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "流程单据编号")
    private String processNumber;

    @Schema(description = "资产id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12453")
    @NotNull(message = "资产id不能为空")
    private Long propertyId;

    @Schema(description = "资产编号")
    private String propertyNumber;

    @Schema(description = "资产分类", example = "1")
    private Integer type;

    @Schema(description = "资产名称", example = "张三")
    private String name;

    @Schema(description = "资产状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "资产状态不能为空")
    private Integer status;

    @Schema(description = "所在位置配置信息id", example = "24517")
    private Long positionId;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "型号", example = "赵六")
    private String modelName;

    @Schema(description = "目标房源")
    private String buildBind;

    @Schema(description = "资产保养上传的文件")
    private String maintainFile;

    @Schema(description = "资产保养上传的金额", example = "17149")
    private BigDecimal maintainPrice;

    @Schema(description = "操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid")
    private Long muserUid;

}