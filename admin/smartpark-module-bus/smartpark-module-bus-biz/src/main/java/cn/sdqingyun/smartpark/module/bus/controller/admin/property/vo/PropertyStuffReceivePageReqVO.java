package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 耗材业务领用分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyStuffReceivePageReqVO extends PageParam {

    @Schema(description = "机构id", example = "28156")
    private Long orgId;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据编号")
    private String processNumber;

    @Schema(description = "申领仓库id", example = "23869")
    private Long depositoryId;

    @Schema(description = "申请部门id", example = "510")
    private Long departmentId;

    @Schema(description = "部门名称", example = "李四")
    private String departmentName;

    @Schema(description = "申请总数量")
    private BigDecimal totalNum;

    @Schema(description = "关联单据类型", example = "2")
    private String relationType;

    @Schema(description = "关联单据编号;派发单据,支持多个")
    private String relationNumber;

    @Schema(description = "已派发的总数量")
    private BigDecimal handoutNum;

    @Schema(description = "申请时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] applyTime;

    @Schema(description = "单据状态;1=进行中;2=已打回;3=已撤销;4=已完结", example = "1")
    private Short status;

    @Schema(description = "申请原因", example = "随便")
    private String remark;

    @Schema(description = "申请人uid", example = "6879")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "13729")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}