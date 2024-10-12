package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 房间价格 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_room_price")
@KeySequence("village_room_price_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomPriceDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 房间ID
     */
    private Long roomId;
    /**
     * 创建日期
     */
    private LocalDateTime createdDay;
    /**
     * 建筑面积
     */
    private BigDecimal buildArea;
    /**
     * 房价（m²/天）
     */
    private BigDecimal squareDay;
    /**
     * 房价（m²/月）
     */
    private BigDecimal squareMonth;
    /**
     * 房价（天）
     */
    private BigDecimal day;
    /**
     * 房价（月）
     */
    private BigDecimal month;
    /**
     * 房价（年）
     */
    private BigDecimal year;
    /**
     * 状态（1正常，0隐藏）
     */
    private Integer status;

}