package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv.*;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 预约详情")
@Data
@ExcelIgnoreUnannotated
public class ResvBookingDetilVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "27433")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20629")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应用标识")
    private String appSign;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14703")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("订单编号")
    private String orderNo;

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25383")
    @ExcelProperty("分类ID")
    private Long categoryId;

    @Schema(description = "场地ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19259")
    @ExcelProperty("场地ID")
    private Long placeId;

    @Schema(description = "场地可容纳人数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("场地可容纳人数")
    private Integer capacity;

    @Schema(description = "预约日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("预约日期")
    private LocalDateTime date;

    @Schema(description = "预约时间段 (多个)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("预约时间段 (多个)")
    private String timeSlots;

    @Schema(description = "开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("结束时间")
    private LocalDateTime endTime;

    @Schema(description = "预约的是星期几", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("预约的是星期几")
    private String weekText;

    @Schema(description = "提醒时间")
    @ExcelProperty("提醒时间")
    private LocalDateTime remindTime;

    @Schema(description = "是否已经提醒过 1是 0否", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否已经提醒过 1是 0否")
    private Integer reminded;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remarks;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10247")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "预约人姓名", example = "王五")
    @ExcelProperty("预约人姓名")
    private String userName;

    @Schema(description = "用户手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("用户手机号")
    private String userMobile;

    @Schema(description = "租客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20786")
    @ExcelProperty("租客ID")
    private Long ownerId;

    @Schema(description = "租客名称", example = "张三")
    @ExcelProperty("租客名称")
    private String ownerName;

    @Schema(description = "冗余信息 比如 分类信息 场地信息")
    @ExcelProperty("冗余信息 比如 分类信息 场地信息")
    private String rest;

    @Schema(description = "状态 0待支付 1已支付 2已核销 3已过期", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态 0待支付 1已支付 2已核销 3已过期")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "订单总额")
    private BigDecimal orderTotal;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "场地名称")
    private String placeName;

    private ResvPlaceDO resvPlace;
    private ResvPlaceCategoryDO resvPlaceCategory;
    private ResvBookingOrderDO resvBookingOrder;
    private ResvBookingVerificationDO resvBookingVerification;
    private ResvPayOrderDO resvPayOrder;
}