package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目类型字典 Response VO")
@Data
@ExcelIgnoreUnannotated
public class VillageTypeDictRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30744")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "类型别名", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("类型别名")
    private String typeAlias;

    @Schema(description = "原字", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("原字")
    private String words;

    @Schema(description = "中文")
    @ExcelProperty("中文")
    private String zhCn;

    @Schema(description = "繁体中文（中国香港）")
    @ExcelProperty("繁体中文（中国香港）")
    private String zhHk;

    @Schema(description = "繁体中文（中国台湾）")
    @ExcelProperty("繁体中文（中国台湾）")
    private String zhTw;

    @Schema(description = "英文")
    @ExcelProperty("英文")
    private String en;

    @Schema(description = "日本语")
    @ExcelProperty("日本语")
    private String ja;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}