package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 分摊记录 DO
 *
 * @author 管理员
 */
@TableName("energy_public_record")
@KeySequence("energy_public_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyPublicRecordDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 类型 1分表 2总表
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
     * 关联自定义表ID
     */
    private Long energyId;
    /**
     * 父级ID
     */
    private Long parentEnergyId;
    /**
     * 表名称
     */
    private String name;
    /**
     * 起始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 分摊用量
     */
    private BigDecimal publicUse;
    /**
     * 分摊金额
     */
    private BigDecimal publicAmount;
    /**
     * 状态，1启动，0审核中，4禁用
     */
    private String status;
    /**
     * 绑定账单
     */
    private String bindBill;
    /**
     * 账单ID
     */
    private Long billId;

}