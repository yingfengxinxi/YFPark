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

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

/**
 * 应用巡检计划 DO
 *
 * @author 智慧园区
 */
@TableName("patrol_plan_equipment")
@KeySequence("patrol_plan_equipment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatrolPlanEquipmentDO extends TenantBaseDO {

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
     * 计划编号
     */
    private String planNumber;
    /**
     * 计划名称
     */
    private String planName;
    /**
     * 所选责任岗位id
     */
    private Long stationId;


    @Schema(description = "责任岗位父级id集合(多个使用逗号隔开)", example = "25025")
    private String stationParentId;

    @Schema(description = "责任部门", example = "20940")
    private Long departmentId;

    @Schema(description = "责任部门父级id集合(多个使用逗号隔开)", example = "25025")
    private String departmentParentId;
    /**
     * 开始日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date startDate;
    /**
     * 截止日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date endDate;
    /**
     * 日期永久标识
     */
    private String dateFlag;
    /**
     * 超时处理规则类型;1=不处理;2=自动完成;3=挂起
     */
    private String timeOutType;
    /**
     * 任务时限/小时
     */
    private Integer timeLimit;
    /**
     * 上报工单所属应用标识
     */
    private String workOrderApp;
    /**
     * 计划周期规则
     */
    private String planRule;
    /**
     * 最近一次执行时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date lastTime;
    /**
     * 下次执行时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date nextTime;
    /**
     * 计划执行结束时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date endTime;
    /**
     * 巡检顺序;1=必须一次;2=可以随机
     */
    private String patrolOrder;
    /**
     * 提醒方式及提醒规则
     */
    private String remindJson;
    /**
     * 巡检计划状态
     */
    private String planStatus;
    /**
     * 计划启用状态;0=关闭;1=开启
     */
    private String status;
    /**
     * 提前完成的时间数量
     */
    private Integer inAdvance;
    /**
     * 提前完成的时间单位，1是小时2是天3是分钟
     */
    private Integer minuteTime;

}