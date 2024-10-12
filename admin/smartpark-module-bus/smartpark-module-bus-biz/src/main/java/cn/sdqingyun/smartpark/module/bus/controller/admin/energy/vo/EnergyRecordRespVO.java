package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 自定义抄表记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyRecordRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28315")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "类型 1分表 2总表 3公摊表", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("类型 1分表 2总表 3公摊表")
    private String type;

    @Schema(description = "设备表种类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("设备表种类")
    private Long meterType;

    @Schema(description = "机构ID", example = "16881")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21680")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "楼宇ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8264")
    @ExcelProperty("楼宇ID")
    private Long buildId;

    @Schema(description = "关联自定义表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20334")
    @ExcelProperty("关联自定义表ID")
    private Long energyId;

    @Schema(description = "关联自定义价格标准ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25565")
    @ExcelProperty("关联自定义价格标准ID")
    private Long energyPriceId;

    @Schema(description = "关联抄表任务ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22674")
    @ExcelProperty("关联抄表任务ID")
    private Long energyTaskId;

    @Schema(description = "抄表负责人", requiredMode = Schema.RequiredMode.REQUIRED, example = "23531")
    @ExcelProperty("抄表负责人")
    private Long leadUid;

    @Schema(description = "上次抄表时间")
    @ExcelProperty("上次抄表时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date lastTime;

    @Schema(description = "上次读数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("上次读数")
    private BigDecimal lastNumber;

    @Schema(description = "上次用量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("上次用量")
    private BigDecimal lastUse;

    @Schema(description = "本次抄表时间")
    @ExcelProperty("本次抄表时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date thisTime;

    @Schema(description = "本次读数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("本次读数")
    private BigDecimal thisNumber;

    @Schema(description = "本次用量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("本次用量")
    private BigDecimal thisUse;

    @Schema(description = "抄表图片")
    @ExcelProperty("抄表图片")
    private String image;

    @Schema(description = "状态，1启动，0审核中，4禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态，1启动，0审核中，4禁用")
    private String status;

    @Schema(description = "来源 0 pc端 1 移动端", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("来源 0 pc端 1 移动端")
    private String isMobile;

    @Schema(description = "是否确认 0未确认 1已确认", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否确认 0未确认 1已确认")
    private String isConfirm;

    @Schema(description = "自动抄表 0=否;1=是", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("自动抄表 0=否;1=是")
    private String isAuto;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;


    /**
     * 创建者，目前使用 SysUser 的 id 编号
     * <p>
     * 使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
     */
    @Schema(description = "负责人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String creator;

    @Schema(description = "是否可以修改数据true可以修改false不可以修改", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isEdit;


    @Schema(description = "是否可以删除数据true可以修改false不可以修改", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isDel;
}