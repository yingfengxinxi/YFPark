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
public class BuildArrReqVO {
    @Schema(description = "项目集合")
    private List<Long> villageIdArr;

    @Schema(description = "建筑集合")
    private List<Long> buildArr;

    @Schema(description = "是否合并")
    private boolean mergeData;

    @Schema(description = "是否合并")
    private List<ProjectBuild> projectBuild;
}
