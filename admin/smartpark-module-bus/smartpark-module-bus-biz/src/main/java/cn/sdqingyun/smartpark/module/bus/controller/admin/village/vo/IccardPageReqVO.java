package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目IC卡表（可能会绑定人员，因不同设备需要而定）分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IccardPageReqVO extends PageParam {

    @Schema(description = "卡ID（128位加密）", example = "10600")
    private String cardId;

    @Schema(description = "房间ID", example = "12701")
    private Long roomId;

    @Schema(description = "楼层ID", example = "14109")
    private Long layerId;

    @Schema(description = "单元ID", example = "1251")
    private Long unitId;

    @Schema(description = "楼栋ID", example = "2632")
    private Long buildId;

    @Schema(description = "分区ID", example = "20095")
    private Long zoneId;

    @Schema(description = "项目ID", example = "22149")
    private Long villageId;

    @Schema(description = "用户ID（可能为空）", example = "27406")
    private Long roomUserId;

    @Schema(description = "数据状态(1使用，0禁用)", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}