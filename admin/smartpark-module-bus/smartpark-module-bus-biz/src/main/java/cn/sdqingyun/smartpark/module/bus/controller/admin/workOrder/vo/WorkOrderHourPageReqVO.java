package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工单工时配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkOrderHourPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "351")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "工时名称", example = "李四")
    private String name;

    @Schema(description = "工时费用")
    private BigDecimal hourFee;

    @Schema(description = "工时计算规则;1=保留两位小数;2=去除小数部分;3=四舍五入去除小数;")
    private String computeRule;

    @Schema(description = "部门id", example = "4416")
    private Long departmentId;

    @Schema(description = "岗位信息json", example = "10182")
    private Long stationId;

    @Schema(description = "岗位员工信息json")
    private String postUids;

    @Schema(description = "启用状态", example = "2")
    private String status;

    @Schema(description = "是否为默认配置")
    private String isDefault;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}