package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 机构业务备注 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgRemarkRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "8170")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "8767")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "业务id;合同id/账单id/等等", example = "25190")
    @ExcelProperty("业务id;合同id/账单id/等等")
    private Long businessId;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "业务类型1账单备注", example = "2")
    @ExcelProperty("业务类型1账单备注")
    private String type;

    @Schema(description = "备注人", example = "2")
    @ExcelProperty("备注人")
    private String creator;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}