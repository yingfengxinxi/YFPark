package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.build;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @ClassName CountDataVO
 * @Description 剖面图列表查询
 * @Author SUNk
 * @Date 2024/6/26 18:38
 * @ModifyDate 2024/6/26 18:38
 * @Version 1.0
 */
@Data
@Schema(description = "剖面图列表查询请求参数")
public class BuildLayerReqVO {

    @Schema(description = "建筑ID", example = "15194")
    private Long id;

    @Schema(description = "楼层ID", example = "15194")
    private Long layerId;
}
