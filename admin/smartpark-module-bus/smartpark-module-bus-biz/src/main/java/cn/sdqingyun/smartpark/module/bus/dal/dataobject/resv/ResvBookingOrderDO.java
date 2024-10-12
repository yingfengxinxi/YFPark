package cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 预约订单 DO
 *
 * @author 智慧园区
 */
@TableName("resv_booking_order")
@KeySequence("resv_booking_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResvBookingOrderDO extends TenantBaseDO {

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
    private String appSign;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单总额
     */
    private BigDecimal orderTotal;
    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;
    /**
     * 已退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 支付订单号
     */
    private String payOrderNo;
    /**
     * 实际支付金额
     */
    private BigDecimal payAmount;
    /**
     * 支付方式
     */
    private String payMethod;
    /**
     * 支付方式文本
     */
    private String payMethodTxt;
    /**
     * 支付时间
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime payTime;
    /**
     * 有效支付时间
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime effectivePayTime;
    /**
     * 支付状态 0支付中 1支付成功 2已退款
     */
    private Integer payStatus;
    /**
     * 第三方订单号
     */
    private String thirdOrderNo;
    /**
     * 订单状态 0待支付 1已支付 2已核销 3已取消 4已过期
     */
    private Integer orderStatus;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 租客ID
     */
    private Long ownerId;

}