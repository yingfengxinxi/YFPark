package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.villagebuild.BuildArrRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目集合表，方便快速选择 Response VO")
@Data
@ExcelIgnoreUnannotated
public class VillageCollectionRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30355")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "938")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1582")
    @ExcelProperty("用户ID")
    private Long uid;

    @Schema(description = "集合名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("集合名称")
    private String collectionName;

    @Schema(description = "集合下的楼宇列表")
    @ExcelProperty("集合下的楼宇列表")
    private String collectionBuild;

    @Schema(description = "项目类型", example = "2")
    @ExcelProperty("项目类型")
    private String villageType;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "建筑楼宇集合")
    private BuildArrRespVO villageAndBuild;

    @Schema(description = "集合下的楼宇列表名称")
    private String collectionBuildName;
}