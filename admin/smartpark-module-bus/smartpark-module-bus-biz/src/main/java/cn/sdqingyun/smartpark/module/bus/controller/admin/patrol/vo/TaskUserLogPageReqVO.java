package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 应用巡检任务变更执行人日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskUserLogPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "1167")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "任务id", example = "26464")
    private Long taskId;

    @Schema(description = "旧执勤人id", example = "13253")
    private Long oldUid;

    @Schema(description = "旧执勤人姓名", example = "王五")
    private String oldName;

    @Schema(description = "新执勤人id", example = "26820")
    private Long newUid;

    @Schema(description = "新执勤人姓名", example = "张三")
    private String newName;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}