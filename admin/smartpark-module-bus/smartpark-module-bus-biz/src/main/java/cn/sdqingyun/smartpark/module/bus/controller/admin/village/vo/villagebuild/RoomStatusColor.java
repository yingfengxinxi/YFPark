package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.villagebuild;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @ClassName RoomStatusColor
 * @Description TODO
 * @Author SUNk
 * @Date 2024/6/20 17:32
 * @ModifyDate 2024/6/20 17:32
 * @Version 1.0
 */
// 定义RoomStatusColor类
@Data
public class RoomStatusColor {
    @Schema(description = "颜色值")
    private String color;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "是否自定义", example = "0")
    private Integer isCustom;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "限制", example = "0")
    private Integer limit;

}