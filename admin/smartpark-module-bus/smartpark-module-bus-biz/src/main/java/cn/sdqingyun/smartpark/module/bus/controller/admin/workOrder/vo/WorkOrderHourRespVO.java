package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 工单工时配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class WorkOrderHourRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24279")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", example = "351")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "应用标识")
    @ExcelProperty("应用标识")
    private String application;

    @Schema(description = "工时名称", example = "李四")
    @ExcelProperty("工时名称")
    private String name;

    @Schema(description = "工时费用")
    @ExcelProperty("工时费用")
    private BigDecimal hourFee;

    @Schema(description = "工时计算规则;1=保留两位小数;2=去除小数部分;3=四舍五入去除小数;", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("工时计算规则;1=保留两位小数;2=去除小数部分;3=四舍五入去除小数;")
    private String computeRule;

    @Schema(description = "部门id", example = "4416")
    private Long departmentId;

    @ExcelProperty("部门名称")
    private String departmentName;

    @Schema(description = "岗位信息json", example = "10182")
    private String stationId;

    @ExcelProperty("岗位信息")
    private String stationName;

    @Schema(description = "岗位员工信息json")
    @ExcelProperty("岗位员工信息json")
    private String postUids;

    @Schema(description = "启用状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("启用状态")
    private String status;

    @Schema(description = "是否为默认配置", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否为默认配置")
    private String isDefault;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    //是否可以删除
    @Schema(description = "是否可以删除0=否1=是")
    @ExcelProperty("是否可以删除0=否1=是")
    private String isDelete;

}