package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 税收分类编码配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgTaxCodeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7993")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "商品/服务名称", example = "李四")
    @ExcelProperty("商品/服务名称")
    private String name;

    @Schema(description = "税收编号")
    @ExcelProperty("税收编号")
    private String taxCode;

    @Schema(description = "税率")
    @ExcelProperty("税率")
    private BigDecimal taxRate;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}