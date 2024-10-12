package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产维修 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyRepairRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20214")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11403")
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

    @Schema(description = "单据状态", example = "1")
    @ExcelProperty("单据状态")
    private Integer status;

    @Schema(description = "报修人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "15497")
    @ExcelProperty("报修人uid")
    private Long repairUid;

    @Schema(description = "报修部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9700")
    @ExcelProperty("报修部门id")
    private Long repairDepartmentId;

    @Schema(description = "报修时间")
    @ExcelProperty("报修时间")
    private LocalDateTime repairTime;

    @Schema(description = "报修原因", example = "不好")
    @ExcelProperty("报修原因")
    private String repairReason;

    @Schema(description = "处理人", requiredMode = Schema.RequiredMode.REQUIRED, example = "12563")
    @ExcelProperty("处理人")
    private Long operateUid;

    @Schema(description = "预计维修金额", example = "8161")
    @ExcelProperty("预计维修金额")
    private String expectRepairPrice;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "维修内容")
    @ExcelProperty("维修内容")
    private String repairContent;

    @Schema(description = "工单信息")
    @ExcelProperty("工单信息")
    private String workorderInfo;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "11957")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "4505")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "报修部门名称")
    private String repairDepartmentName;
}