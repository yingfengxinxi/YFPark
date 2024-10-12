package cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 应用巡检任务 DO
 *
 * @author 智慧园区
 */
@TableName("patrol_task_equipment")
@KeySequence("patrol_task_equipment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatrolTaskEquipmentDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 应用标识
     */
    private String application;
    /**
     * 计划id
     */
    private Long planId;
    /**
     * 巡检顺序;1=必须依次;2=可以随机
     */
    private String patrolOrder;
    /**
     * 任务周期
     */
    private String taskCycle;
    /**
     * 任务key
     */
    private String taskKey;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务编号
     */
    private String taskNumber;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date startTime;
    /**
     * 应开始时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date shouldTime;
    /**
     * 任务提醒时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date remindTime;
    /**
     * 负责岗位id
     */
    private Long stationId;

    @Schema(description = "责任岗位父级id集合(多个使用逗号隔开)", example = "25025")
    private String stationParentId;

    @Schema(description = "责任部门", example = "20940")
    private Long departmentId;

    @Schema(description = "责任部门父级id集合(多个使用逗号隔开)", example = "25025")
    private String departmentParentId;
    /**
     * 岗位部门人员uids
     */
    private String postUids;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date endTime;
    /**
     * 超时时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date timeoutTime;
    /**
     * 是否超时
     */
    private String isTimeout;
    /**
     * 是否发送提醒
     */
    private String isRemind;
    /**
     * 任务状态;【执行结果状态】
     */
    private String status;
    /**
     * 周期状态
     */
    private String hasLoop;
    /**
     * 第三方任务id
     */
    private String thirdTaskId;
    /**
     * 任务是否异常终止;
     */
    private Integer isStop;
    /**
     * 任务终止异常log
     */
    private String stopLog;

}