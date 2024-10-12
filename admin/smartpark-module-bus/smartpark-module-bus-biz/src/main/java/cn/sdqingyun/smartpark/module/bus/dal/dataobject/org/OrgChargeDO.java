package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 收费标准 DO
 *
 * @author 智慧园区
 */
@TableName("org_charge")
@KeySequence("org_charge_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgChargeDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 标准名称
     */
    private String name;
    /**
     * 计费模式：1-单价，2-单价*计量单位
     */
    private String mode;
    /**
     * 收费金额、单价(根据 mode 来决定)
     */
    private BigDecimal unitPrice;
    /**
     * 标准生效时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date validTime;
    /**
     * 标准失效时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date expireTime;
    /**
     * 状态：0-已失效，1-生效中
     */
    private String status;
    /**
     * 计量单位
     */
    private String unit;
    /**
     * 违约金起算天数
     */
    private Integer afterDaysStartBill;
    /**
     * 违约金收取比例（天/%）
     */
    private Integer everyDayPre;
    /**
     * 违约金上限(元)
     */
    private BigDecimal upperLimitFee;
    /**
     * 绑定对象： 1-房间，2-车位
     */
    private String bindObject;
    /**
     * 园区Id
     */
    private Long villageId;
    /**
     * 账单费用类型表id
     */
    private Long billCostTypeId;
    /**
     * 计费单位
     */
    private String unitCharging;
    /**
     * 计费规则(天单价换算、月划分)
     */
    private String dailyUnitPriceAndMonthRule;
    /**
     * 账单(应)付款时间-动作
     */
    private String payDateAction;
    /**
     * 天数（日）
     */
    private Integer payDateDays;
    /**
     * 账单(应)付款时间-方式
     */
    private String payDateActionType;
    /**
     * 付款周期（N月一付）
     */
    private Integer payCycle;
    /**
     * 划分方式
     */
    private String divideMethod;

}