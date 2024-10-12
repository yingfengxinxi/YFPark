package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 自定义价格标准表-阶梯单价分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyUnitPricePageReqVO extends PageParam {

    @Schema(description = "设备表种类", example = "2")
    private String type;

    @Schema(description = "机构ID", example = "13190")
    private Long orgId;

    @Schema(description = "用量区间1")
    private String startUsageRange;

    @Schema(description = "用量区间2")
    private String endUsageRange;

    @Schema(description = "价格", example = "14560")
    private BigDecimal price;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "自定义价格标准表id", example = "16066")
    private Long energyPriceId;

}