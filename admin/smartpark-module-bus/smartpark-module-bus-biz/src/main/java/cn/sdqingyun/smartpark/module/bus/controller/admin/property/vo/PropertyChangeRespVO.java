package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产变更领用人 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyChangeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3270")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29372")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "资产id")
    @ExcelProperty("资产id")
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

    @Schema(description = "变更后使用人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "16926")
    @ExcelProperty("变更后使用人uid")
    private Long afterUseUid;

    @Schema(description = "变更后使用部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20832")
    @ExcelProperty("变更后使用部门id")
    private Long afterUseDepartmentId;

    @Schema(description = "变更时间")
    @ExcelProperty("变更时间")
    private LocalDateTime afterTime;

    @Schema(description = "处理人", requiredMode = Schema.RequiredMode.REQUIRED, example = "21094")
    @ExcelProperty("处理人")
    private Long operateUid;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "变更说明", example = "随便")
    @ExcelProperty("变更说明")
    private String remark;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "3815")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "选择的派发单据编号property_handout中的id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30010")
    @ExcelProperty("选择的派发单据编号id不能为空")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "变更后使用人")
    private String afterUseName;

    @Schema(description = "变更后使用部门")
    private String afterUseDepartmentName;
}