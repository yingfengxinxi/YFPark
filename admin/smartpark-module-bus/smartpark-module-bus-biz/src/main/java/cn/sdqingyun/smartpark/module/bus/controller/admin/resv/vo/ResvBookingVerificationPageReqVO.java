package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 预约核销分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResvBookingVerificationPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "15107")
    private Long orgId;

    @Schema(description = "应用标识")
    private String appSign;

    @Schema(description = "项目ID", example = "31308")
    private Long villageId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "预约ID", example = "21438")
    private Long bookingId;

    @Schema(description = "核销码")
    private String code;

    @Schema(description = "核销状态 0未核销 1已核销", example = "2")
    private Integer status;

    @Schema(description = "用户ID", example = "20842")
    private Long userId;

    @Schema(description = "处理人")
    private Long handler;

    @Schema(description = "处理人名称", example = "赵六")
    private String handlerName;

    @Schema(description = "处理时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] handleTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}