package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产归还分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyRevertPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "5112")
    private Long orgId;

    @Schema(description = "项目id", example = "25094")
    private Long villageId;

    @Schema(description = "楼宇id", example = "168")
    private Long buildId;

    @Schema(description = "房间id", example = "18820")
    private Long roomId;

    @Schema(description = "资产id集合json")
    private String propertyIds;

    @Schema(description = "单据编号")
    private String number;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "单据状态", example = "2")
    private Integer status;

    @Schema(description = "归还处理人", example = "28958")
    private Long revertUid;

    @Schema(description = "归还后使用部门", example = "26702")
    private Long departmentId;

    @Schema(description = "归还时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] revertTime;

    @Schema(description = "处理时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] operateTime;

    @Schema(description = "归还备注", example = "随便")
    private String remark;

    @Schema(description = "操作人uid", example = "16543")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "5768")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}