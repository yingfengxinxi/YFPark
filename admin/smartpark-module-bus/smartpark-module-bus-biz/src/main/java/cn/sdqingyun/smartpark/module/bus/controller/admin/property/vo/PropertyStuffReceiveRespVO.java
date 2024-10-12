package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 耗材业务领用 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PropertyStuffReceiveRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9167")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28156")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "流程编号")
    @ExcelProperty("流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String processNumber;

    @Schema(description = "申领仓库id", example = "23869")
    @ExcelProperty("申领仓库id")
    private Long depositoryId;

    @Schema(description = "申请部门id", example = "510")
    @ExcelProperty("申请部门id")
    private Long departmentId;

    @Schema(description = "部门名称", example = "李四")
    @ExcelProperty("部门名称")
    private String departmentName;

    @Schema(description = "申请总数量")
    @ExcelProperty("申请总数量")
    private BigDecimal totalNum;

    @Schema(description = "关联单据类型", example = "2")
    @ExcelProperty("关联单据类型")
    private String relationType;

    @Schema(description = "关联单据编号;派发单据,支持多个")
    @ExcelProperty("关联单据编号;派发单据,支持多个")
    private String relationNumber;

    @Schema(description = "已派发的总数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("已派发的总数量")
    private BigDecimal handoutNum;

    @Schema(description = "申请时间")
    @ExcelProperty("申请时间")
    private LocalDateTime applyTime;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("单据状态;1=进行中;2=已打回;3=已撤销;4=已完结")
    private Short status;

    @Schema(description = "申请原因", example = "随便")
    @ExcelProperty("申请原因")
    private String remark;

    @Schema(description = "申请人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "6879")
    @ExcelProperty("申请人uid")
    private Long cuserUid;

    @Schema(description = "修改人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "13729")
    @ExcelProperty("修改人uid")
    private Long muserUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}