package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerFiles.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 租客附件分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OwnerFilesPageReqVO extends PageParam {

    @Schema(description = "租客ID", example = "15447")
    private Long ownerId;

    @Schema(description = "机构ID", example = "23533")
    private Long orgId;

    @Schema(description = "用户服务ID", example = "12906")
    private Long uid;

    @Schema(description = "文件名", example = "芋艿")
    private String name;

    @Schema(description = "文件网址", example = "https://xxx")
    private String url;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}