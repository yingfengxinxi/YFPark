package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyDepositoryDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffStockDO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 耗材业务流程 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyStuffProcessRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "5627")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "280")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "流程编号")
    @ExcelProperty("流程编号")
    private String processCode;

    @Schema(description = "流程单据编号")
    @ExcelProperty("流程单据编号")
    private String processNumber;

    @Schema(description = "业务流程类型", example = "2")
    @ExcelProperty("业务流程类型")
    private String processType;

    @Schema(description = "耗材物料id", example = "18478")
    @ExcelProperty("耗材物料id")
    private Long stuffId;

    @Schema(description = "所属分类id", example = "30565")
    @ExcelProperty("所属分类id")
    private Long categoryId;

    @Schema(description = "所属仓库id", example = "17783")
    @ExcelProperty("所属仓库id")
    private Long depositoryId;

    @Schema(description = "调入仓库id", example = "23099")
    @ExcelProperty("调入仓库id")
    private Long inDepositoryId;

    @Schema(description = "物料名称", example = "王五")
    @ExcelProperty("物料名称")
    private String name;

    @Schema(description = "物料编码")
    @ExcelProperty("物料编码")
    private String number;

    @Schema(description = "品牌")
    @ExcelProperty("品牌")
    private String brand;

    @Schema(description = "规格型号", example = "赵六")
    @ExcelProperty("规格型号")
    private String modelName;

    @Schema(description = "计量单位")
    @ExcelProperty("计量单位")
    private String meterUnit;

    @Schema(description = "入库单价", example = "453")
    @ExcelProperty("入库单价")
    private BigDecimal price;

    @Schema(description = "数量")
    @ExcelProperty("数量")
    private BigDecimal num;

    @Schema(description = "总价", example = "31872")
    @ExcelProperty("总价")
    private BigDecimal totalPrice;

    @Schema(description = "物料照片;支持多张", example = "https://xxx")
    @ExcelProperty("物料照片;支持多张")
    private String imageUrl;

    @Schema(description = "耗材物料信息状态", example = "2")
    @ExcelProperty("耗材物料信息状态")
    private Short status;

    @Schema(description = "派发数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("派发数量")
    private BigDecimal handoutNum;

    @Schema(description = "退库数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("退库数量")
    private BigDecimal retreatNum;

    @Schema(description = "其他")
    @ExcelProperty("其他")
    private String extra;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "11166")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "19166")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    private PropertyStuffDO stuff;
    private PropertyDepositoryDO depository;
    private PropertyStuffStockDO stock;
    @Schema(description = "物料分类名称")
    private String categoryName;
}