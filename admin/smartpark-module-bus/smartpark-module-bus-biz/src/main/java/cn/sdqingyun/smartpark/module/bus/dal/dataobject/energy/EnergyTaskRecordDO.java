package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 自定义抄表任务记录 DO
 *
 * @author 管理员
 */
@TableName("energy_task_record")
@KeySequence("energy_task_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyTaskRecordDO extends TenantBaseDO {

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
     * 计划id
     */
    private Long planId;
    /**
     * 任务id
     */
    private Long taskId;
    /**
     * 自定义表id
     */
    private Long energyId;
    /**
     * 第三方任务id
     */
    private Long thirdTaskId;
    /**
     * 抄录数据json
     */
    private String extraData;

}