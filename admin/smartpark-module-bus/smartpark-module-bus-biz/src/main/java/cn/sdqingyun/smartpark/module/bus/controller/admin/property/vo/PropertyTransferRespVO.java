package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产调拨 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyTransferRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1539")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22525")
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

    @Schema(description = "调出管理员uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "27333")
    @ExcelProperty("调出管理员uid")
    private Long outAdminUid;

    @Schema(description = "调入管理员uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "22111")
    @ExcelProperty("调入管理员uid")
    private Long inAdminUid;

    @Schema(description = "调入管理员姓名", example = "22111")
    @ExcelProperty("调入管理员姓名")
    private String inAdminUidName;

    @Schema(description = "调入位置id", example = "18013")
    @ExcelProperty("调入位置id")
    private Long inLocationId;

    @Schema(description = "处理人", requiredMode = Schema.RequiredMode.REQUIRED, example = "22115")
    @ExcelProperty("处理人")
    private Long operateUid;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "借出备注", example = "你说的对")
    @ExcelProperty("借出备注")
    private String remark;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "4451")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "22593")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "调入位置名称")
    private String inLocationName;
}