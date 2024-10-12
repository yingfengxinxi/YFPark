package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 耗材业务退库 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyStuffRetreatRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11495")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31583")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "流程编号")
    @ExcelProperty("流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String processNumber;

    @Schema(description = "退库人uid", example = "7917")
    @ExcelProperty("退库人uid")
    private Long retreatUid;

    @Schema(description = "部门id", example = "14623")
    @ExcelProperty("部门id")
    private Long departmentId;

    @Schema(description = "部门名称", example = "赵六")
    @ExcelProperty("部门名称")
    private String departmentName;

    @Schema(description = "入库仓库id", example = "12122")
    @ExcelProperty("入库仓库id")
    private Long depositoryId;

    @Schema(description = "退库时间")
    @ExcelProperty("退库时间")
    private LocalDateTime retreatDate;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("单据状态;1=进行中;2=已打回;3=已撤销;4=已完结")
    private Short status;

    @Schema(description = "退库备注", example = "随便")
    @ExcelProperty("退库备注")
    private String remark;

    @Schema(description = "处理人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "5743")
    @ExcelProperty("处理人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "26278")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "退库人uid")
    private String retreatName;

    @Schema(description = "入库仓库")
    private String depositoryName;
}