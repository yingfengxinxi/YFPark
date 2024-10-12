package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 智能硬件配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HardWareConfigPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "16415")
    private Long orgId;

    @Schema(description = "项目id", example = "16294")
    private Long villageId;

    @Schema(description = "楼宇id", example = "1423")
    private Long buildId;

    @Schema(description = "是否自动断电 1是 0否", example = "1")
    private String electricityType;

    @Schema(description = "电费可逾期天数")
    private Integer electricityLimitDay;

    @Schema(description = "是否自动断水 1是 0否", example = "1")
    private String waterType;

    @Schema(description = "1是已电控水 2直接断水", example = "2")
    private String waterCutType;

    @Schema(description = "水费可逾期天数")
    private Integer waterLimitDay;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}