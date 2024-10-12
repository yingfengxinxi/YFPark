package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 耗材入库记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyStuffEnterRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14652")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6534")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String number;

    @Schema(description = "流程编号")
    @ExcelProperty("流程编号")
    private String processCode;

    @Schema(description = "入库仓库id", example = "31128")
    @ExcelProperty("入库仓库id")
    private Long depositoryId;

    @Schema(description = "入库处理人uid", example = "32101")
    @ExcelProperty("入库处理人uid")
    private Long enterUid;

    @Schema(description = "入库时间")
    @ExcelProperty("入库时间")
    private LocalDateTime enterTime;

    @Schema(description = "供应商")
    @ExcelProperty("供应商")
    private String supplier;

    @Schema(description = "合计金额", example = "23127")
    @ExcelProperty("合计金额")
    private BigDecimal totalPrice;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("单据状态;1=进行中;2=已打回;3=已撤销;4=已完结")
    private Short status;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "15999")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "23291")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "入库仓库")
    private String depositoryName;

    @Schema(description = "入库处理人")
    private String enterName;
}