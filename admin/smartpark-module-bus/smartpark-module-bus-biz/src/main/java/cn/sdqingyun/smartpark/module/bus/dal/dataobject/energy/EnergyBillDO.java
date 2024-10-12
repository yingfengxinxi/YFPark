package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 自定义账单 DO
 *
 * @author 管理员
 */
@TableName("energy_bill")
@KeySequence("energy_bill_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyBillDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 设备表种类
     */
    private String type;
    /**
     * 账单编号
     */
    private String billNumber;
    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 楼宇ID
     */
    private Long buildId;
    /**
     * 自定义关联ID
     */
    private Long energyId;
    /**
     * 租客ID
     */
    private Long ownerId;
    /**
     * 涉及抄表记录ids
     */
    private String energyRecordIds;
    /**
     * 合同信息
     */
    private Long contractId;
    /**
     * 起始时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date endTime;
    /**
     * 分表用量
     */
    private BigDecimal partUse;
    /**
     * 分表金额
     */
    private BigDecimal partAmount;
    /**
     * 分摊用量
     */
    private BigDecimal publicUse;
    /**
     * 分摊金额
     */
    private BigDecimal publicAmount;
    /**
     * 税率
     */
    private Integer taxRate;
    /**
     * 账单应付时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date receivablePaymentTime;
    /**
     * 总计金额
     */
    private BigDecimal totalAmount;
    /**
     * 房间ids
     */
    private String roomIds;
    /**
     * 状态，1是，0否
     */
    private String isNewOwner;
    /**
     * 滞纳金;{"startDay":10,"ratio":50,"toplimit":80}
     */
    private String lateFee;
    /**
     * 状态，1已生成，2待生成，3禁用
     */
    private String status;
    /**
     * 生成流水状态，1已产生，2未产成，3禁用
     */
    private String businessStatus;

}