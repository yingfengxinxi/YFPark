package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 耗材即时库存 DO
 *
 * @author 智慧园区
 */
@TableName("property_stuff_stock")
@KeySequence("property_stuff_stock_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyStuffStockDO extends TenantBaseDO {

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
     * 耗材物料id
     */
    private Long stuffId;
    /**
     * 仓库id;需同步该信息
     */
    private Long depositoryId;
    /**
     * 入库流程编号
     */
    private String processCode;
    /**
     * 可用库存
     */
    private BigDecimal usableNum;
    /**
     * 冻结库存
     */
    private BigDecimal frozenNum;
    /**
     * 总库存
     */
    private BigDecimal totalNum;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 耗材定价
     */
    private BigDecimal chargePrice;
    /**
     * 备注
     */
    private String remark;
    /**
     * 其他
     */
    private String extra;
    /**
     * 是否达到安全库存上限;默认安全
     */
    private Integer isStockUp;
    /**
     * 是否达到安全库存下限;默认安全
     */
    private Integer isStockLower;
    /**
     * 操作人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;

}