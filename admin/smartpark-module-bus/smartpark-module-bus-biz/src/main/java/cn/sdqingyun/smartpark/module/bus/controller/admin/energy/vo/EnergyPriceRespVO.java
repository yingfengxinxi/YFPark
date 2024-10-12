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

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 自定义价格标准 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyPriceRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "16338")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "设备表种类", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("设备表种类")
    private String type;

    @Schema(description = "机构ID", example = "5190")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24193")
    @ExcelProperty("项目ID")
    private Long villageId;
    private String villageName;

    @Schema(description = "楼宇ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "93")
    @ExcelProperty("楼宇ID")
    private Long buildId;
    private String buildName;

    @Schema(description = "名称", example = "赵六")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "生效时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date effectDate;

    @Schema(description = "是否阶梯价 1是 0否", requiredMode = Schema.RequiredMode.REQUIRED, example = "32696")
    @ExcelProperty("是否阶梯价 1是 0否")
    private String isStagePrice;

    @Schema(description = "倍率", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("倍率")
    private BigDecimal taxRate;

    @Schema(description = "单价标准，json存储", example = "2813")
    @ExcelProperty("单价标准，json存储")
    private String unitPrice;

    @Schema(description = "授予用户管理的项目和楼宇，json存储")
    @ExcelProperty("授予用户管理的项目和楼宇，json存储")
    private String builds;

    @Schema(description = "绑定房间信息，1,2,3")
    @ExcelProperty("绑定房间信息，1,2,3")
    private String roomIds;
    private String roomName;

    @Schema(description = "滞纳金比例")
    @ExcelProperty("滞纳金比例")
    private String ratio;

    @Schema(description = "起算天数")
    @ExcelProperty("起算天数")
    private String startDay;

    @Schema(description = "滞纳金上限")
    @ExcelProperty("滞纳金上限")
    private String toplimit;

    @Schema(description = "状态，1启动，0审核中，4禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态，1启动，0审核中，4禁用")
    private String status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}