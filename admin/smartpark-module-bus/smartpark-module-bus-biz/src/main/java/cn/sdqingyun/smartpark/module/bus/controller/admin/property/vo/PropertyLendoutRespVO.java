package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产借出 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyLendoutRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13310")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11387")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "项目id", example = "18099")
    @ExcelProperty("项目id")
    private Long villageId;

    @Schema(description = "楼宇id", example = "25285")
    @ExcelProperty("楼宇id")
    private Long buildId;

    @Schema(description = "房间id", example = "30850")
    @ExcelProperty("房间id")
    private Long roomId;

    @Schema(description = "资产id集合json")
    @ExcelProperty("资产id集合json")
    private String propertyIds;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String number;

    @Schema(description = "流程编号")
    @ExcelProperty("流程编号")
    private String processCode;

    @Schema(description = "数据类型;1=借出;2=归还", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("数据类型")
    private Short type;

    @Schema(description = "单据状态", example = "2")
    @ExcelProperty("单据状态")
    private Integer status;

    @Schema(description = "借用人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "7141")
    @ExcelProperty("借用人uid")
    private Long lendUid;

    @Schema(description = "借用部门id", example = "2325")
    @ExcelProperty("借用部门id")
    private Long departmentId;

    @Schema(description = "借出时间")
    @ExcelProperty("借出时间")
    private LocalDateTime lendTime;

    @Schema(description = "预计归还时间")
    @ExcelProperty("预计归还时间")
    private LocalDateTime expectRevertTime;

    @Schema(description = "处理人", requiredMode = Schema.RequiredMode.REQUIRED, example = "10181")
    @ExcelProperty("处理人")
    private Long operateUid;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "借出备注", example = "你猜")
    @ExcelProperty("借出备注")
    private String remark;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "22583")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "29507")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "借用人姓名")
    private String lendName;

    @Schema(description = "借用部门")
    private String departmentName;
}