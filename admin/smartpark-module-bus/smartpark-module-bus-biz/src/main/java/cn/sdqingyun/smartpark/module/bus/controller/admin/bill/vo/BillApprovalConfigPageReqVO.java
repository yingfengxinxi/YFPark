package cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 账单业务审批配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BillApprovalConfigPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "532")
    private Long orgId;

    @Schema(description = "业务类型adjust_approve调整金", example = "2")
    private String type;

    @Schema(description = "是否开启审批;0=否;1=是")
    private String isUse;

    @Schema(description = "其他信息")
    private String extra;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}