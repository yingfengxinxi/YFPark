package cn.sdqingyun.smartpark.module.bus.controller.admin.patrol.vo;

import cn.sdqingyun.smartpark.module.bus.dal.dataobject.property.PropertyDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 应用巡检记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PatrolRecordEquipmentRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25714")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "24980")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识")
    @ExcelProperty("应用标识")
    private String application;

    @Schema(description = "巡检计划id", example = "29510")
    @ExcelProperty("巡检计划id")
    private Long planId;

    @Schema(description = "巡检任务id", example = "23591")
    @ExcelProperty("巡检任务id")
    private Long taskId;

    @Schema(description = "是否应用签到")
    @ExcelProperty("是否应用签到")
    private String isSigned;

    @Schema(description = "签到状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("签到状态")
    private String signStatus;

    @Schema(description = "是否应用nfc签到")
    @ExcelProperty("是否应用nfc签到")
    private String isNfc;

    @Schema(description = "巡检点id", example = "9561")
    @ExcelProperty("巡检点id")
    private Long patrolPositionId;

    @Schema(description = "巡检点名称")
    @ExcelProperty("巡检点名称")
    private String address;

    @Schema(description = "巡检人uid", example = "21596")
    @ExcelProperty("巡检人uid")
    private Long uid;

    @Schema(description = "巡检人名称", example = "芋艿")
    @ExcelProperty("巡检人名称")
    private String name;

    @Schema(description = "巡检点位置信息")
    @ExcelProperty("巡检点位置信息")
    private String position;

    @Schema(description = "工单发起人uid", example = "10554")
    @ExcelProperty("工单发起人uid")
    private Long launchUid;

    @Schema(description = "工单发起人名称", example = "张三")
    @ExcelProperty("工单发起人名称")
    private String launchName;

    @Schema(description = "巡检时间")
    @ExcelProperty("巡检时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date time;

    @Schema(description = "所属表单id", example = "6402")
    @ExcelProperty("所属表单id")
    private Long formId;

    @Schema(description = "序号")
    @ExcelProperty("序号")
    private Integer sort;

    @Schema(description = "巡检内容信息")
    @ExcelProperty("巡检内容信息")
    private String content;

    @Schema(description = "巡检所选工单应用")
    @ExcelProperty("巡检所选工单应用")
    private String workorderApp;

    @Schema(description = "工单id", example = "31515")
    @ExcelProperty("工单id")
    private Long workorderId;

    @Schema(description = "工单编号")
    @ExcelProperty("工单编号")
    private String workorderNumber;

    @Schema(description = "绑定的资产json")
    @ExcelProperty("绑定的资产json")
    private String propertyList;

    @Schema(description = "巡检状态;1=待开始2=正常3=异常未整改4=异常已整改5=跳过【字典值PATROL_RECORD_EQUIPMENT_STATUS】", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("巡检状态")
    private String status;

    @Schema(description = "整改处理状态;1=待处理;2=处理中;3=已处理", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("整改处理状态;1=待处理;2=处理中;3=已处理")
    private String handleStatus;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "绑定资产")
    private PropertyDO property;
}