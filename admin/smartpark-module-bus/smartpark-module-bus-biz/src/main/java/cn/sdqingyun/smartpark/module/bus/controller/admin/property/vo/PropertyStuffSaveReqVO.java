package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 耗材档案信息新增/修改 Request VO")
@Data
public class PropertyStuffSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10012")
    private Long id;

    @Schema(description = "机构id")
    private Long orgId;

    @Schema(description = "物料编码")
    private String number;

    @Schema(description = "物料名称", example = "赵六")
    private String name;

    @Schema(description = "物料分类id", example = "16205")
    private Long categoryId;

    @Schema(description = "商品条码")
    private String barCode;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "规格型号", example = "李四")
    private String modelName;

    @Schema(description = "计量单位")
    private String meterUnit;

    @Schema(description = "小数位数(数量)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "小数位数(数量)不能为空")
    private Short quantityDigit;

    @Schema(description = "小数位数(单价)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "小数位数(单价)不能为空")
    private Short priceDigit;

    @Schema(description = "成本计算方法;1=加权平均,2=批次管理", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "成本计算方法;1=加权平均,2=批次管理不能为空")
    private Short computeMethod;

    @Schema(description = "是否固定入库单价;0=否1=是", example = "32655")
    private Short lockPrice;

    @Schema(description = "入库单价;最多保留小数点后四位", example = "9569")
    private BigDecimal price;

    @Schema(description = "安全固定上限数量")
    private BigDecimal safeStockUp;

    @Schema(description = "安全固定下限数量")
    private BigDecimal safeStockLower;

    @Schema(description = "限领数量(人/月)")
    private BigDecimal receiveLimit;

    @Schema(description = "是否允许退库;0=否;1=是")
    private Short hasReturn;

    @Schema(description = "物料照片;支持多张", example = "https://xxx")
    private String imageUrl;

    @Schema(description = "耗材状态;1=启用;0=禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "耗材状态;1=启用;0=禁用不能为空")
    private Short status;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "操作人uid")
    private Integer cuserUid;

    @Schema(description = "修改人uid")
    private Integer muserUid;

}