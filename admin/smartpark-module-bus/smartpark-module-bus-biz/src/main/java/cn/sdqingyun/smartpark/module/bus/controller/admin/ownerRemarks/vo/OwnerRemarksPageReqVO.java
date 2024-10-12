package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerRemarks.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 租客备注信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OwnerRemarksPageReqVO extends PageParam {

    @Schema(description = "租客id", example = "19772")
    private Long ownId;

    @Schema(description = "机构id", example = "21676")
    private Long orgId;

    @Schema(description = "备注内容", example = "你猜")
    private String remark;

    @Schema(description = "操作人uid", example = "13731")
    private Long operationUid;

    @Schema(description = "操作人姓名", example = "张三")
    private String operationName;

    @Schema(description = "操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] operationTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}