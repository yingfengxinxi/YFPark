package cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo;

import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategorySubcatDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 工单分类配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CategoryRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15398")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "10955")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识")
    @ExcelProperty("应用标识")
    private String application;

    @Schema(description = "大类名称", example = "芋艿")
    @ExcelProperty("大类名称")
    private String name;

    @Schema(description = "运作模式;1=派单+抢单2=派单", example = "1")
    @ExcelProperty("运作模式;1=派单+抢单2=派单")
    private String type;

    @Schema(description = "完成时限", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("完成时限")
    private Integer overHour;

    @Schema(description = "是否允许转单;1=开启,0=关闭", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否允许转单;1=开启,0=关闭")
    private String hasChange;

    @Schema(description = "排序值")
    @ExcelProperty("排序值")
    private Integer sort;

    @Schema(description = "应用楼宇json")
    @ExcelProperty("应用楼宇json")
    private String buildBind;

    @Schema(description = "工单子类id集合")
    @ExcelProperty("工单子类id集合")
    private String subcatIds;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "子类数量")
    public Long subcatNumber;

    @Schema(description = "工单数量")
    public Long orderNumber;

    @Schema(description = "子类集合")
    List<CategorySubcatDO> categorySubcatList;
}