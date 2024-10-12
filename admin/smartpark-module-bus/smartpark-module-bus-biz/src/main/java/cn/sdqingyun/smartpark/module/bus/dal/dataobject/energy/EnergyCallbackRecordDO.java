package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

/**
 * 终端数据推送记录 DO
 *
 * @author 管理员
 */
@TableName("energy_callback_record")
@KeySequence("energy_callback_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyCallbackRecordDO extends TenantBaseDO {

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
     * 设备编号
     */
    private String equipNumber;
    /**
     * 设备类型码
     */
    private String equipCode;
    /**
     * 表ID
     */
    private Long energyId;
    /**
     * 设备读数
     */
    private BigDecimal reading;
    /**
     * 剩余水电数
     */
    private BigDecimal remaining;
    /**
     * 电池容量
     */
    private BigDecimal battery;
    /**
     * 电池容量百分比
     */
    private Integer percent;
    /**
     * 记录时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date saveTime;
    /**
     * 记录时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date saveDate;
    /**
     * 推送数据json
     */
    private String result;
    /**
     * 通断状态，1断，0通
     */
    private String isOff;
    /**
     * 异常状态，0无 1离线 2断电 3电量过低
     */
    private String abnormalStatus;
    private String isCompleted;

}