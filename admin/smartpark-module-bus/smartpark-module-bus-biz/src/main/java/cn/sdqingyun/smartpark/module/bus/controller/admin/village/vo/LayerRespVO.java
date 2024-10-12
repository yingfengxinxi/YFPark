package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 项目楼层 Response VO")
@Data
@ExcelIgnoreUnannotated
public class LayerRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26108")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "楼层编号")
    @ExcelProperty("楼层编号")
    private Long layerNumber;

    @Schema(description = "楼层名称", example = "王五")
    @ExcelProperty("楼层名称")
    private String layerName;

    @Schema(description = "单元ID", example = "31381")
    @ExcelProperty("单元ID")
    private Long unitId;

    @Schema(description = "楼栋ID", example = "5016")
    @ExcelProperty("楼栋ID")
    private Long buildId;

    @Schema(description = "分区ID", example = "16890")
    @ExcelProperty("分区ID")
    private Long zoneId;

    @Schema(description = "项目ID", example = "20265")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "排序，越大越前", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序，越大越前")
    private Integer sort;

    @Schema(description = "数据状态(1使用，0隐藏)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("数据状态(1使用，0隐藏)")
    private Integer status;

    @Schema(description = "3D模型")
    @ExcelProperty("3D模型")
    private String threeDimensionalFile;

    @Schema(description = "3D模型物体ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26630")
    @ExcelProperty("3D模型物体ID")
    private String threeDimensionalId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "房屋列表")
    private List<RoomRespVO> RoomRespVO;

    @Schema(description = "楼层房间总面积")
    private BigDecimal rentalAreaAll;

    @Schema(description = "楼层下是否包含房间", example = "true")
    private Boolean haveRoom;

    @Schema(description = "楼栋名称")
    @ExcelProperty("楼栋ID")
    private String buildName;

    @Schema(description = "项目名称")
    private String villageName;
}