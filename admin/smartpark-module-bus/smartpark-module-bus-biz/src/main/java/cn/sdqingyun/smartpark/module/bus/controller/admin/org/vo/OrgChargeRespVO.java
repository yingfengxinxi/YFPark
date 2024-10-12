package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 收费标准 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgChargeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7332")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "标准名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("标准名称")
    private String name;

    @Schema(description = "计费模式：1-单价，2-单价*计量单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计费模式：1-单价，2-单价*计量单位")
    private String mode;

    @Schema(description = "收费金额、单价(根据 mode 来决定)", requiredMode = Schema.RequiredMode.REQUIRED, example = "14473")
    @ExcelProperty("收费金额、单价(根据 mode 来决定)")
    private BigDecimal unitPrice;

    @Schema(description = "标准生效时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("标准生效时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date validTime;

    @Schema(description = "标准失效时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("标准失效时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date expireTime;

    @Schema(description = "状态：0-已失效，1-生效中", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态：0-已失效，1-生效中")
    private String status;

    @Schema(description = "计量单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计量单位")
    private String unit;

    @Schema(description = "违约金起算天数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("违约金起算天数")
    private Integer afterDaysStartBill;

    @Schema(description = "违约金收取比例（天/%）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("违约金收取比例（天/%）")
    private Integer everyDayPre;

    @Schema(description = "违约金上限(元)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("违约金上限(元)")
    private BigDecimal upperLimitFee;

    @Schema(description = "绑定对象： 1-房间，2-车位", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("绑定对象： 1-房间，2-车位")
    private String bindObject;

    @Schema(description = "园区Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17371")
    @ExcelProperty("园区Id")
    private Long villageId;

    @Schema(description = "账单费用类型表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4364")
    @ExcelProperty("账单费用类型表id")
    private Long billCostTypeId;

    @Schema(description = "计费单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计费单位")
    private String unitCharging;

    @Schema(description = "计费规则(天单价换算、月划分)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计费规则(天单价换算、月划分)")
    private String dailyUnitPriceAndMonthRule;

    @Schema(description = "账单(应)付款时间-动作", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("账单(应)付款时间-动作")
    private String payDateAction;

    @Schema(description = "天数（日）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("天数（日）")
    private Integer payDateDays;

    @Schema(description = "账单(应)付款时间-方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("账单(应)付款时间-方式")
    private String payDateActionType;

    @Schema(description = "付款周期（N月一付）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("付款周期（N月一付）")
    private Integer payCycle;

    @Schema(description = "划分方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("划分方式")
    private String divideMethod;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}