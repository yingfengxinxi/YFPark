package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 耗材业务处置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyStuffHandlePageReqVO extends PageParam {

    @Schema(description = "机构id", example = "9336")
    private Long orgId;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    private String processNumber;

    @Schema(description = "发起部门id", example = "30395")
    private Long departmentId;

    @Schema(description = "发起部门名称", example = "张三")
    private String departmentName;

    @Schema(description = "处置仓库id", example = "2738")
    private Long depositoryId;

    @Schema(description = "发起时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] launchTime;

    @Schema(description = "合计金额", example = "12235")
    private BigDecimal totalPrice;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", example = "2")
    private Short status;

    @Schema(description = "处置原因", example = "你说的对")
    private String remark;

    @Schema(description = "发起人uid", example = "4075")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "4792")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}