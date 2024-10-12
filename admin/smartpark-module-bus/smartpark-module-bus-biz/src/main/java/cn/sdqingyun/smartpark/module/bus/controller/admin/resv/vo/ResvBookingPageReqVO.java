package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 预约分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResvBookingPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "20629")
    private Long orgId;

    @Schema(description = "应用标识")
    private String appSign;

    @Schema(description = "项目ID", example = "14703")
    private Long villageId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "分类ID", example = "25383")
    private Long categoryId;

    @Schema(description = "场地ID", example = "19259")
    private Long placeId;

    @Schema(description = "场地可容纳人数")
    private Integer capacity;

    @Schema(description = "预约日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] date;

    @Schema(description = "预约时间段 (多个)")
    private String timeSlots;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

    @Schema(description = "预约的是星期几")
    private String weekText;

    @Schema(description = "提醒时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] remindTime;

    @Schema(description = "是否已经提醒过 1是 0否")
    private Integer reminded;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "用户ID", example = "10247")
    private Long userId;

    @Schema(description = "租客姓名", example = "王五")
    private String userName;

    @Schema(description = "用户手机号")
    private String userMobile;

    @Schema(description = "租客ID", example = "20786")
    private Long ownerId;

    @Schema(description = "租客名称", example = "张三")
    private String ownerName;

    @Schema(description = "冗余信息 比如 分类信息 场地信息")
    private String rest;

    @Schema(description = "状态 0待支付 1已支付 2已核销 3已过期", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}