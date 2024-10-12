package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lvzy
 * @Date 2024/9/4 15:42
 */
@Data
public class StockListPageVO extends PageParam {



    private String ids;

    @Schema(description = "物料id")
    private Long stuffStockId;

    @Schema(description = "物料名称")
    private String stuffName;

    @Schema(description = "物料编码")
    private String stuffNumber;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "规格型号")
    private String modelName;

    @Schema(description = "物料价格")
    private BigDecimal chargePrice;

    @Schema(description = "计量单位")
    private String meterUnit;
    @Schema(description = "图片[多个值使用逗号隔开]")
    private String imageUrl;

    @Schema(description = "仓库id")
    private Long depositoryId;
}
