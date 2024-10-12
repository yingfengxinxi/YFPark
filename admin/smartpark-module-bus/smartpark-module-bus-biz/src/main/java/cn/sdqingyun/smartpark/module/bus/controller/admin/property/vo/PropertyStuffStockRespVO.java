package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 耗材即时库存 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyStuffStockRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18952")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29345")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "耗材物料id", example = "18539")
    @ExcelProperty("耗材物料id")
    private Long stuffId;

    @Schema(description = "仓库id;需同步该信息", requiredMode = Schema.RequiredMode.REQUIRED, example = "19709")
    @ExcelProperty("仓库id;需同步该信息")
    private Long depositoryId;

    @Schema(description = "入库流程编号")
    @ExcelProperty("入库流程编号")
    private String processCode;

    @Schema(description = "可用库存")
    @ExcelProperty("可用库存")
    private BigDecimal usableNum;

    @Schema(description = "冻结库存")
    @ExcelProperty("冻结库存")
    private BigDecimal frozenNum;

    @Schema(description = "总库存")
    @ExcelProperty("总库存")
    private BigDecimal totalNum;

    @Schema(description = "总金额", example = "15952")
    @ExcelProperty("总金额")
    private BigDecimal totalPrice;

    @Schema(description = "耗材定价", example = "30366")
    @ExcelProperty("耗材定价")
    private BigDecimal chargePrice;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "其他")
    @ExcelProperty("其他")
    private String extra;

    @Schema(description = "是否达到安全库存上限;默认安全", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否达到安全库存上限;默认安全")
    private Short isStockUp;

    @Schema(description = "是否达到安全库存下限;默认安全", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否达到安全库存下限;默认安全")
    private Short isStockLower;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "24099")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "4194")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "物料详情")
    private PropertyStuffRespVO stuff;
    @Schema(description = "仓库详情")
    private PropertyDepositoryRespVO depository;
    @ExcelProperty("物料分类名称")
    private String categoryName;
}