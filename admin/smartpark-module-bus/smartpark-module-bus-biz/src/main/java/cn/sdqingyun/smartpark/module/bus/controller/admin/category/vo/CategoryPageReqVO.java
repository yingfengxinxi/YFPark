package cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工单分类配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CategoryPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "10955")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "大类名称", example = "芋艿")
    private String name;

    @Schema(description = "运作模式;1=派单+抢单2=派单", example = "1")
    private String type;

    @Schema(description = "完成时限")
    private Integer overHour;

    @Schema(description = "是否允许转单;1=开启,0=关闭")
    private String hasChange;

    @Schema(description = "排序值")
    private Integer sort;

    @Schema(description = "应用楼宇json")
    private String buildBind;

    @Schema(description = "工单子类id集合")
    private String subcatIds;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}