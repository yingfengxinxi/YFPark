package cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 车辆操作日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ParkCarsOperatorLogPageReqVO extends PageParam {

    @Schema(description = "0添加、1修改、2删除、3缴费，关联订单、4导入", example = "1")
    private String type;

    @Schema(description = "车辆id", example = "17587")
    private Long carsId;

    @Schema(description = "订单id", example = "22830")
    private String orderId;

    @Schema(description = "修改前")
    private String before;

    @Schema(description = "修改后")
    private String after;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}