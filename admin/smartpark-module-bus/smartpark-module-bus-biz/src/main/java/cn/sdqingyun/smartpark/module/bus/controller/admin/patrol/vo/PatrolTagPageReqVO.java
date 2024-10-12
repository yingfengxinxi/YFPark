package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 巡检标签模板分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PatrolTagPageReqVO extends PageParam {

    @Schema(description = "机构Id", example = "292")
    private Long orgId;

    @Schema(description = "应用标识EQUIPMENT_INSPECTION=巡检;SECURITY_INSPECTION=安防", example = "292")
    private String application;
    @Schema(description = "模板id(数据字典PATROL_TAG_TEMPLATE)", example = "9132")
    private Long templateId;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "模板名称", example = "赵六")
    private String name;

    @Schema(description = "字段上限数量")
    private Integer fieldLimit;

    @Schema(description = "有无logo;0=无;1=有")
    private String hasLogo;

    @Schema(description = "应用数据json")
    private String applyJson;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}