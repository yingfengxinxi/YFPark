package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 机构合同附件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AnnexRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25017")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "基础合同,合同主表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3510")
    @ExcelProperty("基础合同,合同主表id")
    private Integer contractId;

    @Schema(description = "文件名/文件夹", example = "赵六")
    @ExcelProperty("文件名/文件夹")
    private String name;

    @Schema(description = "1=pc;2=phone", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("1=pc;2=phone")
    private Integer annexSource;

    @Schema(description = "文件路径")
    @ExcelProperty("文件路径")
    private String filePath;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date createTime;

    @Schema(description = "创建人")
    private String creator;
}