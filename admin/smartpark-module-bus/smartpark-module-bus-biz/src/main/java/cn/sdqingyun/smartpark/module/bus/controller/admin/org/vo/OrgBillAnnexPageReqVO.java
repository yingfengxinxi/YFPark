package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 机构账单收支流水附件分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrgBillAnnexPageReqVO extends PageParam {

    @Schema(description = "附件唯一标识", example = "8246")
    private String objectId;

    @Schema(description = "流水id", example = "10068")
    private Long sourceId;

    @Schema(description = "父级id", example = "16007")
    private Long parentId;

    @Schema(description = "置顶排序")
    private Long sort;

    @Schema(description = "文件名/文件夹", example = "李四")
    private String name;

    @Schema(description = "账单操作类型;1=账单附件;2=流水附件", example = "2")
    private String type;

    @Schema(description = "1=pc;2=phone")
    private String annexSource;

    @Schema(description = "文件路径")
    private String filePath;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}