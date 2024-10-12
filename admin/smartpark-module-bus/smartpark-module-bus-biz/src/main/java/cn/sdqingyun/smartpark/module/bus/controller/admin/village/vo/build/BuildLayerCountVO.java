package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.build;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName BuildLayerCountVO
 * @Description 剖面图统计数据
 * @Author SUNk
 * @Date 2024/6/26 18:38
 * @ModifyDate 2024/6/26 18:38
 * @Version 1.0
 */
@Data
@Schema(description = "剖面图统计数据")
public class BuildLayerCountVO {

    @Schema(description = "管理区域", example = "16620")
    private BigDecimal managementArea;

    @Schema(description = "管理房间数量", example = "36")
    private Integer managementRoom;

    @Schema(description = "空置区域", example = "100")
    private BigDecimal emptyArea;

    @Schema(description = "空置房间数量", example = "25")
    private Integer emptyRoom;

    @Schema(description = "空置率", example = "0.6")
    private String emptyPrecent;

    @Schema(description = "空置房间比例", example = "69.44")
    private String emptyRoomPrecent;

    @Schema(description = "在租区域", example = "4150")
    private BigDecimal rentInArea;

    @Schema(description = "在租房间数量", example = "11")
    private int rentInRoom;

    @Schema(description = "在租比例", example = "24.97")
    private String rentInPrecent;

    @Schema(description = "在租房间比例", example = "30.56")
    private String rentInRoomPrecent;

    @Schema(description = "出租率", example = "0")
    private int rentRatePrecent;

    @Schema(description = "预计完成率", example = "0")
    private int expectCompletionRate;

    @Schema(description = "今年收入目标", example = "0")
    private BigDecimal nowYearRevenue;

    @Schema(description = "年度收入", example = "415798490")
    private int incomeYear;

    @Schema(description = "总收入", example = "415798490")
    private int incomeAmounts;

    @Schema(description = "月收入", example = "0")
    private int incomeMonth;

    @Schema(description = "季度收入", example = "0")
    private int incomeQuarter;

    @Schema(description = "上季度收入", example = "415798490")
    private int incomeLastQuarter;

    @Schema(description = "上半年收入", example = "415798490")
    private int incomeLastHalfYear;

    @Schema(description = "下半年收入", example = "0")
    private int incomeDownHalfYear;

    @Schema(description = "在租合同数量", example = "11")
    private int rentInContract;

    @Schema(description = "在租实际价格", example = "173.45")
    private String rentInRealPrice;
}
