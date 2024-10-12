package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 业务流程单据关联资产 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyProcessDataRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14735")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "11914")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "流程编号")
    @ExcelProperty("流程编号")
    private String processCode;

    @Schema(description = "流程单据编号")
    @ExcelProperty("流程单据编号")
    private String processNumber;

    @Schema(description = "资产id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12453")
    @ExcelProperty("资产id")
    private Long propertyId;

    @Schema(description = "资产编号")
    @ExcelProperty("资产编号")
    private String propertyNumber;

    @Schema(description = "资产分类", example = "1")
    @ExcelProperty("资产分类")
    private Integer type;

    @Schema(description = "资产名称", example = "张三")
    @ExcelProperty("资产名称")
    private String name;

    @Schema(description = "资产状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("资产状态")
    private Integer status;

    @Schema(description = "所在位置配置信息id", example = "24517")
    @ExcelProperty("所在位置配置信息id")
    private Long positionId;

    @Schema(description = "品牌")
    @ExcelProperty("品牌")
    private String brand;

    @Schema(description = "型号", example = "赵六")
    @ExcelProperty("型号")
    private String modelName;

    @Schema(description = "目标房源")
    @ExcelProperty("目标房源")
    private String buildBind;

    @Schema(description = "资产保养上传的文件")
    @ExcelProperty("资产保养上传的文件")
    private String maintainFile;

    @Schema(description = "资产保养上传的金额", example = "17149")
    @ExcelProperty("资产保养上传的金额")
    private BigDecimal maintainPrice;

    @Schema(description = "操作人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "23598")
    @ExcelProperty("操作人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "14025")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}