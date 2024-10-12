package cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工单子类费用设置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CategoryFeeSetPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "9721")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "工单大类id", example = "866")
    private Long categoryId;

    @Schema(description = "工单子类id", example = "17139")
    private Long subcatId;

    @Schema(description = "费用设置")
    private String feeSet;

    @Schema(description = "耗材设置")
    private String stuffSet;

    @Schema(description = "服务项设置")
    private String serviceSet;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}