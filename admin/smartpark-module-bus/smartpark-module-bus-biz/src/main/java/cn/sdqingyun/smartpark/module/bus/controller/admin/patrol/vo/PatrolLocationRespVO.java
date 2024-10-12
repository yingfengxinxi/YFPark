package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 位置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PatrolLocationRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26137")
    private Long id;

    @Schema(description = "机构ID", example = "25119")
    private Long orgId;

    @Schema(description = "位置编码")
    @ExcelProperty("位置编码")
    private String number;

    @Schema(description = "位置名称", example = "王五")
    @ExcelProperty("位置名称")
    private String name;

    @Schema(description = "位置级别字符串，逗号拼接上级id")
    private String level;

    @Schema(description = "位置级别字符串，斜杠拼接上级name")
    @ExcelProperty("位置级别")
    private String levelName;

    @Schema(description = "父级id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31662")
    private Long parentId;

    @TableField(exist = false)
    private String parentName;

    @Schema(description = "参数")
    private String param;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sort;

    @Schema(description = "显示状态，2禁用 1显示，0隐藏", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("显示状态，2禁用 1显示，0隐藏")
    private String status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("下级数据")
    @TableField(exist = false)
    private List<PatrolLocationRespVO> children;

}