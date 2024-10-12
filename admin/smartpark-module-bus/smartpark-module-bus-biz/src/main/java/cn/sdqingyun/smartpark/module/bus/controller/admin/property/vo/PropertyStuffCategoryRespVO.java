package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 资产耗材分类 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyStuffCategoryRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30313")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17543")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "分类编码")
    @ExcelProperty("分类编码")
    private String number;

    @Schema(description = "分类名称", example = "李四")
    @ExcelProperty("分类名称")
    private String name;

    @Schema(description = "父级id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23274")
    @ExcelProperty("父级id")
    private Long parentId;

    @Schema(description = "参数")
    @ExcelProperty("参数")
    private String param;

    @Schema(description = "上下层级关系")
    @ExcelProperty("上下层级关系")
    private String level;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "显示状态，2禁用 1显示，0隐藏", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("显示状态，2禁用 1显示，0隐藏")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "父级名称")
    private String parentName;

    @Schema(description = "子节点")
    private List<PropertyStuffCategoryRespVO> children;

}