package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产标签模板分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyTagPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "7559")
    private Long orgId;

    @Schema(description = "0=未应用;1=应用")
    private Short isApply;

    @Schema(description = "0=自定义模板;1=系统默认模板")
    private Short isDefault;

    @Schema(description = "模板链接")
    private String templatePath;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "模板名称", example = "赵六")
    private String name;

    @Schema(description = "字段上限数量")
    private Integer fieldLimit;

    @Schema(description = "有无logo;0=无;1=有")
    private Short hasLogo;

    @Schema(description = "应用数据json")
    private String applyJson;

    @Schema(description = "操作人uid", example = "6095")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "22518")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}