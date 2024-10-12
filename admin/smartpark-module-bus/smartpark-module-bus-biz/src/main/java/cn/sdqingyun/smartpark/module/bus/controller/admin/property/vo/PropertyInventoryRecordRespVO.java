package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产盘点记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyInventoryRecordRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23915")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7485")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "盘点清单id", example = "2469")
    @ExcelProperty("盘点清单id")
    private Long inventoryId;

    @Schema(description = "资产id", example = "576")
    @ExcelProperty("资产id")
    private Long propertyId;

    @Schema(description = "资产分类id", example = "17141")
    @ExcelProperty("资产分类id")
    private Long typeId;

    @Schema(description = "0未盘 1已盘 2异常 3盘盈 4盘亏 5已结束", example = "2")
    @ExcelProperty("0未盘 1已盘 2异常 3盘盈 4盘亏 5已结束")
    private Integer status;

    @Schema(description = "资产状态", example = "2")
    @ExcelProperty("资产状态")
    private Integer propertyStatus;

    @Schema(description = "是否在盘点范围之内")
    @ExcelProperty("是否在盘点范围之内")
    private Integer isRange;

    @Schema(description = "是否修改资产信息 0否 1是", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否修改资产信息 0否 1是")
    private Integer isUpdate;

    @Schema(description = "盘点备注", example = "你说的对")
    @ExcelProperty("盘点备注")
    private String remark;

    @Schema(description = "盘点图片")
    @ExcelProperty("盘点图片")
    private String image;

    @Schema(description = "盘点标签")
    @ExcelProperty("盘点标签")
    private String tag;

    @Schema(description = "盘点时间")
    @ExcelProperty("盘点时间")
    private LocalDateTime inventoryTime;

    @Schema(description = "盘点人", example = "19108")
    @ExcelProperty("盘点人")
    private String inventoryUid;

    @Schema(description = "当前位置", example = "11648")
    @ExcelProperty("当前位置")
    private Long positionId;

    @Schema(description = "处理人", example = "5098")
    @ExcelProperty("处理人")
    private Long handleUid;

    @Schema(description = "资产所属部门id", example = "26725")
    @ExcelProperty("资产所属部门id")
    private Long departmentId;

    @Schema(description = "资产盘点信息")
    @ExcelProperty("资产盘点信息")
    private String propertyInfo;

    @Schema(description = "资产名称", example = "李四")
    @ExcelProperty("资产名称")
    private String name;

    @Schema(description = "资产编码")
    @ExcelProperty("资产编码")
    private String propertyNumber;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}