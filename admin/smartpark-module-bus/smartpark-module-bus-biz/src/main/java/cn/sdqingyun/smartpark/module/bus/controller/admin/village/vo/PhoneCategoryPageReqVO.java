package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目电话类型分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PhoneCategoryPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "1959")
    private Long orgId;

    @Schema(description = "项目ID", example = "16209")
    private Long villageId;

    @Schema(description = "分类名称", example = "赵六")
    private String catName;

    @Schema(description = "排序值")
    private Integer sort;

    @Schema(description = "状态（1正常，0隐藏）", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}