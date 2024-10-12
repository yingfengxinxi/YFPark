package cn.sdqingyun.smartpark.module.bus.controller.admin.user.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 机构用户自定义操作配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SpercialSettingPageReqVO extends PageParam {

    @Schema(description = "用户ID", example = "13108")
    private Long uid;

    @Schema(description = "机构ID", example = "243")
    private Long orgId;

    @Schema(description = "自定义类型", example = "2")
    private String type;

    @Schema(description = "用户自定义配置内容json，JSON串({buildId: [1011, 1021, 1031], villageId: [101, 102, 103]})")
    private String content;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}