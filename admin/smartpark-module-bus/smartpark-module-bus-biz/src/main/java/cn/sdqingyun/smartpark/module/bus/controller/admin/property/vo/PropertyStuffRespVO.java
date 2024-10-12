package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 耗材档案信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyStuffRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10012")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "32669")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "物料编码")
    @ExcelProperty("物料编码")
    private String number;

    @Schema(description = "物料名称", example = "赵六")
    @ExcelProperty("物料名称")
    private String name;

    @Schema(description = "物料分类id", example = "16205")
    @ExcelProperty("物料分类id")
    private Long categoryId;

    @Schema(description = "商品条码")
    @ExcelProperty("商品条码")
    private String barCode;

    @Schema(description = "品牌")
    @ExcelProperty("品牌")
    private String brand;

    @Schema(description = "规格型号", example = "李四")
    @ExcelProperty("规格型号")
    private String modelName;

    @Schema(description = "计量单位")
    @ExcelProperty("计量单位")
    private String meterUnit;

    @Schema(description = "小数位数(数量)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("小数位数(数量)")
    private Short quantityDigit;

    @Schema(description = "小数位数(单价)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("小数位数(单价)")
    private Short priceDigit;

    @Schema(description = "成本计算方法;1=加权平均,2=批次管理", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("成本计算方法;1=加权平均,2=批次管理")
    private Short computeMethod;

    @Schema(description = "是否固定入库单价;0=否1=是", example = "32655")
    @ExcelProperty("是否固定入库单价;0=否1=是")
    private Short lockPrice;

    @Schema(description = "入库单价;最多保留小数点后四位", example = "9569")
    @ExcelProperty("入库单价;最多保留小数点后四位")
    private BigDecimal price;

    @Schema(description = "安全固定上限数量")
    @ExcelProperty("安全固定上限数量")
    private BigDecimal safeStockUp;

    @Schema(description = "安全固定下限数量")
    @ExcelProperty("安全固定下限数量")
    private BigDecimal safeStockLower;

    @Schema(description = "限领数量(人/月)")
    @ExcelProperty("限领数量(人/月)")
    private BigDecimal receiveLimit;

    @Schema(description = "是否允许退库;0=否;1=是")
    @ExcelProperty("是否允许退库;0=否;1=是")
    private Short hasReturn;

    @Schema(description = "物料照片;支持多张", example = "https://xxx")
    @ExcelProperty("物料照片;支持多张")
    private String imageUrl;

    @Schema(description = "耗材状态;1=启用;0=禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("耗材状态;1=启用;0=禁用")
    private Short status;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "11254")
    @ExcelProperty("操作人uid")
    private Integer cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "10456")
    @ExcelProperty("修改人uid")
    private Integer muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}