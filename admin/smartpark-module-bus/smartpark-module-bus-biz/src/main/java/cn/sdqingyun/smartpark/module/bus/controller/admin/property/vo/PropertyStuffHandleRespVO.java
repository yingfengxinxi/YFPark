package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 耗材业务处置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyStuffHandleRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24238")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9336")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "流程编号")
    @ExcelProperty("流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String processNumber;

    @Schema(description = "发起部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30395")
    @ExcelProperty("发起部门id")
    private Long departmentId;

    @Schema(description = "发起部门名称", example = "张三")
    @ExcelProperty("发起部门名称")
    private String departmentName;

    @Schema(description = "处置仓库id", example = "2738")
    @ExcelProperty("处置仓库id")
    private Long depositoryId;

    @Schema(description = "发起时间")
    @ExcelProperty("发起时间")
    private LocalDateTime launchTime;

    @Schema(description = "合计金额", example = "12235")
    @ExcelProperty("合计金额")
    private BigDecimal totalPrice;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("单据状态;1=进行中;2=已打回;3=已撤销;4=已完结")
    private Short status;

    @Schema(description = "处置原因", example = "你说的对")
    @ExcelProperty("处置原因")
    private String remark;

    @Schema(description = "发起人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "4075")
    @ExcelProperty("发起人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "4792")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "处置仓库")
    private String depositoryName;
}