package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 位置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyLocationRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11084")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18199")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "位置编码")
    @ExcelProperty("位置编码")
    private String number;

    @Schema(description = "位置名称", example = "张三")
    @ExcelProperty("位置名称")
    private String name;

    @Schema(description = "位置级别字符串，逗号拼接上级id")
    @ExcelProperty("位置级别字符串，逗号拼接上级id")
    private String level;

    @Schema(description = "父级id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26703")
    @ExcelProperty("父级id")
    private Long parentId;

    @Schema(description = "参数")
    @ExcelProperty("参数")
    private String param;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "显示状态，2禁用 1显示，0隐藏", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("显示状态，2禁用 1显示，0隐藏")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "子节点")
    private List<PropertyLocationRespVO> children;

    @Schema(description = "父级名称")
    private String parentName;
}