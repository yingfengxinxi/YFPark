package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.countNum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName CountDataVO
 * @Description 项目概况统计值
 * @Author SUNk
 * @Date 2024/6/26 18:38
 * @ModifyDate 2024/6/26 18:38
 * @Version 1.0
 */
@Data
@Schema(description = "园区列表统计值")
public class ProjectOverviewVO {
    @Schema(description = "在租合同数", example = "1")
    private Integer contractCount;

    @Schema(description = "房源数量", example = "1")
    private Integer housesNum;

    @Schema(description = "管理面积", example = "1")
    private BigDecimal managementArea;

    @Schema(description = "在租实时均价", example = "23")
    private BigDecimal averagePrice;

    @Schema(description = "建筑面积", example = "48")
    private BigDecimal builtArea;

    @Schema(description = "在租面积", example = "51.67")
    private BigDecimal rentedArea;

    @Schema(description = "出租率", example = "30")
    private BigDecimal rentalRate;

    @Schema(description = "待租面积", example = "133")
    private BigDecimal WaitingArea;

    // 默认构造函数，初始化默认值
    public ProjectOverviewVO() {
        this.contractCount = 0;
        this.housesNum = 0;
        this.managementArea = BigDecimal.ZERO;
        this.averagePrice = BigDecimal.ZERO;
        this.builtArea = BigDecimal.ZERO;
        this.rentedArea = BigDecimal.ZERO;
        this.rentalRate = BigDecimal.ZERO;
    }

}
