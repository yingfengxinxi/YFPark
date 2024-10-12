package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 预约应用分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResvApplicationPageReqVO extends PageParam {

    @Schema(description = "应用名称", example = "李四")
    private String name;

    @Schema(description = "应用简称", example = "芋艿")
    private String shortName;

    @Schema(description = "应用标志")
    private String sign;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "取消订单规则")
    private String cancelOrderRule;

    @Schema(description = "是否支持退款")
    private Integer refundSupported;

    @Schema(description = "退款规则")
    private String refundRule;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}