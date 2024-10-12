package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目类型字典分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VillageTypeDictPageReqVO extends PageParam {

    @Schema(description = "类型别名")
    private String typeAlias;

    @Schema(description = "原字")
    private String words;

    @Schema(description = "中文")
    private String zhCn;

    @Schema(description = "繁体中文（中国香港）")
    private String zhHk;

    @Schema(description = "繁体中文（中国台湾）")
    private String zhTw;

    @Schema(description = "英文")
    private String en;

    @Schema(description = "日本语")
    private String ja;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}