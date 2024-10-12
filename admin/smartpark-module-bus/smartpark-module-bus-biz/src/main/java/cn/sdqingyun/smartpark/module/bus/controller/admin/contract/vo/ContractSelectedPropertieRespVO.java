package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 合同中选中房源 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ContractSelectedPropertieRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "6151")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "合同id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27164")
    @ExcelProperty("合同id")
    private Long contractId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "园区id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8477")
    @ExcelProperty("园区id")
    private Long parkId;

    @Schema(description = "楼宇id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27591")
    @ExcelProperty("楼宇id")
    private Long buildId;

    @Schema(description = "房间id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24755")
    @ExcelProperty("房间id")
    private Long villageRoomId;

    @Schema(description = "已选房源租赁面积")
    @ExcelProperty("已选房源租赁面积")
    private BigDecimal rentalArea;

}