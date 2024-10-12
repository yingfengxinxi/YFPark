package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

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

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 自定义抄表计划 DO
 *
 * @author 管理员
 */
@TableName("energy_plan")
@KeySequence("energy_plan_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyPlanDO extends TenantBaseDO {

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
    private String stationIds;

    /**
     * 所选责任岗位父级id
     */
    private String stationParentId;
    /**
     * 责任部门
     */
    private Long departmentId;

    /**
     * 责任部门父级id
     */
    private String departmentParentId;
    /**
     * 任务时限/小时
     */
    private Integer timeLimit;
    /**
     * 抄表范围json
     */
    private String energyType;
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
     * 项目ID
     */
    private Long villageId;
    /**
     * 楼宇ID
     */
    private Long buildId;

    @Schema(description = "绑定楼层信息，1,2,3")
    private String layerIds;

    /**
     * 绑定房间信息，1,2,3
     */
    private String roomIds;

    /**
     * 提醒方式及提醒规则
     */
    private String remindJson;
    /**
     * 自定义抄表计划状态
     */
    private String planStatus;
    /**
     * 计划启用状态;0=关闭;1=开启
     */
    private String status;

}