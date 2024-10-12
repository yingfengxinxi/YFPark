package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 自定义关联分总分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyBindPageReqVO extends PageParam {

    @Schema(description = "类型 1分表 2总表", example = "1")
    private String type;

    @Schema(description = "设备表种类", example = "1")
    private Long meterType;

    @Schema(description = "用途类型 1分表 2总表 3公摊表", example = "2")
    private String purposeType;

    @Schema(description = "机构ID", example = "4286")
    private Long orgId;

    @Schema(description = "项目ID", example = "10718")
    private Long villageId;

    @Schema(description = "楼宇ID", example = "18372")
    private Long buildId;

    @Schema(description = "自身自定义表ID", example = "9240")
    private Long energyId;

    @Schema(description = "关联自定义表ID", example = "30103")
    private Long parentEnergyId;

    @Schema(description = "表名称", example = "芋艿")
    private String name;

    @Schema(description = "绑定表数据集合，json存储")
    private String binds;

    @Schema(description = "扩展字段 类型为1-分表-分摊比例,类型为2-总表-电表用量")
    private String extendContent;

    @Schema(description = "最近抄录时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date latelyMeterTime;

    @Schema(description = "状态，1启动，0审核中，4禁用", example = "1")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}