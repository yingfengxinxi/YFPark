package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 电表操作id关联表、回调用 DO
 *
 * @author 管理员
 */
@TableName("henghan_energy_opr")
@KeySequence("henghan_energy_opr_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HengHanEnergyOprDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构iD
     */
    private Long orgId;
    /**
     * 操作ID
     */
    private Long oprId;
    /**
     * 电表id
     */
    private Long energyId;
    /**
     * 操作类型、read（抄表） open（开闸） close(关闸)
     */
    private String type;

}