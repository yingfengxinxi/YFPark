package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 账单缴费通知单模板 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgBillNoticeTemplateRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13483")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "通知单模板名称", example = "赵六")
    @ExcelProperty("通知单模板名称")
    private String name;

    @Schema(description = "模板上传地址")
    @ExcelProperty("模板上传地址")
    private String templatePath;

    @Schema(description = "应用楼宇")
    @ExcelProperty("应用楼宇")
    private String buildBind;

    @Schema(description = "操作时间")
    @ExcelProperty("操作时间")
    private LocalDateTime createTime;

}