package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 自定义抄表管理员分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyAdminPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "1291")
    private Long orgId;

    @Schema(description = "用户uid", example = "5104")
    private Long uid;

    @Schema(description = "管理员姓名", example = "张三")
    private String name;

    @Schema(description = "0=管理员,1=超级管理员;")
    private String role;

    @Schema(description = "状态;0=禁用,1=启用", example = "1")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}