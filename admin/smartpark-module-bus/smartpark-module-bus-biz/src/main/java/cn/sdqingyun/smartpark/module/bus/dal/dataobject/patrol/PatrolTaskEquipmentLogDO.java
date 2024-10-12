package cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
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
 * 应用巡检任务日志 DO
 *
 * @author 智慧园区
 */
@TableName("patrol_task_equipment_log")
@KeySequence("patrol_task_equipment_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatrolTaskEquipmentLogDO extends TenantBaseDO {

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
     * 计划key
     */
    private String planKey;
    /**
     * 周期
     */
    private String crontab;
    /**
     * 规则
     */
    private Integer rule;
    /**
     * 生成时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date buildTime;
    /**
     * 任务执行时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date taskTime;
    /**
     * 状态
     */
    private String status;
    /**
     * 执行顺序
     */
    private Integer sort;
    /**
     * 累计执行
     */
    private Integer total;

}