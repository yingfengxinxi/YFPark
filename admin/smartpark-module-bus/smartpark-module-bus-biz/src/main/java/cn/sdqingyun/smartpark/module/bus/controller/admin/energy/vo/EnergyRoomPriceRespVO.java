package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 房间对应自定义价格 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyRoomPriceRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13066")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "28830")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4417")
    @ExcelProperty("房间ID")
    private Long roomId;

    @Schema(description = "关联自定义价格标准ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22778")
    @ExcelProperty("关联自定义价格标准ID")
    private Long energyPriceId;

    @Schema(description = "设备表种类", example = "1")
    @ExcelProperty("设备表种类")
    private String type;

    @Schema(description = "状态，1启动，0审核中，4禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态，1启动，0审核中，4禁用")
    private String status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}