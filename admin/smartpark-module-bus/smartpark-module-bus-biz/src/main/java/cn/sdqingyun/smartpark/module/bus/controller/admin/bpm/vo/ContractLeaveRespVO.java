package cn.sdqingyun.smartpark.module.bus.controller.admin.bpm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 合同审批 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ContractLeaveRespVO {

    @Schema(description = "合同审批单主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1813")
    @ExcelProperty("合同审批单主键")
    private Long id;

    @Schema(description = "申请人的用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23063")
    @ExcelProperty("申请人的用户编号")
    private Long userId;

    @Schema(description = "开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("结束时间")
    private LocalDateTime endTime;

    @Schema(description = "审批结果 0未提交，10：审批中，20：审核通过，30：审核不通过, 40:已取消", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("审批结果 0未提交，10：审批中，20：审核通过，30：审核不通过, 40:已取消")
    private Integer status;

    @Schema(description = "合同id", example = "20791")
    @ExcelProperty("合同id")
    private String contractId;

    @Schema(description = "合同编号")
    @ExcelProperty("合同编号")
    private String contractNumber;

    @Schema(description = "流程实例的编号", example = "7931")
    @ExcelProperty("流程实例的编号")
    private String processInstanceId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}