package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 收据模板 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgBillReceiptTemplateRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "6091")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "收据模板名称", example = "赵六")
    @ExcelProperty("收据模板名称")
    private String name;

    @Schema(description = "模板上传地址")
    @ExcelProperty("模板上传地址")
    private String templatePath;

    @Schema(description = "应用楼宇id,多个楼宇使用逗号隔开(1,2,3)")
    @ExcelProperty("应用楼宇id,多个楼宇使用逗号隔开(1,2,3)")
    private String buildBind;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}