package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.villagebuild;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @ClassName BuildArr
 * @Description TODO
 * @Author SUNk
 * @Date 2024/6/20 17:34
 * @ModifyDate 2024/6/20 17:34
 * @Version 1.0
 */
@Data
public class BuildArr {
    @Schema(description = "名称")
    private String name;

    @Schema(description = "编号")
    private String number;

    @Schema(description = "排序值")
    private String sort;

    @Schema(description = "楼宇列表")
    private List<BuildList> buildList;

}
