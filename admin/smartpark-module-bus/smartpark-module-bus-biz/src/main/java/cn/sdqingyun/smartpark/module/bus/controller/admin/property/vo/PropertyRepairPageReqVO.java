package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产维修分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyRepairPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "11403")
    private Long orgId;

    @Schema(description = "资产id")
    private String propertyIds;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据状态", example = "1")
    private Integer status;

    @Schema(description = "报修人uid", example = "15497")
    private Long repairUid;

    @Schema(description = "报修部门id", example = "9700")
    private Long repairDepartmentId;

    @Schema(description = "报修时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] repairTime;

    @Schema(description = "报修原因", example = "不好")
    private String repairReason;

    @Schema(description = "处理人", example = "12563")
    private Long operateUid;

    @Schema(description = "预计维修金额", example = "8161")
    private String expectRepairPrice;

    @Schema(description = "处理时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] operateTime;

    @Schema(description = "维修内容")
    private String repairContent;

    @Schema(description = "工单信息")
    private String workorderInfo;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "操作人uid", example = "11957")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "4505")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}