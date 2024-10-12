package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 水电表开关记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HydropowerOperateRecordPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "13278")
    private Long orgId;

    @Schema(description = "水电表id", example = "21409")
    private Long energyId;

    @Schema(description = "操作人、自动处理为空", example = "1535")
    private Long operateUid;

    @Schema(description = "操作状态、0关闸、1开闸 2、重置", example = "2")
    private String status;

    @Schema(description = "操作原因", example = "不香")
    private String reason;

    @Schema(description = "设备类型", example = "1")
    private String deviceType;

    @Schema(description = "如果是以电控水、对应的水表id", example = "32701")
    private Long deviceWaterId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}