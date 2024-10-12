package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 预约收费规则 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ResvBillRuleRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28877")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "64")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应用标识")
    private String appSign;

    @Schema(description = "规则名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("规则名称")
    private String name;

    @Schema(description = "所属项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14589")
    @ExcelProperty("所属项目ID")
    private String villageId;

    @Schema(description = "收费标准", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("收费标准")
    private String chargeStandard;

    @Schema(description = "是否启用多时间收费", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否启用多时间收费")
    private Integer isMultiTimeCharge;

    @Schema(description = "多时间收费收费标准", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("多时间收费收费标准")
    private String multiTimeChargeStandard;

    @Schema(description = "状态: 1为开启 0为关闭", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态: 1为开启 0为关闭")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "所属项目名称")
    private String villageName;
}