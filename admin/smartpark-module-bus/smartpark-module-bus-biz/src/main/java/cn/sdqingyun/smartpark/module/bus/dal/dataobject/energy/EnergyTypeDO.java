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
 * 表种类管理 DO
 *
 * @author 管理员
 */
@TableName("energy_type")
@KeySequence("energy_type_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyTypeDO extends TenantBaseDO {

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
     * 表种类名称
     */
    private String name;
    /**
     * 费用类型
     */
    private String costType;
    /**
     * 费用类型文本
     */
    private String costTypeTxt;
    /**
     * 设备类型
     */
    private String equipType;
    /**
     * 计费单位 如度、方等
     */
    private String unit;
    /**
     * 是否自动断电 0否 1是
     */
    private String isBroken;
    /**
     * 可逾期天数
     */
    private Integer overdueDay;
    /**
     * 是否已电控水 0否 1是
     */
    private String cutType;
    /**
     * 提醒值
     */
    private BigDecimal remindValue;

}