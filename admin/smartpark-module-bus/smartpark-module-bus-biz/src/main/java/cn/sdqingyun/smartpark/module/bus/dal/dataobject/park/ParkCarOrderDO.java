package cn.sdqingyun.smartpark.module.bus.dal.dataobject.park;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 车的收费订单 DO
 *
 * @author 智慧园区
 */
@TableName("park_car_order")
@KeySequence("park_car_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkCarOrderDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 项目名称
     */
    @TableField(exist = false)
    private String villageName;
    /**
     * 停车场ID
     */
    private Long parkId;
    /**
     * 停车场名称
     */
    @TableField(exist = false)
    private String parkName;
    /**
     * 用户服务的ID
     */
    private Long uid;
    /**
     * 用户支付时的openid
     */
    private String userOpenid;
    /**
     * 车牌号
     */
    private String carNumber;
    /**
     * 订单类别（1月租车，2储值车，3临时车）
     */
    private String orderType;
    /**
     * 出入记录ID
     */
    private Long logId;
    /**
     * 入场时间
     */
    private LocalDateTime enterTime;
    /**
     * 离场时间
     */
    private LocalDateTime outTime;
    /**
     * 订单名称
     */
    private String orderName;
    /**
     * 订单详情数据
     */
    private String orderInfo;
    /**
     * 订单金额
     */
    private BigDecimal orderMoney;
    /**
     * 订单状态（0未支付，1已支付，2退款中，3已退款，4订单取消）
     */
    private String orderStatus;
    /**
     * 是否直付码（0预付码，1直付码）
     */
    private String isDirect;
    /**
     * 父级车道ID
     */
    private Long parentGateId;
    /**
     * 直付码的出场出入口
     */
    private Long gateId;
    /**
     * 订单实付金额
     */
    private BigDecimal payMoney;
    /**
     * 订单支付时间
     */
    private LocalDateTime payTime;
    /**
     * 收款用户ID
     */
    private Long cashUid;
    /**
     * 支付服务的订单号，用于退款、查询
     */
    private String payOrderId;
    /**
     * 支付方式
     */
    private String payMethod;
    /**
     * 支付方式文本
     */
    private String payMethodTxt;
    /**
     * 第三方订单号
     */
    private String thirdOrderNo;
    /**
     * 收费标准ID
     */
    private Long chargeId;
    /**
     * 发票状态0未开具1已开具2开具中3开票失败
     */
    private String invoiceStatus;
    /**
     * 发票链接
     */
    private String invoiceUrl;
    /**
     * 开票失败原因
     */
    private String invoiceError;
    /**
     * 退款金额
     */
    private BigDecimal refundMoney;
    /**
     * 退款时间
     */
    private LocalDateTime refundTime;
    /**
     * 优惠券id
     */
    private Long couponId;
    /**
     * 优惠券金额
     */
    private BigDecimal couponAmount;
    /**
     * 退款理由
     */
    private String reason;
    /**
     * 预计支付金额
     */
    private BigDecimal preMoney;
    /**
     * 充电抵扣金额
     */
    private BigDecimal underCharge;
    /**
     * 免费出场原因id
     */
    private Long freeReasonId;

}