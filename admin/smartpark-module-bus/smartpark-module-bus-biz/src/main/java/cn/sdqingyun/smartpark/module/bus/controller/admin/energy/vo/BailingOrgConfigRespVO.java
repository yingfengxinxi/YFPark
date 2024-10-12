package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 智能表参数配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BailingOrgConfigRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "846")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "8904")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "类型【数据字典值BAILING_ORG_CONFIG】", example = "芋艿")
    @ExcelProperty("类型【数据字典值BAILING_ORG_CONFIG】")
    private String type;
    private String typeName;

    @Schema(description = "名称", example = "芋艿")
    @ExcelProperty("名称")
    private String key;

    @Schema(description = "值")
    @ExcelProperty("值")
    private String value;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}