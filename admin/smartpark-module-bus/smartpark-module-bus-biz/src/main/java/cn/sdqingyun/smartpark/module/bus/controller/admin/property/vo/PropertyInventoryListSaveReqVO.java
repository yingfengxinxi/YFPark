package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 资产盘点清单新增/修改 Request VO")
@Data
public class PropertyInventoryListSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26642")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30094")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "盘点清单名称", example = "芋艿")
    private String inventoryName;

    @Schema(description = "盘点清单状态 0 盘点中 1盘点审批中 2已完结", example = "2")
    private Integer inventoryStatus;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "盘点人", example = "27867")
    private String inventoryUid;

    @Schema(description = "资产限制字段")
    private String restrictField;

    @Schema(description = "盘点范围（使用部门）")
    private String departmentIds;

    @Schema(description = "盘点范围（资产分类）")
    private String typeIds;

    @Schema(description = "盘点范围（资产位置）")
    private String positionIds;

    @Schema(description = "资产状态", example = "2")
    private String propertyStatus;

    @Schema(description = "主付状态", example = "2")
    private Integer initiativePayStatus;

    @Schema(description = "管理员id", example = "29507")
    private String adminId;

    @Schema(description = "所属公司")
    private Long company;

    @Schema(description = "购置方式", example = "1")
    private String purchaseType;

    @Schema(description = "仅扫码盘点的分类", example = "1")
    private String codeInventoryType;

    @Schema(description = "不可批量盘点的分类", example = "2")
    private String notbreachInventoryType;

    @Schema(description = "ntake_pic:仅上传拍照 ntask_pic_after:在原有基础在补充", example = "1")
    private String uploadType;

    @Schema(description = "是否批量分配 1是0否")
    private Integer isBatch;

    @Schema(description = "单个分配 分配范围")
    private String distributionRange;

    @Schema(description = "额外数据、部门和分类的回显")
    private String exterData;

}