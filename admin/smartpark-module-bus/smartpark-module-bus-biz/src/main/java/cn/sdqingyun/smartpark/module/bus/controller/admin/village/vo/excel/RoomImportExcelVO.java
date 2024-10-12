package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 房间 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class RoomImportExcelVO {

    @Schema(description = "项目名称", example = "智慧园区")
    @ExcelProperty("项目名称")
    @ContentFontStyle(color = 10)
    private String name;

    @Schema(description = "楼栋名称", example = "一栋")
    @ExcelProperty("楼栋名称")
    @ContentFontStyle(color = 10)
    private String buildName;

    @Schema(description = "楼层名称", example = "三层")
    @ExcelProperty("楼层名称")
    @ContentFontStyle(color = 10)
    private String layerName;

    @Schema(description = "房间编号")
    @ExcelProperty("房间编号")
    @ContentFontStyle(color = 10)
    private String roomNumber;

    @Schema(description = "房间名称", example = "301")
    @ExcelProperty("房间名称")
    @ContentFontStyle(color = 10)
    private String roomName;

    @Schema(description = "计租面积")
    @ExcelProperty("计租面积")
    @ContentFontStyle(color = 10)
    private String rentalArea;

    @Schema(description = "计费面积")
    @ExcelProperty("计费面积")
    @ContentFontStyle(color = 10)
    private String chargingArea;

    @Schema(description = "建筑面积")
    @ExcelProperty("建筑面积")
    @ContentFontStyle(color = 10)
    private String buildArea;

    @Schema(description = "标准层高")
    @ExcelProperty("标准层高")
    @ContentFontStyle(color = 10)
    private String floorHeight;

    @Schema(description = "预租单价", example = "17991")
    @ExcelProperty("预租单价")
    @ContentFontStyle(color = 10)
    private String preUnitPrice;

    @Schema(description = "最低价格")
    @ExcelProperty("最低价格")
    @ContentFontStyle(color = 10)
    private String preUnitPriceMin;

    @Schema(description = "价格单位")
    @ExcelProperty("价格单位")
    @ContentFontStyle(color = 10)
    private String priceUnit;

    @Schema(description = "底价单位")
    @ExcelProperty("底价单位")
    @ContentFontStyle(color = 10)
    private String priceUnitMin;

    @Schema(description = "房源备案号")
    @ExcelProperty("房源备案号")
    @ContentFontStyle(color = 10)
    private String recordNo;

    @Schema(description = "装修情况")
    @ExcelProperty("装修情况")
    @ContentFontStyle(color = 10)
    private String decoration;

}
