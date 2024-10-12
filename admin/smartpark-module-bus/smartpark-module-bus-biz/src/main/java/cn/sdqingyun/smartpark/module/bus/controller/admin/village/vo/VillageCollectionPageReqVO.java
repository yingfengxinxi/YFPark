package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目集合表，方便快速选择分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VillageCollectionPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "938")
    private Long orgId;

    @Schema(description = "用户ID", example = "1582")
    private Long uid;

    @Schema(description = "集合名称", example = "芋艿")
    private String collectionName;

    @Schema(description = "集合下的楼宇列表")
    private String collectionBuild;

    @Schema(description = "项目类型", example = "2")
    private String villageType;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}