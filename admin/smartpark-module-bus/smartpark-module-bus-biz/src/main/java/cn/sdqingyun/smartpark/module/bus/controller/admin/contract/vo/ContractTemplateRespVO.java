package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 机构合同模板配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ContractTemplateRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3761")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "7157")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "水印模板id", example = "14254")
    @ExcelProperty("水印模板id")
    private Long watermarkId;

    @Schema(description = "应用场景;普通合同(normal)/退租合同(retreat)等等")
    @ExcelProperty("应用场景;普通合同(normal)/退租合同(retreat)等等")
    private String scene;

    @Schema(description = "模板名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("模板名称")
    private String templateName;

    @Schema(description = "模板文件", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("模板文件")
    private String templatePath;

    @Schema(description = "模板主条款信息")
    @ExcelProperty("模板主条款信息")
    private String basicClause;

    @Schema(description = "模板合同编号规则")
    @ExcelProperty("模板合同编号规则")
    private String numberRule;

    @Schema(description = "滞纳金条款")
    @ExcelProperty("滞纳金条款")
    private String latefeeClause;

    @Schema(description = "综合条款")
    @ExcelProperty("综合条款")
    private String multipleClause;

    @Schema(description = "楼宇id,多个id使用逗号隔开")
    @ExcelProperty("楼宇id,多个id使用逗号隔开")
    private String relationBuilds;

    @Schema(description = "楼宇id,多个id使用逗号隔开")
    @ExcelProperty("楼宇id,多个id使用逗号隔开")
    @TableField(exist = false)
    private String relationBuildName;

    @Schema(description = "模板变量自定义表头集合")
    @ExcelProperty("模板变量自定义表头集合")
    private String tableFields;

    @Schema(description = "永久标识;1=永久", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("永久标识;1=永久")
    private String dateFlag;

    @Schema(description = "过期时间")
    @ExcelProperty("过期时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date timeoutTime;

    @Schema(description = "模板使用状态;1=启用;0=关闭", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("模板使用状态;1=启用;0=关闭")
    private String status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}