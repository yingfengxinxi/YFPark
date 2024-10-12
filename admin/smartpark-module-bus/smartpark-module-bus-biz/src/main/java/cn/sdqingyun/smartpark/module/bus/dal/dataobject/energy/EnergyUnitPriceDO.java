package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 自定义价格标准表-阶梯单价 DO
 *
 * @author 管理员
 */
@TableName("energy_unit_price")
@KeySequence("energy_unit_price_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyUnitPriceDO extends TenantBaseDO {

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
     * 用量区间1
     */
    private String startUsageRange;
    /**
     * 用量区间2
     */
    private String endUsageRange;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 自定义价格标准表id
     */
    private Long energyPriceId;

}