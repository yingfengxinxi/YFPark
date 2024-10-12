package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 房间对应自定义价格分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyRoomPricePageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "28830")
    private Long orgId;

    @Schema(description = "房间ID", example = "4417")
    private Long roomId;

    @Schema(description = "关联自定义价格标准ID", example = "22778")
    private Long energyPriceId;

    @Schema(description = "设备表种类", example = "1")
    private String type;

    @Schema(description = "状态，1启动，0审核中，4禁用", example = "1")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}