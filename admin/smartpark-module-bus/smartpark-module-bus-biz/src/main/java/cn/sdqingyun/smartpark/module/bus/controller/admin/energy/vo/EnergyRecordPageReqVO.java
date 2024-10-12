package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 自定义抄表记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyRecordPageReqVO extends PageParam {

    @Schema(description = "类型 1分表 2总表 3公摊表", example = "2")
    private String type;

    @Schema(description = "设备表种类", example = "1")
    private Long meterType;

    @Schema(description = "机构ID", example = "16881")
    private Long orgId;

    @Schema(description = "项目ID", example = "21680")
    private Long villageId;

    @Schema(description = "楼宇ID", example = "8264")
    private Long buildId;

    @Schema(description = "关联自定义表ID", example = "20334")
    private Long energyId;

    @Schema(description = "关联自定义价格标准ID", example = "25565")
    private Long energyPriceId;

    @Schema(description = "关联抄表任务ID", example = "22674")
    private Long energyTaskId;

    @Schema(description = "抄表负责人", example = "23531")
    private Long leadUid;

    @Schema(description = "上次抄表时间【本次计费起始时间】")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date lastTime;

    @Schema(description = "上次读数")
    private BigDecimal lastNumber;

    @Schema(description = "上次用量")
    private BigDecimal lastUse;

    @Schema(description = "本次抄表时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date thisTime;

    @Schema(description = "本次读数")
    private BigDecimal thisNumber;

    @Schema(description = "本次用量")
    private BigDecimal thisUse;

    @Schema(description = "抄表图片")
    private String image;

    @Schema(description = "状态，1启动，0审核中，4禁用", example = "2")
    private String status;

    @Schema(description = "来源 0 pc端 1 移动端")
    private String isMobile;

    @Schema(description = "是否确认 0未确认 1已确认")
    private String isConfirm;

    @Schema(description = "自动抄表 0=否;1=是")
    private String isAuto;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}