package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

/**
 * @Author lvzy
 * @Date 2024/9/25 15:11
 */
@Data
public class EnergyNotCompleteTaskListVO {

    /**
     * 编号
     */
    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22388")
    private Long energyId;

    /**
     * 名称
     */
    @Schema(description = "表名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "22388")
    private String name;
    /**
     * 表具编号
     */
    @Schema(description = "表具编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22388")
    private String number;

    /**
     * 用途 1-分表 2-总表 3-公摊表
     */
    @Schema(description = "用途 1-分表 2-总表 3-公摊表", requiredMode = Schema.RequiredMode.REQUIRED, example = "22388")
    private String purpose;

    @Schema(description = "绑定位置", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bindingLocation;

    @Schema(description = "最近抄录的时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date thisTime;

    @Schema(description = "上次读数", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal thisNumber;

    @Schema(description = "任务iD", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long energyTaskId;
}
