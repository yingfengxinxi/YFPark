package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 表种类管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyTypePageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "6410")
    private Long orgId;

    @Schema(description = "表种类名称", example = "张三")
    private String name;

    @Schema(description = "费用类型", example = "1")
    private String costType;

    @Schema(description = "费用类型文本")
    private String costTypeTxt;

    @Schema(description = "设备类型", example = "1")
    private String equipType;

    @Schema(description = "计费单位 如度、方等")
    private String unit;

    @Schema(description = "是否自动断电 0否 1是")
    private String isBroken;

    @Schema(description = "可逾期天数")
    private Integer overdueDay;

    @Schema(description = "是否已电控水 0否 1是", example = "2")
    private String cutType;

    @Schema(description = "提醒值")
    private BigDecimal remindValue;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}