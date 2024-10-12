package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产归还 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyRevertRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24566")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5112")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "项目id", example = "25094")
    @ExcelProperty("项目id")
    private Long villageId;

    @Schema(description = "楼宇id", example = "168")
    @ExcelProperty("楼宇id")
    private Long buildId;

    @Schema(description = "房间id", example = "18820")
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

    @Schema(description = "单据状态", example = "2")
    @ExcelProperty("单据状态")
    private Integer status;

    @Schema(description = "归还处理人", requiredMode = Schema.RequiredMode.REQUIRED, example = "28958")
    @ExcelProperty("归还处理人")
    private Long revertUid;

    @Schema(description = "归还后使用部门", example = "26702")
    @ExcelProperty("归还后使用部门")
    private Long departmentId;

    @Schema(description = "归还时间")
    @ExcelProperty("归还时间")
    private LocalDateTime revertTime;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "归还备注", example = "随便")
    @ExcelProperty("归还备注")
    private String remark;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "16543")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "5768")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}