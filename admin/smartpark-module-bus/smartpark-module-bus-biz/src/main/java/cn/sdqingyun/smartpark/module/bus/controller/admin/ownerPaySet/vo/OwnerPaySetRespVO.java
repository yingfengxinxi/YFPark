package cn.sdqingyun.smartpark.module.bus.controller.admin.ownerPaySet.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 企业自动缴费费用配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OwnerPaySetRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32365")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", example = "27029")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "所属项目id", example = "6931")
    @ExcelProperty("所属项目id")
    private Long villageId;

    @Schema(description = "楼宇id", example = "4744")
    @ExcelProperty("楼宇id")
    private Long buildId;

    @Schema(description = "租客id", example = "12599")
    @ExcelProperty("租客id")
    private Long ownerId;

    @Schema(description = "自动缴费费用类型设置")
    @ExcelProperty("自动缴费费用类型设置")
    private String costTypes;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}