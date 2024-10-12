package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 税率规则配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgTaxRuleRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28295")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "费用类型", example = "1")
    @ExcelProperty("费用类型")
    private String costType;

    @Schema(description = "税收分类编码ID", example = "19620")
    private Long taxCodeId;

    @TableField(exist = false)
    @ExcelProperty("税收分类名称")
    private String taxCodeName;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "状态;1=开启;0=关闭", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态;1=开启;0=关闭")
    private String status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}