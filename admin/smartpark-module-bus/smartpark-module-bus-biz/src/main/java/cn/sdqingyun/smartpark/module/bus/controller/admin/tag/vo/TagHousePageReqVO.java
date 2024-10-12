package cn.sdqingyun.smartpark.module.bus.controller.admin.tag.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 房源标签分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TagHousePageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "11195")
    private Integer orgId;

    @Schema(description = "标签名称", example = "赵六")
    private String name;

    @Schema(description = "标签描述")
    private String descVillage;

    @Schema(description = "标签样式")
    private String color;

    @Schema(description = "用户是否可见（1可见，0不可见）")
    private Integer userShow;

    @Schema(description = "状态，1启用，0禁用", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}