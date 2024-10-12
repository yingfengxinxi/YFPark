package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产派发/退库 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyHandoutRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4478")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12362")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "项目id", example = "30963")
    @ExcelProperty("项目id")
    private Long villageId;

    @Schema(description = "楼宇id", example = "25748")
    @ExcelProperty("楼宇id")
    private Long buildId;

    @Schema(description = "房间id", example = "4303")
    @ExcelProperty("房间id")
    private Long roomId;

    @Schema(description = "资产id集合json")
    @ExcelProperty("资产id集合json")
    private String propertyIds;

    @Schema(description = "流程编号")
    @ExcelProperty("流程编号")
    private String processCode;

    @Schema(description = "数据类型;1=派发;2=退库", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("数据类型;1=派发;2=退库")
    private Short type;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String number;

    @Schema(description = "单据状态", example = "1")
    @ExcelProperty("单据状态")
    private Integer status;

    @Schema(description = "所选部门id", example = "13761")
    @ExcelProperty("所选部门id")
    private Long departmentId;

    @Schema(description = "领用人", example = "29669")
    @ExcelProperty("领用人")
    private Long receiveUid;

    @Schema(description = "派发日期")
    @ExcelProperty("派发日期")
    private LocalDateTime handoutTime;

    @Schema(description = "退库日期")
    @ExcelProperty("退库日期")
    private LocalDateTime returnTime;

    @Schema(description = "处理人", requiredMode = Schema.RequiredMode.REQUIRED, example = "31773")
    @ExcelProperty("处理人")
    private Long operateUid;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private LocalDateTime operateTime;

    @Schema(description = "处理备注", example = "你说的对")
    @ExcelProperty("处理备注")
    private String remark;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "5575")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "12201")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "所选部门名称")
    private String departmentName;

    @Schema(description = "领用人姓名")
    private String receiveName;
}