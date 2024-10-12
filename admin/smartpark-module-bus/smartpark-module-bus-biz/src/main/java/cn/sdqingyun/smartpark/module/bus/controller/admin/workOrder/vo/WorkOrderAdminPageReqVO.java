package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工单应用管理员信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkOrderAdminPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "3210")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "1=管理员;2=客服工作人员")
    private String role;

    @Schema(description = "用户uid", example = "22093")
    private Long uid;

    @Schema(description = "岗位id集合", example = "19009")
    private String postId;

    @Schema(description = "姓名", example = "张三")
    private String name;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}