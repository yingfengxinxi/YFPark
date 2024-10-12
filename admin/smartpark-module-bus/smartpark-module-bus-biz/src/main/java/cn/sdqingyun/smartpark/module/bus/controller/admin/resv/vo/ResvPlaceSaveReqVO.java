package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 预约场地新增/修改 Request VO")
@Data
public class ResvPlaceSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13103")
    private Long id;

    @Schema(description = "机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "应用标识不能为空")
    private String appSign;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10520")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27324")
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    @Schema(description = "场地名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "场地名称不能为空")
    private String name;

    @Schema(description = "场地图片")
    private String images;

    @Schema(description = "场地地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "场地地址不能为空")
    private String address;

    @Schema(description = "地址的冗余字段 用于前端回显")
    private String addressRest;

    @Schema(description = "场地纬度")
    private String latitude;

    @Schema(description = "场地经度")
    private String longitude;

    @Schema(description = "场地设施")
    private String facilityArr;

    @Schema(description = "收费规则ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30728")
    @NotNull(message = "收费规则ID不能为空")
    private Long billRuleId;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @Schema(description = "场地预约成功、通知的员工id")
    private String notifier;

    @Schema(description = "通知人json回显")
    private String notifierData;

    @Schema(description = "详细介绍", example = "随便")
    private String description;

    @Schema(description = "状态: 1为开启 0为关闭", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态: 1为开启 0为关闭不能为空")
    private Integer status;

}