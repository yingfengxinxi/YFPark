package cn.sdqingyun.smartpark.module.bus.dal.dataobject.pay;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 支付订单和工单订单记录中间 DO
 *
 * @author 管理员
 */
@TableName("pay_order_work_order")
@KeySequence("pay_order_work_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderWorkOrderDO extends TenantBaseDO {

    /**
     * 支付订单编号
     */
    @TableId
    private Long id;
    /**
     * 应用标识
     */
    private String application;
    /**
     * 付款记录id
     */
    private Long workorderProposeOrderId;
    /**
     * 商户订单编号
     */
    private String merchantOrderId;
    /**
     * 支付金额，单位：分
     */
    private Integer price;
    /**
     * 订单类型0=支付1=退款
     */
    private String orderType;

}