package cn.sdqingyun.smartpark.module.bus.controller.admin.bill.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 流水删除历史 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BillStreamDeleteHistoryRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17718")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "流水id", requiredMode = Schema.RequiredMode.REQUIRED, example = "13960")
    @ExcelProperty("流水id")
    private Integer streamId;

    @Schema(description = "审批id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21888")
    @ExcelProperty("审批id")
    private Integer approvalId;

    @Schema(description = "审批状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("审批状态")
    private Integer approvalStatus;

    @Schema(description = "0新建1审批回调", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("0新建1审批回调")
    private Integer type;

    @Schema(description = "审批结束时间")
    @ExcelProperty("审批结束时间")
    private LocalDateTime approvalTime;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}