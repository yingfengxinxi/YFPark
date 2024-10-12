package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 租客在租/绑定房间分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RoomRentPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "4965")
    private Long orgId;

    @Schema(description = "项目id", example = "3743")
    private Long villageId;

    @Schema(description = "楼宇id", example = "28546")
    private Long buildId;

    @Schema(description = "楼层id", example = "1061")
    private Long layerId;

    @Schema(description = "房间id", example = "7631")
    private Long roomId;

    @Schema(description = "租客id", example = "23794")
    private Long ownerId;

    @Schema(description = "合同id", example = "7074")
    private Long contractId;

    @Schema(description = "租赁到期时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] expireTime;

    @Schema(description = "来源标识;1=合同;2=物业绑定")
    private Integer source;

    @Schema(description = "房间状态;0=在租;", example = "1")
    private Integer status;

    @Schema(description = "其他")
    private String extra;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}