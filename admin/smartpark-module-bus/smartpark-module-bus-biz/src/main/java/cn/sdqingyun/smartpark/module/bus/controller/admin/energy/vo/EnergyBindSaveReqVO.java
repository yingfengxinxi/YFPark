package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 自定义关联分总新增/修改 Request VO")
@Data
public class EnergyBindSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "29752")
    private Long id;

    @Schema(description = "类型 1分表 2总表", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "类型 1分表 2总表不能为空")
    private String type;

    @Schema(description = "设备表种类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "设备表种类不能为空")
    private Long meterType;

    @Schema(description = "用途类型 1分表 2总表 3公摊表", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "用途类型 1分表 2总表 3公摊表不能为空")
    private String purposeType;

    @Schema(description = "机构ID", example = "4286")
    private Long orgId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10718")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "楼宇ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18372")
    @NotNull(message = "楼宇ID不能为空")
    private Long buildId;

    @Schema(description = "自身自定义表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9240")
    @NotNull(message = "自身自定义表ID不能为空")
    private Long energyId;

    @Schema(description = "关联自定义表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30103")
    @NotNull(message = "关联自定义表ID不能为空")
    private Long parentEnergyId;

    @Schema(description = "表名称", example = "芋艿")
    private String name;

    @Schema(description = "绑定表数据集合，json存储")
    private String binds;

    @Schema(description = "扩展字段 类型为1-分表-分摊比例,类型为2-总表-电表用量")
    private String extendContent;

    @Schema(description = "最近抄录时间")
    private String latelyMeterTime;

    @Schema(description = "状态，1启动，0审核中，4禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "状态，1启动，0审核中，4禁用不能为空")
    private String status;

}