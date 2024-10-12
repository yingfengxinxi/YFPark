package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 住户的IC卡 Response VO")
@Data
@ExcelIgnoreUnannotated
public class UserIcCardRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4540")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "village_user用户表ID，可能为空", example = "23162")
    @ExcelProperty("village_user用户表ID，可能为空")
    private Long userId;

    @Schema(description = "归属租客ID", example = "29114")
    @ExcelProperty("归属租客ID")
    private Long ownerId;

    @Schema(description = "IC卡号（128位加密）")
    @ExcelProperty("IC卡号（128位加密）")
    private String icCard;

    @Schema(description = "数据状态(1使用，0禁用)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("数据状态(1使用，0禁用)")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}