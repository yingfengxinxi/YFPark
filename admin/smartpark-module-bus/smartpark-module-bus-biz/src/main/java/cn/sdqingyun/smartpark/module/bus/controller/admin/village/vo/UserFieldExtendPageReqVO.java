package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 用户扩展信息自定义系统设置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserFieldExtendPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "7430")
    private Long orgId;

    @Schema(description = "项目id", example = "6702")
    private Long villageId;

    @Schema(description = "关联 village_user_system_field_extend id", example = "16431")
    private Long sysFieldId;

    @Schema(description = "字段名称", example = "芋艿")
    private String fieldName;

    @Schema(description = "表单名称", example = "张三")
    private String inputName;

    @Schema(description = "表单名称自定义")
    private String inputNameCus;

    @Schema(description = "输入框类型", example = "1")
    private String inputType;

    @Schema(description = "表单默认值 ")
    private String defaultInputValue;

    @Schema(description = "表单默认值 ")
    private String defaultFieldValue;

    @Schema(description = "是否启用，1-启用,0-禁用")
    private Integer isEnable;

    @Schema(description = "是否必填 1-必填")
    private Integer isRequired;

    @Schema(description = "是否允许修改表单名称，1允许，0不允许")
    private Integer isAllowModifie;

    @Schema(description = "JSON 字段集合")
    private String jsonFields;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}