package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 耗材档案信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyStuffDepositoryRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14116")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7260")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "仓库编号")
    @ExcelProperty("仓库编号")
    private String number;

    @Schema(description = "仓库名称", example = "李四")
    @ExcelProperty("仓库名称")
    private String name;

    @Schema(description = "上级仓库id", requiredMode = Schema.RequiredMode.REQUIRED, example = "25605")
    @ExcelProperty("上级仓库id")
    private Long parentId;

    @Schema(description = "耗材仓库状态;1=启用,0=禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("耗材仓库状态;1=启用,0=禁用")
    private Short status;

    @Schema(description = "上下层级关系")
    @ExcelProperty("上下层级关系")
    private String level;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "序号")
    @ExcelProperty("序号")
    private Integer sort;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "3632")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "29032")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "父级名称")
    private String parentName;

    @Schema(description = "子节点")
    private List<PropertyStuffDepositoryRespVO> children;
}