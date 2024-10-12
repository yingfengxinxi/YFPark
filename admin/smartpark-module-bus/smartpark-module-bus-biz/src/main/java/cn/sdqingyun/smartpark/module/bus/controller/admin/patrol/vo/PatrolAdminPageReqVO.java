package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 资产管理子应用管理员配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PatrolAdminPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "17415")
    private Long orgId;

    @Schema(description = "应用标识EQUIPMENT_INSPECTION=巡检;SECURITY_INSPECTION=安防", example = "17415")
    private String application;

    @Schema(description = "用户uid", example = "6750")
    private Long uid;

    @Schema(description = "部门id", example = "7100")
    private String departmentId;

    @Schema(description = "管理员级别,1=普通管理员;99=超级管理员")
    private String level;

    @Schema(description = "姓名", example = "张三")
    private String name;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "状态;0=禁用,1=启用", example = "2")
    private String status;

    @Schema(description = "最后访问角色类型")
    private String lastRole;

    @Schema(description = "最后访问登录时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date lastTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}