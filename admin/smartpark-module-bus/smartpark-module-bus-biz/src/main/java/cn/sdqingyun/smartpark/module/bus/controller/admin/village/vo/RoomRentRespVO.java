package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 租客在租/绑定房间 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RoomRentRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24763")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4965")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3743")
    @ExcelProperty("项目id")
    private Long villageId;

    @Schema(description = "楼宇id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28546")
    @ExcelProperty("楼宇id")
    private Long buildId;

    @Schema(description = "楼层id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1061")
    @ExcelProperty("楼层id")
    private Long layerId;

    @Schema(description = "房间id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7631")
    @ExcelProperty("房间id")
    private Long roomId;

    @Schema(description = "租客id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23794")
    @ExcelProperty("租客id")
    private Long ownerId;

    @Schema(description = "合同id", example = "7074")
    @ExcelProperty("合同id")
    private Integer contractId;

    @Schema(description = "租赁到期时间")
    @ExcelProperty("租赁到期时间")
    private LocalDateTime expireTime;

    @Schema(description = "来源标识;1=合同;2=物业绑定", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("来源标识;1=合同;2=物业绑定")
    private Integer source;

    @Schema(description = "房间状态;0=在租;", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("房间状态;0=在租;")
    private Integer status;

    @Schema(description = "其他")
    @ExcelProperty("其他")
    private String extra;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}