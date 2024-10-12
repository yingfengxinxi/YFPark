package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 资产管理子应用管理员配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PatrolAdminRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26511")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "17415")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识EQUIPMENT_INSPECTION=巡检;SECURITY_INSPECTION=安防", example = "17415")
    private String application;

    @Schema(description = "用户uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "6750")
    @ExcelProperty("用户uid")
    private Long uid;

    @Schema(description = "部门id", example = "7100")
    @ExcelProperty("部门id")
    private String departmentId;

    @Schema(description = "管理员级别,1=普通管理员;99=超级管理员", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("管理员级别,1=普通管理员;99=超级管理员")
    private String level;

    @Schema(description = "姓名", example = "张三")
    @ExcelProperty("姓名")
    private String name;

    @Schema(description = "用户头像")
    @ExcelProperty("用户头像")
    private String avatar;

    @Schema(description = "手机号码")
    @ExcelProperty("手机号码")
    private String phone;

    @Schema(description = "状态;0=禁用,1=启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态;0=禁用,1=启用")
    private String status;

    @Schema(description = "最后访问角色类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("最后访问角色类型")
    private String lastRole;

    @Schema(description = "最后访问登录时间")
    @ExcelProperty("最后访问登录时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date lastTime;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}