package cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 工单分类子类信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CategorySubcatRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18040")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "2482")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识")
    @ExcelProperty("应用标识")
    private String application;

    @Schema(description = "子类名称", example = "赵六")
    @ExcelProperty("子类名称")
    private String name;

    @Schema(description = "工单大类id", example = "25667")
    @ExcelProperty("工单大类id")
    private Long categoryId;

    @Schema(description = "子类绑定的岗位信息json")
    private String stationJson;
    @ExcelProperty("子类绑定的岗位信息")
    private String stationName;

    @Schema(description = "部门id", example = "31943")
    private Long departmentId;

    @ExcelProperty("部门名称")
    private String departmentName;

    @Schema(description = "标签id集合")
    @ExcelProperty("标签id集合")
    private String labelIds;

    @Schema(description = "排序值", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序值")
    private Integer sort;

    @Schema(description = "提交工单面向类型;1=所有人,2=租客,3=工作人员", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("提交工单面向类型;1=所有人,2=租客,3=工作人员")
    private String submitType;

    @Schema(description = "启用状态;0=否;1=是", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("启用状态;0=否;1=是")
    private String status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "标签数量")
    private Long tagNumber;

    @Schema(description = "累计工单数量")
    private Integer orderNumber;

    @Schema(description = "满意度")
    private String satisfactionLevel;

}