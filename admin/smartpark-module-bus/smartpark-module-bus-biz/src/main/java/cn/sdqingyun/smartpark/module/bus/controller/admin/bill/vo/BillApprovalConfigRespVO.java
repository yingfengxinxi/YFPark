package cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 账单业务审批配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BillApprovalConfigRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "6011")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", example = "532")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "业务类型adjust_approve调整金", example = "2")
    @ExcelProperty("业务类型adjust_approve调整金")
    private String type;

    @Schema(description = "是否开启审批;0=否;1=是", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否开启审批;0=否;1=是")
    private String isUse;

    @Schema(description = "其他信息")
    @ExcelProperty("其他信息")
    private String extra;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}