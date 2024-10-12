package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产管理新增/修改 Request VO")
@Data
public class PropertySaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20507")
    private Long id;

    @Schema(description = "机构ID", example = "24074")
    private Long orgId;

    @Schema(description = "资产编码")
    private String propertyNumber;

    @Schema(description = "资产标签链接")
    private String labelLink;

    @Schema(description = "资产分类", example = "1")
    private Long type;

    @Schema(description = "资产名称", example = "李四")
    private String name;

    @Schema(description = "资产状态")
    private Integer status;

    @Schema(description = "品牌", requiredMode = Schema.RequiredMode.REQUIRED, example = "华为")
    @NotNull(message = "品牌名称不能为空")
    private String brand;

    @Schema(description = "型号", example = "赵六")
    private String modelName;

    @Schema(description = "设备序列号")
    private String deviceCode;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "管理员id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18658")
    @NotNull(message = "管理员id不能为空")
    private Long adminId;

    @Schema(description = "管理员姓名")
    private String adminName;

    @Schema(description = "目标房源项目id", example = "3602")
    private Long villageId;

    @Schema(description = "目标房源楼宇id", example = "16252")
    private Long buildId;

    @Schema(description = "目标房源房间id", example = "28140")
    private String roomId;

    @Schema(description = "绑定的目标房源json")
    private String buildBind;

    @Schema(description = "所在位置配置信息id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "所在位置不能为空")
    private Long positionId;

    @Schema(description = "所在位置名称", example = "第一大厅")
    private String positionName;

    @Schema(description = "购置时间")
    private LocalDateTime purchaseTime;

    @Schema(description = "购置方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "字典表：采购/租赁")
   // @NotNull(message = "购置方式不能为空")
    private Integer purchaseType;

    @Schema(description = "购置金额(含税)")
    private BigDecimal purchaseAmount;

    @Schema(description = "入库时间")
    private LocalDateTime stockTime;

    @Schema(description = "预计使用期限(月)")
    private Long expectMonths;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "图片hash值")
    private String imageHash;

    @Schema(description = "资产图片url", example = "https://********")
    private String imageUrl;

    @Schema(description = "使用人")
    private Long userId;

    @Schema(description = "使用部门id", example = "5829")
    private Long departmentId;

    @Schema(description = "领用日期")
    private LocalDateTime receiveTime;

    @Schema(description = "保养到期时间")
    private LocalDateTime maintainTime;

    @Schema(description = "保养说明")
    private String maintainInfo;

    @Schema(description = "预计折旧期限(月)")
    private Integer depreciationMonths;

    @Schema(description = "操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid")
    private Long muserUid;

    @Schema(description = "所属资产的知识库")
    private String knowledgeBase;

}