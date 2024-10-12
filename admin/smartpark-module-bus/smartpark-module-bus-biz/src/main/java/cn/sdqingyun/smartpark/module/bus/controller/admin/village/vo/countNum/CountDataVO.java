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
@Schema(description = "园区列表统计值")
public class CountDataVO {
    @Schema(description = "管理面积", example = "40030")
    private BigDecimal managementArea;

    @Schema(description = "可招商面积", example = "20685")
    private BigDecimal rentableArea;

    @Schema(description = "总房源数量", example = "551")
    private Integer roomCount;

    @Schema(description = "可招商房源数量", example = "48")
    private Integer roomRentableCount;

    @Schema(description = "可招商比例", example = "51.67")
    private BigDecimal rentableScale;

    @Schema(description = "最大使用面积", example = "0")
    private BigDecimal maxUseArea;

    @Schema(description = "使用限制比例", example = "0")
    private BigDecimal useLimitScale;

    @Schema(description = "最大使用房间数", example = "0")
    private Integer maxUseRoom;

    @Schema(description = "使用限制房间比例", example = "0")
    private BigDecimal useLimitRoomScale;
}
