package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.villagebuild;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @ClassName BuildList
 * @Description TODO
 * @Author SUNk
 * @Date 2024/6/20 17:34
 * @ModifyDate 2024/6/20 17:34
 * @Version 1.0
 */
@Data
public  class BuildList {
    @Schema(description = "名称")
    private String name;

    @Schema(description = "编号")
    private String number;

    @Schema(description = "排序值", example = "1")
    private Integer sort;

    @Schema(description = "层列表")
    private List<LayerList> layerList;

}
