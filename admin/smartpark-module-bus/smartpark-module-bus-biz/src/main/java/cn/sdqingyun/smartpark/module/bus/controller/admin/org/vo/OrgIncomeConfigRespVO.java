package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 机构收支确认配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgIncomeConfigRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32201")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "确认天数,超过此天数不可取消确认", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("确认天数,超过此天数不可取消确认")
    private Integer lmtDay;

    @Schema(description = "附加信息")
    @ExcelProperty("附加信息")
    private String extra;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}