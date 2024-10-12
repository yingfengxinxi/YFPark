package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 工单操作日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkOrderProposeLogPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "21499")
    private Long orgId;

    @Schema(description = "工单id", example = "4432")
    private Long workorderId;

    @Schema(description = "项目id", example = "29130")
    private Long villageId;

    @Schema(description = "楼宇id", example = "17682")
    private Long buildId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "来源标识")
    private String from;

    @Schema(description = "业务来源;1=租客端;2=员工端;")
    private String source;

    @Schema(description = "操作类型", example = "2")
    private String operateType;

    @Schema(description = "操作状态", example = "1")
    private String status;

    @Schema(description = "操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date operateTime;

    @Schema(description = "处理人uid", example = "1188")
    private Long uid;

    @Schema(description = "处理人名称", example = "李四")
    private String name;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "备注内容", example = "你说的对")
    private String remark;

    @Schema(description = "图例;支持多张")
    private String images;

    @Schema(description = "备用内容json")
    private String otherContent;

    @Schema(description = "资产单据信息json")
    private String stuffData;

    @Schema(description = "评价等级;1-5星")
    private Integer appraiseLevel;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}