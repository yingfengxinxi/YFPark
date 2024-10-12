package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 预约收费规则分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResvBillRulePageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "64")
    private Long orgId;

    @Schema(description = "应用标识")
    private String appSign;

    @Schema(description = "规则名称", example = "赵六")
    private String name;

    @Schema(description = "所属项目ID", example = "14589")
    private String villageId;

    @Schema(description = "收费标准")
    private String chargeStandard;

    @Schema(description = "是否启用多时间收费")
    private Integer isMultiTimeCharge;

    @Schema(description = "多时间收费收费标准")
    private String multiTimeChargeStandard;

    @Schema(description = "状态: 1为开启 0为关闭", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}