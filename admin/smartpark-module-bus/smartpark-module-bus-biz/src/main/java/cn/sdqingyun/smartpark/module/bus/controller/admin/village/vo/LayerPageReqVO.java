package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目楼层分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LayerPageReqVO extends PageParam {

    @Schema(description = "楼层编号")
    private Long layerNumber;

    @Schema(description = "楼层名称", example = "王五")
    private String layerName;

    @Schema(description = "单元ID", example = "31381")
    private Long unitId;

    @Schema(description = "楼栋ID", example = "5016")
    private Long buildId;

    @Schema(description = "分区ID", example = "16890")
    private Long zoneId;

    @Schema(description = "项目ID", example = "20265")
    private Long villageId;

    @Schema(description = "排序，越大越前")
    private Integer sort;

    @Schema(description = "数据状态(1使用，0隐藏)", example = "2")
    private Integer status;

    @Schema(description = "3D模型")
    private String threeDimensionalFile;

    @Schema(description = "3D模型物体ID", example = "26630")
    private String threeDimensionalId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}