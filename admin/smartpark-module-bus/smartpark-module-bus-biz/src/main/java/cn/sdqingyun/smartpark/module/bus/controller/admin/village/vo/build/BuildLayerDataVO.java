package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.build;

import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.LayerRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @ClassName CountDataVO
 * @Description 剖面图列表查询
 * @Author SUNk
 * @Date 2024/6/26 18:38
 * @ModifyDate 2024/6/26 18:38
 * @Version 1.0
 */
@Data
@Schema(description = "剖面图列表查询")
public class BuildLayerDataVO {
    @Schema(description = "配置颜色列表")
    private String roomStatusColor;
    @Schema(description = "楼层及房间列表")
    private List<LayerRespVO> layerRespVOS;
}
