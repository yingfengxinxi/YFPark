package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 资产盘点清单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyInventoryListRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26642")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30094")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String number;

    @Schema(description = "盘点清单名称", example = "芋艿")
    @ExcelProperty("盘点清单名称")
    private String inventoryName;

    @Schema(description = "盘点清单状态 0 盘点中 1盘点审批中 2已完结", example = "2")
    @ExcelProperty("盘点清单状态 0 盘点中 1盘点审批中 2已完结")
    private Integer inventoryStatus;

    @Schema(description = "流程编号")
    @ExcelProperty("流程编号")
    private String processCode;

    @Schema(description = "盘点人", example = "27867")
    @ExcelProperty("盘点人")
    private String inventoryUid;

    @Schema(description = "资产限制字段")
    @ExcelProperty("资产限制字段")
    private String restrictField;

    @Schema(description = "盘点范围（使用部门）")
    @ExcelProperty("盘点范围（使用部门）")
    private String departmentIds;

    @Schema(description = "盘点范围（资产分类）")
    @ExcelProperty("盘点范围（资产分类）")
    private String typeIds;

    @Schema(description = "盘点范围（资产位置）")
    @ExcelProperty("盘点范围（资产位置）")
    private String positionIds;

    @Schema(description = "资产状态", example = "2")
    @ExcelProperty("资产状态")
    private String propertyStatus;

    @Schema(description = "主付状态", example = "2")
    @ExcelProperty("主付状态")
    private Integer initiativePayStatus;

    @Schema(description = "管理员id", example = "29507")
    @ExcelProperty("管理员id")
    private String adminId;

    @Schema(description = "所属公司")
    @ExcelProperty("所属公司")
    private Long company;

    @Schema(description = "购置方式", example = "1")
    @ExcelProperty("购置方式")
    private String purchaseType;

    @Schema(description = "仅扫码盘点的分类", example = "1")
    @ExcelProperty("仅扫码盘点的分类")
    private String codeInventoryType;

    @Schema(description = "不可批量盘点的分类", example = "2")
    @ExcelProperty("不可批量盘点的分类")
    private String notbreachInventoryType;

    @Schema(description = "ntake_pic:仅上传拍照 ntask_pic_after:在原有基础在补充", example = "1")
    @ExcelProperty("ntake_pic:仅上传拍照 ntask_pic_after:在原有基础在补充")
    private String uploadType;

    @Schema(description = "是否批量分配 1是0否")
    @ExcelProperty("是否批量分配 1是0否")
    private Integer isBatch;

    @Schema(description = "单个分配 分配范围")
    @ExcelProperty("单个分配 分配范围")
    private String distributionRange;

    @Schema(description = "额外数据、部门和分类的回显")
    @ExcelProperty("额外数据、部门和分类的回显")
    private String exterData;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}