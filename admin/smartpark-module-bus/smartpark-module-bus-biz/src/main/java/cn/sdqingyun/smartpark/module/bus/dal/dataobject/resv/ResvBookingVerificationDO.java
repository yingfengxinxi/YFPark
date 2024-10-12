package cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 预约核销 DO
 *
 * @author 智慧园区
 */
@TableName("resv_booking_verification")
@KeySequence("resv_booking_verification_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResvBookingVerificationDO extends TenantBaseDO {

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
     * 预约ID
     */
    private Long bookingId;
    /**
     * 核销码
     */
    private String code;
    /**
     * 核销状态 0未核销 1已核销
     */
    private Integer status;
    /**
     * 用户ID
     */
    private Long userId;
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

}