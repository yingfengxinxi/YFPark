package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产处置单据记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyHandleRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32249")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", example = "8492")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "发起部门id", example = "10382")
    @ExcelProperty("发起部门id")
    private Long departmentId;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String processNumber;

    @Schema(description = "单据状态;1=进行中", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("单据状态;1=进行中")
    private Integer status;

    @Schema(description = "处置金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("处置金额")
    private BigDecimal handleAmount;

    @Schema(description = "处置费用", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("处置费用")
    private BigDecimal handleExpenses;

    @Schema(description = "处置类型", example = "1")
    @ExcelProperty("处置类型")
    private Integer handleType;

    @Schema(description = "处置原因", example = "你说的对")
    @ExcelProperty("处置原因")
    private String remark;

    @Schema(description = "发起时间")
    @ExcelProperty("发起时间")
    private LocalDateTime applyTime;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "16577")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "25895")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "发起部门名称", example = "10382")
    private String departmentName;

    @Schema(description = "发起人名称", example = "10382")
    private String userName;

    @Schema(description = "发起人id")
    private String creator;
}