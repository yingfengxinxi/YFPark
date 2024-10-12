package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerPaySet.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 企业自动缴费费用配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OwnerPaySetPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "27029")
    private Long orgId;

    @Schema(description = "所属项目id", example = "6931")
    private Long villageId;

    @Schema(description = "楼宇id", example = "4744")
    private Long buildId;

    @Schema(description = "租客id", example = "12599")
    private Long ownerId;

    @Schema(description = "自动缴费费用类型设置")
    private String costTypes;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}