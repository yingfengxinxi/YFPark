package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 应用巡检记录新增/修改 Request VO")
@Data
public class PatrolRecordEquipmentSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25714")
    private Long id;

    @Schema(description = "机构ID", example = "24980")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "巡检计划id", example = "29510")
    private Long planId;

    @Schema(description = "巡检任务id", example = "23591")
    private Long taskId;

    @Schema(description = "是否应用签到")
    private String isSigned;

    @Schema(description = "签到状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String signStatus;

    @Schema(description = "是否应用nfc签到")
    private String isNfc;

    @Schema(description = "巡检点id", example = "9561")
    private Long patrolPositionId;

    @Schema(description = "巡检点名称")
    private String address;

    @Schema(description = "巡检人uid", example = "21596")
    private Long uid;

    @Schema(description = "巡检人名称", example = "芋艿")
    private String name;

    @Schema(description = "巡检点位置信息")
    private String position;

    @Schema(description = "工单发起人uid", example = "10554")
    private Long launchUid;

    @Schema(description = "工单发起人名称", example = "张三")
    private String launchName;

    @Schema(description = "巡检时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date time;

    @Schema(description = "所属表单id", example = "6402")
    private Long formId;

    @Schema(description = "序号")
    private Integer sort;

    @Schema(description = "巡检内容信息")
    private String content;

    @Schema(description = "巡检所选工单应用")
    private String workorderApp;

    @Schema(description = "工单id", example = "31515")
    private Long workorderId;

    @Schema(description = "工单编号")
    private String workorderNumber;

    @Schema(description = "绑定的资产json")
    private String propertyList;

    @Schema(description = "巡检状态;1=待开始2=正常3=异常未整改4=异常已整改5=跳过【字典值PATROL_RECORD_EQUIPMENT_STATUS】", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String status;

    @Schema(description = "整改处理状态;1=待处理;2=处理中;3=已处理", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String handleStatus;

}