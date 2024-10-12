package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

/**
 * 自定义 DO
 *
 * @author 管理员
 */
@TableName("energy")
@KeySequence("energy_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 设备表种类
     */
    private Long type;
    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 楼宇ID
     */
    private Long buildId;
    /**
     * 用途 1-分表 2-总表 3-公摊表
     */
    private String purpose;
    /**
     * 名称
     */
    private String name;
    /**
     * 表具编号
     */
    private String number;
    /**
     * 倍率
     */
    private BigDecimal magnification;
    /**
     * 原始读数
     */
    private BigDecimal originalReading;
    /**
     * 最大读数
     */
    private BigDecimal maxReading;
    /**
     * 抄录时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date meterTime;
    /**
     * 公摊方式 1-不公摊 2-按分表用量比例公摊 3-按租客租赁面积比例公摊 4-按房源数量平均公摊
     */
    private String publicType;
    /**
     * 授予用户管理的项目和楼宇，json存储
     */
    private String builds;

    @Schema(description = "楼层id,1,2,3")
    private String layerIds;
    /**
     * 绑定房间信息，1,2,3
     */
    private String roomIds;
    /**
     * 合同ids
     */
    private String contractIds;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态，1启动，0审核中，4禁用
     */
    private String status;

    @Schema(description = "厂商id", example = "2")
    private String deviceId;

    @Schema(description = "设备类型", example = "2")
    private String deviceType;

    @Schema(description = "设备序列号", example = "2")
    private String deviceSerial;
    /**
     * 资产的ID
     */
    private Long propertyId;
    /**
     * 设备通断状态 0通，1断
     */
    private String deviceOff;
    /**
     * 设备状态 0无 1离线 2断电 3电量过低
     */
    private String deviceStatus;
    /**
     * 上次心跳时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date lastHeartTime;
    /**
     * 是否开启远程阀控/1是0否
     */
    private String remoteValveControl;
    /**
     * 是否提醒过 1是0否、send_remind_value(低于提醒值)，send_zero_value（小于0）
     */
    private String isRemind;
    /**
     * 当前余量
     */
    private BigDecimal currentRemaining;
    /**
     * 付费类型 0是后付费 1是预付费
     */
    private String paymentType;

}