package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 工单应用管理员信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class WorkOrderAdminRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "29400")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "3210")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识")
    @ExcelProperty("应用标识")
    private String application;

    @Schema(description = "1=管理员;2=客服工作人员", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("1=管理员;2=客服工作人员")
    private String role;

    @Schema(description = "用户uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "22093")
    @ExcelProperty("用户uid")
    private Long uid;

    @Schema(description = "岗位id集合", example = "19009")
    @ExcelProperty("岗位id集合")
    private String postId;
    private String postName;
    private Integer number;

    @Schema(description = "姓名", example = "张三")
    @ExcelProperty("姓名")
    private String name;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}