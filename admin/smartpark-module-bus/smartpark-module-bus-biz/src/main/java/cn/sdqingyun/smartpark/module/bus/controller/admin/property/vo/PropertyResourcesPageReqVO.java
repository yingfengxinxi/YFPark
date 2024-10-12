package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产静态资源管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyResourcesPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "4077")
    private Long orgId;

    @Schema(description = "资源业务类型", example = "2")
    private Integer type;

    @Schema(description = "资源第三方链接", example = "https://xxx")
    private String url;

    @Schema(description = "hash唯一标识")
    private String ossHash;

    @Schema(description = "操作人uid", example = "30046")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "24667")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}