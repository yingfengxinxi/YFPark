package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产处置单据记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyHandlePageReqVO extends PageParam {

    @Schema(description = "机构id", example = "8492")
    private Long orgId;

    @Schema(description = "发起部门id", example = "10382")
    private Long departmentId;

    @Schema(description = "单据编号")
    private String processNumber;

    @Schema(description = "单据状态;1=进行中", example = "2")
    private Integer status;

    @Schema(description = "处置金额")
    private BigDecimal handleAmount;

    @Schema(description = "处置费用")
    private BigDecimal handleExpenses;

    @Schema(description = "处置类型", example = "1")
    private Integer handleType;

    @Schema(description = "处置原因", example = "你说的对")
    private String remark;

    @Schema(description = "发起时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] applyTime;

    @Schema(description = "操作人uid", example = "16577")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "25895")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}