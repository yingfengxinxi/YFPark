package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目类型分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VillageTypePageReqVO extends PageParam {

    @Schema(description = "类型名称", example = "芋艿")
    private String name;

    @Schema(description = "类型别名")
    private String alias;

    @Schema(description = "类型背景图片")
    private String bgImg;

    @Schema(description = "类型icon图片")
    private String iconImg;

    @Schema(description = "服务上报的菜单及应用")
    private String menu;

    @Schema(description = "受限制过滤的菜单及应用")
    private String filterMenu;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}