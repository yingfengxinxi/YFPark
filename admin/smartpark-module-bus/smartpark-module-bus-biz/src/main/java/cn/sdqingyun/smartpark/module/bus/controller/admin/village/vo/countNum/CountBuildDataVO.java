package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.countNum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName CountDataVO
 * @Description 园区列表统计值
 * @Author SUNk
 * @Date 2024/6/26 18:38
 * @ModifyDate 2024/6/26 18:38
 * @Version 1.0
 */
@Data
@Schema(description = "建筑列表统计值")
public class CountBuildDataVO {
    @Schema(description = "楼宇数量", example = "10")
    private int buildCount;

    @Schema(description = "管理面积", example = "40030")
    private BigDecimal managementArea;

    @Schema(description = "房间总数", example = "100")
    private int roomCount;

    @Schema(description = "在租面积", example = "20000")
    private BigDecimal rentInArea;

    @Schema(description = "在租房间数", example = "50")
    private int rentInRoom;

    @Schema(description = "在租比例", example = "50")
    private BigDecimal rentInPercent;

    @Schema(description = "在租合同数", example = "30")
    private int rentInContract;

    @Schema(description = "待租面积", example = "20030")
    private BigDecimal toRentArea;

    @Schema(description = "待租房间数", example = "50")
    private int toRentRoom;

    @Schema(description = "本年度营收目标", example = "100000")
    private BigDecimal revenueTargetYear;

    @Schema(description = "在租房间总数比例", example = "50")
    private BigDecimal rentInRoomPercent;

    @Schema(description = "在租面积总数比例", example = "50")
    private BigDecimal rentInAreaPercent;
}
