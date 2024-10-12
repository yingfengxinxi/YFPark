package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 位置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PatrolLocationPageReqVO extends PageParam {

    private Long id;

    @Schema(description = "机构ID", example = "25119")
    private Long orgId;

    @Schema(description = "位置编码")
    private String number;

    @Schema(description = "位置名称", example = "王五")
    private String name;

    @Schema(description = "位置级别字符串，逗号拼接上级id")
    private String level;

    @Schema(description = "位置级别字符串，斜杠拼接上级name")
    private String levelName;
    @Schema(description = "父级id", example = "31662")
    private Long parentId;

    @Schema(description = "参数")
    private String param;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "显示状态，2禁用 1显示【数据字典PATROL_LOCATION_STATUS】", example = "1")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}