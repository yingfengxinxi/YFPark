package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 耗材档案信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyStuffPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "32669")
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

    @Schema(description = "小数位数(数量)")
    private Short quantityDigit;

    @Schema(description = "小数位数(单价)")
    private Short priceDigit;

    @Schema(description = "成本计算方法;1=加权平均,2=批次管理")
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

    @Schema(description = "耗材状态;1=启用;0=禁用", example = "1")
    private Short status;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "操作人uid", example = "11254")
    private Integer cuserUid;

    @Schema(description = "修改人uid", example = "10456")
    private Integer muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}