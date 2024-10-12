package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工单规则设置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkOrderRulePageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "1517")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "规则名称", example = "李四")
    private String name;

    @Schema(description = "抢单数上限;/个")
    private Integer snatchLimit;

    @Schema(description = "抢单前置时长;/分钟")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] preposeTime;

    @Schema(description = "抢单限制时长;单位/分钟")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] robTime;

    @Schema(description = "可退款时长;/分钟")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] refundTime;

    @Schema(description = "取消订单时长;/分钟")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] cancelTime;

    @Schema(description = "重新打开时长;/分钟")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] restartTime;

    @Schema(description = "绑定的楼宇信息json")
    private String buildBind;

    @Schema(description = "是否为默认配置")
    private String isDefault;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}