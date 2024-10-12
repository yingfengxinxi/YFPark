package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 自定义关联分总 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyBindRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "29752")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "类型 1分表 2总表", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("类型 1分表 2总表")
    private String type;

    @Schema(description = "设备表种类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("设备表种类")
    private Long meterType;

    @Schema(description = "用途类型 1分表 2总表 3公摊表", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("用途类型 1分表 2总表 3公摊表")
    private String purposeType;

    @Schema(description = "机构ID", example = "4286")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10718")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "楼宇ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18372")
    @ExcelProperty("楼宇ID")
    private Long buildId;

    @Schema(description = "自身自定义表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9240")
    @ExcelProperty("自身自定义表ID")
    private Long energyId;

    @Schema(description = "关联自定义表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30103")
    @ExcelProperty("关联自定义表ID")
    private Long parentEnergyId;

    @Schema(description = "表名称", example = "芋艿")
    @ExcelProperty("表名称")
    private String name;

    @Schema(description = "绑定表数据集合，json存储")
    @ExcelProperty("绑定表数据集合，json存储")
    private String binds;

    @Schema(description = "扩展字段 类型为1-分表-分摊比例,类型为2-总表-电表用量")
    @ExcelProperty("扩展字段 类型为1-分表-分摊比例,类型为2-总表-电表用量")
    private String extendContent;

    @Schema(description = "最近抄录时间")
    @ExcelProperty("最近抄录时间")
    private String latelyMeterTime;

    @Schema(description = "状态，1启动，0审核中，4禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态，1启动，0审核中，4禁用")
    private String status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}