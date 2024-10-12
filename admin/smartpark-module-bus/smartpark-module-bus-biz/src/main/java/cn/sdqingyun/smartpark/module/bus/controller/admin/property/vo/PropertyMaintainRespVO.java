package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产保养记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyMaintainRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26509")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", example = "11036")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "保养人uid", example = "11772")
    @ExcelProperty("保养人uid")
    private Long maintainUid;

    @Schema(description = "保养人所在部门", example = "30809")
    @ExcelProperty("保养人所在部门")
    private Long departmentId;

    @Schema(description = "保养人所在部门名称", example = "赵六")
    @ExcelProperty("保养人所在部门名称")
    private String departmentName;

    @Schema(description = "保养项目id", example = "23931")
    @ExcelProperty("保养项目id")
    private String maintainVillageId;

    @Schema(description = "资产id集合json")
    @ExcelProperty("资产id集合json")
    private String propertyIds;

    @Schema(description = "资产集合json")
    @ExcelProperty("资产集合json")
    private String propertyData;

    @Schema(description = "单据编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("单据编号")
    private String number;

    @Schema(description = "流程编号")
    @ExcelProperty("流程编号")
    private String processCode;

    @Schema(description = "单据状态", example = "2")
    @ExcelProperty("单据状态")
    private Integer status;

    @Schema(description = "保养总金额", example = "30690")
    @ExcelProperty("保养总金额")
    private BigDecimal maintainTotalPrice;

    @Schema(description = "保养时间")
    @ExcelProperty("保养时间")
    private LocalDateTime maintainDate;

    @Schema(description = "下次保养时间")
    @ExcelProperty("下次保养时间")
    private LocalDateTime nextMaintainDate;

    @Schema(description = "处理人", example = "22505")
    @ExcelProperty("处理人")
    private Long operateUid;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "保养备注", example = "你说的对")
    @ExcelProperty("保养备注")
    private String remark;

    @Schema(description = "操作人", example = "5222")
    @ExcelProperty("操作人")
    private Long cuserUid;

    @Schema(description = "修改人", example = "16190")
    @ExcelProperty("修改人")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "保养人")
    private String maintainName;
}