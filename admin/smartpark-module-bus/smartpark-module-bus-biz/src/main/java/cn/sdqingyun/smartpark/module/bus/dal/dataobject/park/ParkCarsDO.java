package cn.sdqingyun.smartpark.module.bus.dal.dataobject.park;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 月租车白名单 DO
 *
 * @author 智慧园区
 */
@TableName("park_cars")
@KeySequence("park_cars_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkCarsDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 项目ID
     */
    private Long villageId;
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
     * 住户ID
     */
    private Long userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户手机号(128加密)
     */
    private String userPhone;
    /**
     * 车牌号
     */
    private String carNumber;
    /**
     * 车的类型（1蓝牌、黄牌等）
     */
    private String carType;
    /**
     * 管理类型（1月租车，2储值车，3登记/预约临时车）
     */
    private String chargeType;
    /**
     * 余额（单位元）
     */
    private BigDecimal balance;
    /**
     * 车的备注
     */
    private String carRemark;
    /**
     * 绑定的车位ID（允许多车多位所以用json）
     */
    private String parkSpace;
    /**
     * 车的数据字段（例如发动机编号、车品牌等）
     */
    private String carData;
    /**
     * 数据录入来源（1后台，0租客）
     */
    private String addFrom;
    /**
     * 当前名单是否有效（0：无效，1：有效）
     */
    private String enable;
    /**
     * 当前名单是否为黑名单（0：否，1：黑名单）
     */
    private String needAlarm;
    /**
     * 生效时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date enableTime;
    /**
     * 过期时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date overdueTime;
    /**
     * 是否已经推送给停车主机（0没有，1推送成功，2推送失败），修改后重置成0
     */
    private String isPush;
    /**
     * 推送主机的结果（主要展示失败原因）
     */
    private String pushResult;
    /**
     * 收费规则id，0是默认
     */
    private Long feeChargeId;
    /**
     * 车位数
     */
    private Integer spaceNum;
    /**
     * 主车辆id
     */
    private Long parentId;
    /**
     * 子车辆数量
     */
    private Integer subCarNum;


    /**
     * 车辆品牌
     */
    private String vehicleBrand;


    /**
     * 车辆设备号
     */
    private String vehicleEquipmentNumber;


    /**
     * 行驶证号
     */
    private String drivingLicenseNumber;


    /**
     * 颜色
     */
    private String vehicleColor;
}