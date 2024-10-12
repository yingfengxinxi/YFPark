package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 账单费用类型分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrgBillCostTypePageReqVO extends PageParam {

    @Schema(description = "费用分类id", example = "10176")
    private Long categoryId;

    @Schema(description = "是否为保证金类型")
    private String isBond;

    @Schema(description = "是否系统内置")
    private String isRoot;

    @Schema(description = "必须缴费的项目，否则影响业务使用")
    private String isImportant;

    @Schema(description = "费用类型名称", example = "芋艿")
    private String name;

    @Schema(description = "费用类型", example = "2")
    private String costType;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "费用状态", example = "2")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}