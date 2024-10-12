package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 收支账户配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgAccountRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18788")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "条目名称", example = "王五")
    @ExcelProperty("条目名称")
    private String name;

    @Schema(description = "收款公司")
    @ExcelProperty("收款公司")
    private String company;

    @Schema(description = "开户银行")
    @ExcelProperty("开户银行")
    private String bank;

    @Schema(description = "银行账号", example = "10688")
    @ExcelProperty("银行账号")
    private String bankAccount;

    @Schema(description = "总分类账科目")
    @ExcelProperty("总分类账科目")
    private String subject;

    @Schema(description = "应用的楼宇")
    @ExcelProperty("应用的楼宇")
    private String build;

    @Schema(description = "是否启用0=否1=是", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("是否启用0=否1=是")
    private String status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "应用的楼宇名称")
    private String buildName;
}