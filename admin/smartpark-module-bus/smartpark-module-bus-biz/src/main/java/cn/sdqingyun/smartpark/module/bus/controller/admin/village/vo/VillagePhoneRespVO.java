package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目电话 Response VO")
@Data
@ExcelIgnoreUnannotated
public class VillagePhoneRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32410")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18628")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4993")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23819")
    @ExcelProperty("分类ID")
    private Long catId;

    @Schema(description = "号码名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("号码名称")
    private String phoneName;

    @Schema(description = "号码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("号码")
    private String phone;

    @Schema(description = "排序值", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序值")
    private Integer sort;

    @Schema(description = "状态（1正常，0隐藏）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态（1正常，0隐藏）")
    private Integer status;

    @Schema(description = "紧急电话（1是，0否）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("紧急电话（1是，0否）")
    private Integer urgent;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "编号")
    private Long pid;
}