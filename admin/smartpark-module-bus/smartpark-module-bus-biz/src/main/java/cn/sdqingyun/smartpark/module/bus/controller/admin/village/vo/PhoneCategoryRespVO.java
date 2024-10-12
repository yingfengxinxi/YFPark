package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.VillagePhoneDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目电话类型 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PhoneCategoryRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15283")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1959")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16209")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("分类名称")
    private String catName;

    @Schema(description = "排序值", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序值")
    private Integer sort;

    @Schema(description = "状态（1正常，0隐藏）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态（1正常，0隐藏）")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "电话列表")
    private List<VillagePhoneRespVO> villagePhoneDOS;
}