package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 合同到期提醒规则分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractExpireRulePageReqVO extends PageParam {

    @Schema(description = "合同到期规则名称", example = "赵六")
    private String ruleName;

    @Schema(description = "关联楼宇id，多个id用逗号隔开")
    private String relationBuilds;

    @Schema(description = "提醒方式0=站内1=公众号")
    private String reminderMethod;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}