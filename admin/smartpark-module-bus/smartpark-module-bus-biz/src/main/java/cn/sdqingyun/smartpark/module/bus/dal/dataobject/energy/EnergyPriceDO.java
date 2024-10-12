package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 自定义价格标准 DO
 *
 * @author 管理员
 */
@TableName("energy_price")
@KeySequence("energy_price_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyPriceDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 设备表种类
     */
    private String type;
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
     * 名称
     */
    private String name;

    /**
     * 生效时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date effectDate;
    /**
     * 是否阶梯价 1是 0否
     */
    private String isStagePrice;
    /**
     * 倍率
     */
    private BigDecimal taxRate;
    /**
     * 单价标准，json存储
     */
    private String unitPrice;
    /**
     * 授予用户管理的项目和楼宇，json存储
     */
    private String builds;
    /**
     * 绑定房间信息，1,2,3
     */
    private String roomIds;
    /**
     * 滞纳金比例
     */
    private String ratio;
    /**
     * 起算天数
     */
    private String startDay;
    /**
     * 滞纳金上限
     */
    private String toplimit;
    /**
     * 状态，1启动，0审核中，4禁用
     */
    private String status;

}