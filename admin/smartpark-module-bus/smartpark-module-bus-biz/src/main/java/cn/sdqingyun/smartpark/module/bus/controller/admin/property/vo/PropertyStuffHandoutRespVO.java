package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyStuffProcessDO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 耗材业务派发 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyStuffHandoutRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20897")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9661")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "流程编号")
    @ExcelProperty("流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String processNumber;

    @Schema(description = "领用人uid", example = "16733")
    @ExcelProperty("领用人uid")
    private Long receiveUid;

    @Schema(description = "领用部门id", example = "8784")
    @ExcelProperty("领用部门id")
    private Long departmentId;

    @Schema(description = "领用部门名称", example = "王五")
    @ExcelProperty("领用部门名称")
    private String departmentName;

    @Schema(description = "出库所属仓库id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10242")
    @ExcelProperty("出库所属仓库id")
    private Long depositoryId;

    @Schema(description = "派发时间")
    @ExcelProperty("派发时间")
    private LocalDateTime handoutTime;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("单据状态;1=进行中;2=已打回;3=已撤销;4=已完结")
    private Short status;

    @Schema(description = "派发备注", example = "随便")
    @ExcelProperty("派发备注")
    private String remark;

    @Schema(description = "处理人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "9193")
    @ExcelProperty("处理人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "13756")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "领用人")
    private String receiveName;

    @Schema(description = "出库所属仓库")
    private String depositoryName;
    @Schema(description = "物料即时库存详情")
    private List<PropertyStuffProcessDO> stuff;
}