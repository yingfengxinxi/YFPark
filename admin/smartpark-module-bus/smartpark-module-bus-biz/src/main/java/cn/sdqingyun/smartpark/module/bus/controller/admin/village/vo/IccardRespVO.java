package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目IC卡表（可能会绑定人员，因不同设备需要而定） Response VO")
@Data
@ExcelIgnoreUnannotated
public class IccardRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31252")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "卡ID（128位加密）", example = "10600")
    @ExcelProperty("卡ID（128位加密）")
    private String cardId;

    @Schema(description = "房间ID", example = "12701")
    @ExcelProperty("房间ID")
    private Long roomId;

    @Schema(description = "楼层ID", example = "14109")
    @ExcelProperty("楼层ID")
    private Long layerId;

    @Schema(description = "单元ID", example = "1251")
    @ExcelProperty("单元ID")
    private Long unitId;

    @Schema(description = "楼栋ID", example = "2632")
    @ExcelProperty("楼栋ID")
    private Long buildId;

    @Schema(description = "分区ID", example = "20095")
    @ExcelProperty("分区ID")
    private Long zoneId;

    @Schema(description = "项目ID", example = "22149")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "用户ID（可能为空）", example = "27406")
    @ExcelProperty("用户ID（可能为空）")
    private Long roomUserId;

    @Schema(description = "数据状态(1使用，0禁用)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("数据状态(1使用，0禁用)")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}