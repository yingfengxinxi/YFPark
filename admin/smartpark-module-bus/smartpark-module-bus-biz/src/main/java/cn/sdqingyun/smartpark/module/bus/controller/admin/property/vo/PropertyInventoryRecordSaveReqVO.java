package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产盘点记录新增/修改 Request VO")
@Data
public class PropertyInventoryRecordSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23915")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7485")
    @NotNull(message = "机构id不能为空")
    private Long orgId;

    @Schema(description = "盘点清单id", example = "2469")
    private Long inventoryId;

    @Schema(description = "资产id", example = "576")
    private Long propertyId;

    @Schema(description = "资产分类id", example = "17141")
    private Long typeId;

    @Schema(description = "0未盘 1已盘 2异常 3盘盈 4盘亏 5已结束", example = "2")
    private Integer status;

    @Schema(description = "资产状态", example = "2")
    private Integer propertyStatus;

    @Schema(description = "是否在盘点范围之内")
    private Integer isRange;

    @Schema(description = "是否修改资产信息 0否 1是", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否修改资产信息 0否 1是不能为空")
    private Integer isUpdate;

    @Schema(description = "盘点备注", example = "你说的对")
    private String remark;

    @Schema(description = "盘点图片")
    private String image;

    @Schema(description = "盘点标签")
    private String tag;

    @Schema(description = "盘点时间")
    private LocalDateTime inventoryTime;

    @Schema(description = "盘点人", example = "19108")
    private String inventoryUid;

    @Schema(description = "当前位置", example = "11648")
    private Long positionId;

    @Schema(description = "处理人", example = "5098")
    private Long handleUid;

    @Schema(description = "资产所属部门id", example = "26725")
    private Long departmentId;

    @Schema(description = "资产盘点信息")
    private String propertyInfo;

    @Schema(description = "资产名称", example = "李四")
    private String name;

    @Schema(description = "资产编码")
    private String propertyNumber;

}