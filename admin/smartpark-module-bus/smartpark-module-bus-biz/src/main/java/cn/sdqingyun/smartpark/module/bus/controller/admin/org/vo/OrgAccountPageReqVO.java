package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 收支账户配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrgAccountPageReqVO extends PageParam {

    @Schema(description = "条目名称", example = "王五")
    private String name;

    @Schema(description = "收款公司")
    private String company;

    @Schema(description = "开户银行")
    private String bank;

    @Schema(description = "银行账号", example = "10688")
    private String bankAccount;

    @Schema(description = "总分类账科目")
    private String subject;

    @Schema(description = "应用的楼宇")
    private String build;

    @Schema(description = "是否启用0=否1=是", example = "2")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}