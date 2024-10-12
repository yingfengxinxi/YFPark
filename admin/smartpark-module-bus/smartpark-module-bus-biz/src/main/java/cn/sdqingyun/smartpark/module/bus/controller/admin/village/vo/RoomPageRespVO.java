package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 招商中心装修页 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RoomPageRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9729")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "32210")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "内容信息json")
    @ExcelProperty("内容信息json")
    private String content;

    @Schema(description = "最近修改的时间")
    @ExcelProperty("最近修改的时间")
    private LocalDateTime lastTime;

    @Schema(description = "来源 0 pc端 1 移动端", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("来源 0 pc端 1 移动端")
    private Integer isMobile;

    @Schema(description = "启用状态;0=关闭;1=开启", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("启用状态;0=关闭;1=开启")
    private Integer status;

    @Schema(description = "创建人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "25171")
    @ExcelProperty("创建人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "22910")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}