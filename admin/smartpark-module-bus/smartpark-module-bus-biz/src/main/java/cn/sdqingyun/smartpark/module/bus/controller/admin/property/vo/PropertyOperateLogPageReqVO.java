package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产操作日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyOperateLogPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "7070")
    private Long orgId;

    @Schema(description = "操作人uid", example = "12977")
    private Long operateUid;

    @Schema(description = "资产id信息json")
    private String propertyJson;

    @Schema(description = "流程编号")
    private String processCode;

    @Schema(description = "操作类型", example = "1")
    private String operateType;

    @Schema(description = "日志类型", example = "1")
    private String logType;

    @Schema(description = "操作人")
    private String operateUser;

    @Schema(description = "操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] operateTime;

    @Schema(description = "操作内容")
    private String operateContent;

    @Schema(description = "其他")
    private String otherContent;

    @Schema(description = "操作权限")
    private String operate;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}