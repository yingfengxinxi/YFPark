package cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 应用巡检记录 DO
 *
 * @author 管理员
 */
@TableName("patrol_record_equipment")
@KeySequence("patrol_record_equipment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatrolRecordEquipmentDO extends TenantBaseDO {

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
     * 巡检计划id
     */
    private Long planId;
    /**
     * 巡检任务id
     */
    private Long taskId;
    /**
     * 是否应用签到
     */
    private String isSigned;
    /**
     * 签到状态
     */
    private String signStatus;
    /**
     * 是否应用nfc签到
     */
    private String isNfc;
    /**
     * 巡检点id
     */
    private Long patrolPositionId;
    /**
     * 巡检点位置信息
     */
    private String address;
    /**
     * 巡检人uid
     */
    private Long uid;
    /**
     * 巡检人名称
     */
    private String name;
    /**
     * 巡检点名称
     */
    private String position;
    /**
     * 工单发起人uid
     */
    private Long launchUid;
    /**
     * 工单发起人名称
     */
    private String launchName;
    /**
     * 巡检时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date time;
    /**
     * 所属表单id
     */
    private Long formId;
    /**
     * 序号
     */
    private Integer sort;
    /**
     * 巡检内容信息【填写信息后更新】
     */
    private String content;
    /**
     * 巡检所选工单应用
     */
    private String workorderApp;
    /**
     * 工单id
     */
    private Long workorderId;
    /**
     * 工单编号
     */
    private String workorderNumber;
    /**
     * 绑定的资产json
     */
    private String propertyList;
    /**
     * 巡检状态;1=待开始2=正常3=异常未整改4=异常已整改5=跳过【字典值PATROL_RECORD_EQUIPMENT_STATUS】
     */
    private String status;
    /**
     * 整改处理状态;1=待处理;2=处理中;3=已处理
     */
    private String handleStatus;

}