package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产分类分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyCategoryPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "25905")
    private Long orgId;

    @Schema(description = "分类编码")
    private String number;

    @Schema(description = "分类名称", example = "赵六")
    private String name;

    @Schema(description = "分类级别字符串，逗号拼接上级id")
    private String level;

    @Schema(description = "父级id", example = "14183")
    private Long parentId;

    @Schema(description = "参数")
    private String param;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "显示状态，2禁用 1显示，0隐藏", example = "2")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}