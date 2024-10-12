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
import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 预约 DO
 *
 * @author 智慧园区
 */
@TableName("resv_booking")
@KeySequence("resv_booking_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResvBookingDO extends TenantBaseDO {

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
     * 分类ID
     */
    private Long categoryId;
    /**
     * 场地ID
     */
    private Long placeId;
    /**
     * 场地可容纳人数
     */
    private Integer capacity;
    /**
     * 预约日期
     */
    private Date date;
    /**
     * 预约时间段 (多个)
     */
    private String timeSlots;
    /**
     * 开始时间
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime endTime;
    /**
     * 预约的是星期几
     */
    private String weekText;
    /**
     * 提醒时间
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime remindTime;
    /**
     * 是否已经提醒过 1是 0否
     */
    private Integer reminded;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 租客姓名
     */
    private String userName;
    /**
     * 用户手机号
     */
    private String userMobile;
    /**
     * 租客ID
     */
    private Long ownerId;
    /**
     * 租客名称
     */
    private String ownerName;
    /**
     * 冗余信息 比如 分类信息 场地信息
     */
    private String rest;
    /**
     * 状态 0待支付 1已支付 2已核销 3已过期
     */
    private Integer status;

}