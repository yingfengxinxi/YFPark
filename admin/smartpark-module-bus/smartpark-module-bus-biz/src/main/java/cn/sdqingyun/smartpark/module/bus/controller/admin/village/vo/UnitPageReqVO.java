package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目单元分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UnitPageReqVO extends PageParam {

    @Schema(description = "单元编号")
    private Integer unitNumber;

    @Schema(description = "单元名称", example = "李四")
    private String unitName;

    @Schema(description = "楼栋ID", example = "7648")
    private Long buildId;

    @Schema(description = "分区ID", example = "13430")
    private Long zoneId;

    @Schema(description = "项目ID", example = "23241")
    private Long villageId;

    @Schema(description = "排序，越大越前")
    private Integer sort;

    @Schema(description = "数据状态(1使用，0隐藏)", example = "2")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}