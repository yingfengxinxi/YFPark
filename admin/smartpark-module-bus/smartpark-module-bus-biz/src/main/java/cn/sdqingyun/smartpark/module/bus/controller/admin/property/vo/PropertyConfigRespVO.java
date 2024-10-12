package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产配置信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyConfigRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14513")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", example = "922")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "绑定的项目楼宇信息json")
    @ExcelProperty("绑定的项目楼宇信息json")
    private String buildBind;

    @Schema(description = "资产编码规则json;")
    @ExcelProperty("资产编码规则json;")
    private String numberRule;

    @Schema(description = "附属信息json;")
    @ExcelProperty("附属信息json;")
    private String extra;

    @Schema(description = "创建人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "23747")
    @ExcelProperty("创建人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "20854")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}