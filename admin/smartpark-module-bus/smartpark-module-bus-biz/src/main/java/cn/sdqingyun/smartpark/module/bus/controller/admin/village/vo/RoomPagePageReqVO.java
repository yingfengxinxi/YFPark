package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 招商中心装修页分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RoomPagePageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "32210")
    private Long orgId;

    @Schema(description = "内容信息json")
    private String content;

    @Schema(description = "最近修改的时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] lastTime;

    @Schema(description = "来源 0 pc端 1 移动端")
    private Integer isMobile;

    @Schema(description = "启用状态;0=关闭;1=开启", example = "2")
    private Integer status;

    @Schema(description = "创建人uid", example = "25171")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "22910")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}