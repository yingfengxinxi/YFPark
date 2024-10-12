package cn.sdqingyun.smartpark.module.bus.dal.dataobject.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 工单子类费用预约时段设置 DO
 *
 * @author 管理员
 */
@TableName("category_fee_set_reservation_date")
@KeySequence("category_fee_set_reservation_date_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryFeeSetReservationDateDO extends TenantBaseDO {

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
     * 工单子类费用设置表id
     */
    private Long feeSetId;
    /**
     * 设置可预约日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date orderTime;
    /**
     * 周
     */
    private String week;
    /**
     * 每日开放预约开始时间
     */
    private String openStartTime;
    /**
     * 每日开放预约结束时间
     */
    private String openEndTime;
    /**
     * 设置间隔时段
     */
    private Integer num;
    /**
     * 设置间隔时段单位
     */
    private String unit;
    /**
     * 间隔时段预约上限数
     */
    private Integer orderLimit;

}