package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产归还仓库信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyRevertDepositoryRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22179")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11522")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "资产id", example = "8036")
    @ExcelProperty("资产id")
    private Long propertyId;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String number;

    @Schema(description = "状态", example = "2")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "入库处理人", requiredMode = Schema.RequiredMode.REQUIRED, example = "20579")
    @ExcelProperty("入库处理人")
    private Long operateUid;

    @Schema(description = "入库处理时间")
    @ExcelProperty("入库处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "入库备注", example = "你猜")
    @ExcelProperty("入库备注")
    private String remark;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "15844")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "23517")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}