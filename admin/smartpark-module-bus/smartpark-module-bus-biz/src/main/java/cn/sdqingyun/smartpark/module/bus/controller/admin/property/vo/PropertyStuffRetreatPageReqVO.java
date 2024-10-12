package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 耗材业务退库分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyStuffRetreatPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "31583")
    private Long orgId;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    private String processNumber;

    @Schema(description = "退库人uid", example = "7917")
    private Long retreatUid;

    @Schema(description = "部门id", example = "14623")
    private Long departmentId;

    @Schema(description = "部门名称", example = "赵六")
    private String departmentName;

    @Schema(description = "入库仓库id", example = "12122")
    private Long depositoryId;

    @Schema(description = "退库时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] retreatDate;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", example = "2")
    private Short status;

    @Schema(description = "退库备注", example = "随便")
    private String remark;

    @Schema(description = "处理人uid", example = "5743")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "26278")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}