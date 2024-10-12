package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 预约应用 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ResvApplicationRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14034")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "应用名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("应用名称")
    private String name;

    @Schema(description = "应用简称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("应用简称")
    private String shortName;

    @Schema(description = "应用标志", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应用标志")
    private String sign;

    @Schema(description = "图标", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("图标")
    private String icon;

    @Schema(description = "取消订单规则")
    @ExcelProperty("取消订单规则")
    private String cancelOrderRule;

    @Schema(description = "是否支持退款", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否支持退款")
    private Integer refundSupported;

    @Schema(description = "退款规则")
    @ExcelProperty("退款规则")
    private String refundRule;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}