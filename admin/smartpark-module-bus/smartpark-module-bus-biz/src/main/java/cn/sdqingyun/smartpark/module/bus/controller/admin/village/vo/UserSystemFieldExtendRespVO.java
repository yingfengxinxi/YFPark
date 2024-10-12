package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户扩展信息自定义系统设置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class UserSystemFieldExtendRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9540")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "字段名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("字段名称")
    private String fieldName;

    @Schema(description = "表单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("表单名称")
    private String inputName;

    @Schema(description = "表单名称自定义", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("表单名称自定义")
    private String inputNameCus;

    @Schema(description = "输入框类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("输入框类型")
    private String inputType;

    @Schema(description = "表单默认值 ")
    @ExcelProperty("表单默认值 ")
    private String defaultInputValue;

    @Schema(description = "表单可选值 ")
    @ExcelProperty("表单可选值 ")
    private String defaultFieldValue;

    @Schema(description = "是否启用，1-启用,0-禁用", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否启用，1-启用,0-禁用")
    private Integer isEnable;

    @Schema(description = "是否必填 1-必填", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否必填 1-必填")
    private Integer isRequired;

    @Schema(description = "是否允许修改表单名称，1允许修改，0不允许", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否允许修改表单名称，1允许修改，0不允许")
    private Integer isAllowModifie;

    @Schema(description = "JSON 字段集合")
    @ExcelProperty("JSON 字段集合")
    private String jsonFields;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}