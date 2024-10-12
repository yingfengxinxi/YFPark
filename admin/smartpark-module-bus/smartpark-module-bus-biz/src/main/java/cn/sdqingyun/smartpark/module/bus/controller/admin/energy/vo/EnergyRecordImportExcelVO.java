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
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

/**
 * @Author lvzy
 * @Date 2024/10/7 21:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class EnergyRecordImportExcelVO {

    @Schema(description = "表名称", example = "李四")
    @ExcelProperty("表名称")
    @ContentFontStyle(color = 10)
    private String name;


    @Schema(description = "表具编号")
    @ExcelProperty("表具编号")
    @ContentFontStyle(color = 10)
    private String number;

    @Schema(description = "表种类")
    @ExcelProperty("表种类")
    @ContentFontStyle(color = 10)
    private String type;


    @Schema(description = "本次抄表时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    @ExcelProperty("本次抄表时间")
    @ContentFontStyle(color = 10)
    private String thisTime;

    @Schema(description = "本次读数")
    @ExcelProperty("本次读数")
    @ContentFontStyle(color = 10)
    private String thisNumber;


    @Schema(description = "导入结果说明")
    @ExcelProperty("导入结果说明")
    @ContentFontStyle(color = 10)
    private String importResult;

}
