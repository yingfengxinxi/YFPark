package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产盘点操作日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyInventoryLogPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "26796")
    private Long orgId;

    @Schema(description = "操作人", example = "1866")
    private Long operatorId;

    @Schema(description = "操作人姓名", example = "赵六")
    private String operatorName;

    @Schema(description = "盘点清单id", example = "5646")
    private Long inventoryId;

    @Schema(description = "盘点记录id", example = "26664")
    private Long recordId;

    @Schema(description = "操作类型", example = "2")
    private String type;

    @Schema(description = "操作内容")
    private String content;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}