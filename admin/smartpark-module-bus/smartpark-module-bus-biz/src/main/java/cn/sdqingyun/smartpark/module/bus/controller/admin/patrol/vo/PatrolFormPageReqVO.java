package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 巡检表单设置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PatrolFormPageReqVO extends PageParam {

    @Schema(description = "机构Id", example = "26573")
    private Long orgId;

    @Schema(description = "应用标识EQUIPMENT_INSPECTION=巡检;SECURITY_INSPECTION=安防")
    private String application;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "表单内容")
    private String content;

    @Schema(description = "是否为默认配置0=否1=是")
    private String isDefault;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}