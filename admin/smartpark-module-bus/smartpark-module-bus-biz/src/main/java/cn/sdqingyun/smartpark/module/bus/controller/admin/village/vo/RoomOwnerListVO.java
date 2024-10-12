package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lvzy
 * @Date 2024/8/1 16:33
 */
@Data
public class RoomOwnerListVO {

    @Schema(description = "房屋id")
    private Long roomId;


    @Schema(description = "项目【园区id】")
    private Long parkId;

    @Schema(description = "项目【园区名称】")
    private String parkName;

    @Schema(description = "楼宇Id")
    private Long buildId;

    @Schema(description = "楼宇")
    private String buildName;

    @Schema(description = "楼层")
    private String floor;

    @Schema(description = "房间号")
    private String roomName;

    @Schema(description = "合同id")
    private Long contractId;

    @Schema(description = "租赁面积")
    private BigDecimal rentalArea;

    @Schema(description = "房屋标签")
    private String tagName;
}
