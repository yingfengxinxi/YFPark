package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import cn.hutool.core.date.DateTime;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Schema(description = "管理后台 - 预约新增/修改 Request VO")
@Data
public class ResvBookingSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号")
    private Long id;

    @Schema(description = "机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "应用标识不能为空")
    private String appSign;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14703")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25383")
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    @Schema(description = "场地ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19259")
    @NotNull(message = "场地ID不能为空")
    private Long placeId;

    @Schema(description = "场地可容纳人数")
    private Integer capacity;

    @Schema(description = "预约日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "预约日期不能为空")
    private Date date;

    @Schema(description = "预约时间段 (多个) [{\"start_time\":\"19:00\",\"end_time\":\"20:00\"}]", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "预约时间段 (多个)不能为空 [{\"start_time\":\"19:00\",\"end_time\":\"20:00\"}]")
    private String timeSlots;

    @Schema(description = "开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "开始时间不能为空")
    private DateTime startTime;

    @Schema(description = "结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "结束时间不能为空")
    private DateTime endTime;

    @Schema(description = "预约的是星期几", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "预约的是星期几不能为空")
    private String weekText;

    @Schema(description = "提醒时间")
    private DateTime remindTime;

    @Schema(description = "是否已经提醒过 1是 0否", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否已经提醒过 1是 0否不能为空")
    private Integer reminded;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "租客姓名", example = "王五")
    private String userName;

    @Schema(description = "用户手机号")
    private String userMobile;

    @Schema(description = "租客ID")
    private Long ownerId;

    @Schema(description = "租客名称")
    private String ownerName;

    @Schema(description = "冗余信息 比如 分类信息 场地信息")
    private String rest;

    @Schema(description = "状态 0待支付 1已支付 2已核销 3已过期", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态 0待支付 1已支付 2已核销 3已过期不能为空")
    private Integer status;

    @Schema(description = "订单总额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单总额不能为空")
    private BigDecimal orderTotal;
}