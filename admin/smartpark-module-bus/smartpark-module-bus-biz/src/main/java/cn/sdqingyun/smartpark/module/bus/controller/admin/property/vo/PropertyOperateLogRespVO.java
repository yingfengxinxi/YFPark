package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产操作日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyOperateLogRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "465")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7070")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "12977")
    @ExcelProperty("操作人uid")
    private Long operateUid;

    @Schema(description = "资产id信息json")
    @ExcelProperty("资产id信息json")
    private String propertyJson;

    @Schema(description = "流程编号")
    @ExcelProperty("流程编号")
    private String processCode;

    @Schema(description = "操作类型", example = "1")
    @ExcelProperty("操作类型")
    private String operateType;

    @Schema(description = "日志类型", example = "1")
    @ExcelProperty("日志类型")
    private String logType;

    @Schema(description = "操作人", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("操作人")
    private String operateUser;

    @Schema(description = "操作时间")
    @ExcelProperty("操作时间")
    private LocalDateTime operateTime;

    @Schema(description = "操作内容")
    @ExcelProperty("操作内容")
    private String operateContent;

    @Schema(description = "其他")
    @ExcelProperty("其他")
    private String otherContent;

    @Schema(description = "操作权限")
    @ExcelProperty("操作权限")
    private String operate;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}