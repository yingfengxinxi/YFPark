package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 自定义抄表管理员 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyAdminRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10516")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "1291")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "用户uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "5104")
    @ExcelProperty("用户uid")
    private Long uid;

    @Schema(description = "管理员姓名", example = "张三")
    @ExcelProperty("管理员姓名")
    private String name;

    @Schema(description = "0=管理员,1=超级管理员;", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("0=管理员,1=超级管理员;")
    private String role;

    @Schema(description = "状态;0=禁用,1=启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态;0=禁用,1=启用")
    private String status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}