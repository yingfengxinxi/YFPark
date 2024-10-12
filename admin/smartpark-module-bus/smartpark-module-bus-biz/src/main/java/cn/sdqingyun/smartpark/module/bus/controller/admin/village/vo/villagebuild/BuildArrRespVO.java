package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.villagebuild;

import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.BuildRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageCollectionRespVO;
import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.VillageRespVO;
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
public class BuildArrRespVO {
    @Schema(description = "项目集合")
    private List<VillageRespVO> villageList;

    @Schema(description = "建筑集合")
    private List<BuildRespVO> villageBuildList;

    @Schema(description = "合并后的项目集合")
    private List<VillageRespVO> villageRespVOS;

    @Schema(description = "用户建筑集合列表")
    private List<VillageCollectionRespVO> villageCollectionRespVOS;
}
