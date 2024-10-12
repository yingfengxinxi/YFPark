package cn.sdqingyun.smartpark.module.bus.dal.dataobject.park;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 停车场收费标准 DO
 *
 * @author 智慧园区
 */
@TableName("park_charge")
@KeySequence("park_charge_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkChargeDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 收费标准名称
     */
    private String chargeName;
    /**
     * 停车场ID
     */
    private Long parkId;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 适用车的类型（1蓝牌、黄牌等，参照D1）
     */
    private String carType;
    /**
     * 月租费用（null不支持缴费）
     */
    private BigDecimal monthFee;
    /**
     * 月租车到期后类型（0临时车（可能不允许进入），1储值车）
     */
    private String monthOverdueType;
    /**
     * 月租车自主缴费能输入月数（0不允许，1允许）
     */
    private String monthRechargeInput;
    /**
     * 月租车自主缴费套餐
     */
    private String monthRechargePackage;
    /**
     * 生效日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date effectiveDate;
    /**
     * 临时车免费时长（分钟）
     */
    private Integer freeMinute;
    /**
     * 临时车免费出场时长（分钟）
     */
    private Integer freeOutMinute;
    /**
     * 临时车0标准收费，1按次收费，2按白天夜间收费，3按设定时间收费
     */
    private String chargeType;
    /**
     * 临时车每日最高费用（0）不限
     */
    private BigDecimal dayMaxFee;
    /**
     * 临时车收费细则
     */
    private String chargeDetail;
    /**
     * 是否默认,1是0否
     */
    private String isDefault;

}