package cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 付款单记录 DO
 *
 * @author 管理员
 */
@TableName("workorder_payment_order")
@KeySequence("workorder_payment_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderPaymentOrderDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构id
     */
    private Long orgId;
    /**
     * 应用标识
     */
    private String application;
    /**
     * 账单id
     */
    private String billId;
    /**
     * 账单号
     */
    private String billNumber;
    /**
     * 单据编号
     */
    private String approveNumber;
    /**
     * 收款方、租客id
     */
    private Long ownerId;
    /**
     * 金额
     */
    private BigDecimal totalPrice;
    /**
     * 审核时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date auditTime;
    /**
     * 付款时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date payTime;
    /**
     * 工单id
     */
    private String orderId;
    /**
     * 0待审核  1同意（已生成）  2拒绝  3 撤销  4已付款
     */
    private String status;
    /**
     * 费用类型
     */
    private String costType;

}