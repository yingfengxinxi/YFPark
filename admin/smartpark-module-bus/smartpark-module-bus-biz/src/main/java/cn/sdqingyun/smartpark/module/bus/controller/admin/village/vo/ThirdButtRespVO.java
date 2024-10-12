package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 第三方数据对接（目前用于智慧社区系统，全功能版） Response VO")
@Data
@ExcelIgnoreUnannotated
public class ThirdButtRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28608")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "第三方业务类别", example = "2")
    @ExcelProperty("第三方业务类别")
    private String businessType;

    @Schema(description = "第三方业务ID", example = "424")
    @ExcelProperty("第三方业务ID")
    private String businessId;

    @Schema(description = "自有数据ID", example = "17673")
    @ExcelProperty("自有数据ID")
    private String selfId;

    @Schema(description = "数据原有返回格式")
    @ExcelProperty("数据原有返回格式")
    private String originalData;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}