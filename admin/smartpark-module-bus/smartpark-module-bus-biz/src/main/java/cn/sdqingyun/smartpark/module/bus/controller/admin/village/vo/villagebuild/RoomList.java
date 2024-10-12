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
public class RoomList {
    @Schema(description = "排序值", example = "0")
    private Integer sort;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "编号")
    private Integer number;
}