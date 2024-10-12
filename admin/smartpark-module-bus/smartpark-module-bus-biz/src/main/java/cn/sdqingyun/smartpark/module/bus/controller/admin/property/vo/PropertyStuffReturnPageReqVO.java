package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 耗材业务耗材退还分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyStuffReturnPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "16224")
    private Long orgId;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    private String processNumber;

    @Schema(description = "关联单据类型", example = "1")
    private String relationType;

    @Schema(description = "关联单据编号")
    private String relationNumber;

    @Schema(description = "申请部门id", example = "5775")
    private Long departmentId;

    @Schema(description = "申请部门", example = "芋艿")
    private String departmentName;

    @Schema(description = "归还仓库id", example = "5855")
    private Long depositoryId;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", example = "1")
    private Short status;

    @Schema(description = "归还原因", example = "你说的对")
    private String remark;

    @Schema(description = "申请人uid", example = "12869")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "8393")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}