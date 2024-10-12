package cn.sdqingyun.smartpark.module.bus.dal.dataobject.pay;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 支付订单和账单表中间表	 DO
 *
 * @author 智慧园区
 */
@TableName("pay_order_bill")
@KeySequence("pay_order_bill_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderBillDO extends TenantBaseDO {

    /**
     * 支付订单编号
     */
    @TableId
    private Long id;
    /**
     * 账单明细id
     */
    private Long billId;
    /**
     * 商户订单编号
     */
    private String merchantOrderId;
    /**
     * 支付金额，单位：分
     */
    private Integer price;

}