package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 表管理 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class EnergyImportExcelVO {


    @Schema(description = "项目", example = "项目名称")
    @ExcelProperty("项目")
    @ContentFontStyle(color = 10)
    private String villageName;

    @Schema(description = "楼宇", example = "7880")
    @ExcelProperty("楼宇")
    @ContentFontStyle(color = 10)
    private String buildName;

    @Schema(description = "房号")
    @ExcelProperty("房号")
    @ContentFontStyle(color = 10)
    private String roomName;



    @Schema(description = "表种类")
    @ExcelProperty("表种类")
    @ContentFontStyle(color = 10)
    private String type;

    @Schema(description = "表名称", example = "李四")
    @ExcelProperty("表名称")
    @ContentFontStyle(color = 10)
    private String name;

    @Schema(description = "表具编号")
    @ExcelProperty("表具编号")
    @ContentFontStyle(color = 10)
    private String number;

    @Schema(description = "倍率")
    @ExcelProperty("倍率")
    @ContentFontStyle(color = 10)
    private String magnification;

    @Schema(description = "最大读数")
    @ExcelProperty("最大读数")
    @ContentFontStyle(color = 10)
    private String maxReading;

    @Schema(description = "抄录时间")
    @ExcelProperty("抄录时间")
    @ContentFontStyle(color = 10)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private String meterTime;


    @Schema(description = "原始读数")
    @ExcelProperty("原始读数")
    @ContentFontStyle(color = 10)
    private String originalReading;


    @Schema(description = "用途")
    @ExcelProperty("用途")
    @ContentFontStyle(color = 10)
    private String purpose;

    @Schema(description = "公摊方式", example = "2")
    @ExcelProperty("公摊方式")
    @ContentFontStyle(color = 10)
    private String publicType;

    @Schema(description = "智能设备类型", example = "2")
    @ExcelProperty("智能设备类型")
    @ContentFontStyle(color = 10)
    private String deviceType;

    @Schema(description = "设备序列号", example = "2")
    @ExcelProperty("设备序列号")
    @ContentFontStyle(color = 10)
    private String deviceSerial;

    @Schema(description = "付费类型", example = "1")
    @ExcelProperty("付费类型")
    @ContentFontStyle(color = 10)
    private String paymentType;

    @Schema(description = "导入结果说明")
    @ExcelProperty("导入结果说明")
    @ContentFontStyle(color = 10)
    private String importResult;

}
