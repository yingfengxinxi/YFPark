package cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 预约订单退款 DO
 *
 * @author 智慧园区
 */
@TableName("reav_booking_order_refund")
@KeySequence("reav_booking_order_refund_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResvBookingOrderRefundDO extends TenantBaseDO {

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
     * 预约ID
     */
    private Long bookingId;
    /**
     * 预约订单ID
     */
    private Long orderId;
    /**
     * 退款订单号
     */
    private String orderNo;
    /**
     * 退款金额
     */
    private BigDecimal amount;
    /**
     * 实际退款金额
     */
    private BigDecimal actualAmount;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 租客ID
     */
    private Long ownerId;
    /**
     * 处理人
     */
    private Long handler;
    /**
     * 处理人名称
     */
    private String handlerName;
    /**
     * 处理时间
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime handleTime;
    /**
     * 退款类型 1 全部退款 2 部分退款
     */
    private Integer refundType;
    /**
     * 0未处理 1已处理 2驳回
     */
    private Integer status;
    /**
     * 退款错误日志
     */
    private String errorMsg;

}