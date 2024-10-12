package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.villagebuild;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @ClassName LayerList
 * @Description TODO
 * @Author SUNk
 * @Date 2024/6/20 17:33
 * @ModifyDate 2024/6/20 17:33
 * @Version 1.0
 */
@Data
public class LayerList {
    @Schema(description = "名称")
    private String name;

    @Schema(description = "编号")
    private Long number;

    @Schema(description = "房间列表")
    private List<RoomList> roomList;

    @Schema(description = "排序值", example = "0")
    private Integer sort;

}