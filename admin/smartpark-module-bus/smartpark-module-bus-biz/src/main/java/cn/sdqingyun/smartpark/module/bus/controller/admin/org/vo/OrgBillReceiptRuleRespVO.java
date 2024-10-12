package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 账单收据编号规则 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgBillReceiptRuleRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12810")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "收据编号规则名称", example = "李四")
    @ExcelProperty("收据编号规则名称")
    private String name;

    @Schema(description = "收据编号前缀")
    @ExcelProperty("收据编号前缀")
    private String prefix;

    @Schema(description = "开始编号")
    @ExcelProperty("开始编号")
    private String startNumber;

    @Schema(description = "结束编号")
    @ExcelProperty("结束编号")
    private String endNumber;

    @Schema(description = "应用楼宇id,多个楼宇使用逗号隔开(1,2,3)")
    @ExcelProperty("应用楼宇id,多个楼宇使用逗号隔开(1,2,3)")
    private String buildBind;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}