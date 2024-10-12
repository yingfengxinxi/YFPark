package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 住户的IC卡分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserIcCardPageReqVO extends PageParam {

    @Schema(description = "village_user用户表ID，可能为空", example = "23162")
    private Long userId;

    @Schema(description = "归属租客ID", example = "29114")
    private Long ownerId;

    @Schema(description = "IC卡号（128位加密）")
    private String icCard;

    @Schema(description = "数据状态(1使用，0禁用)", example = "2")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}