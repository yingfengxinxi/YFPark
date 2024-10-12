package cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 付费工单订单 DO
 *
 * @author 管理员
 */
@TableName("workorder_propose_order")
@KeySequence("workorder_propose_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderProposeOrderDO extends TenantBaseDO {

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
     * 工单id
     */
    private Long workorderId;
    /**
     * 项目id
     */
    private Long villageId;
    /**
     * 楼宇id
     */
    private Long buildId;
    /**
     * 房号
     */
    private String roomId;
    /**
     * 应用标识
     */
    private String application;
    /**
     * 来源标识
     */
    @TableField(value = "`from`")
    private String from;
    /**
     * 租客id
     */
    private Long ownerId;
    /**
     * 订单名称
     */
    private String orderName;
    /**
     * 订单编号
     */
    private String orderNumber;
    /**
     * 订单状态;1=待支付;2=已支付;3=已退款;4=已取消5=退款失败 WORL_ORDER_PAY_STATUS;
     */
    private String orderStatus;
    /**
     * 退款状态;1=部分;2=全额;
     */
    private String refundStatus;
    /**
     * 工单大类id
     */
    private Long categoryId;
    /**
     * 工单子类id
     */
    private Long subcatId;
    /**
     * 订单金额(应付金额)
     */
    private BigDecimal amount;
    /**
     * 订单需付金额
     */
    private BigDecimal needAmount;
    /**
     * 支付金额
     */
    private BigDecimal paymentAmount;
    /**
     * 支付时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date paymentTime;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 退款时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date refundTime;
    /**
     * 订单类型 WORL_ORDER_TYPE 1=预缴费用 (下单即收费(定金/全额)) 2=附加费用 (物料耗材费+工时费+自定义服务项费用)3=尾款费用 (下单定金尾款/完成后收费+手动加价)
     */
    private String orderType;
    /**
     * 支付方式
     */
    private String payMethod;
    /**
     * 第三方订单号
     */
    private String thirdOrderNo;
    /**
     * 第三方信息
     */
    private String thirdExtra;
    /**
     * extra信息
     */
    private String extraData;
    /**
     * 资产单据信息json
     */
    private String stuffData;
    /**
     * 订单流程状态;1=正常;0=关闭
     */
    private String status;
    /**
     * 是否生成 1未生产 2已生成
     */
    private String generateStatus;

}