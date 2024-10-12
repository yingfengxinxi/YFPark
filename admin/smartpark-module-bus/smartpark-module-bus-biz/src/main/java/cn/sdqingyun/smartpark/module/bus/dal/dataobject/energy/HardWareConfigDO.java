package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 智能硬件配置 DO
 *
 * @author 管理员
 */
@TableName("hardware_config")
@KeySequence("hardware_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HardWareConfigDO extends TenantBaseDO {

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
     * 项目id
     */
    private Long villageId;
    /**
     * 楼宇id
     */
    private Long buildId;
    /**
     * 是否自动断电 1是 0否
     */
    private String electricityType;
    /**
     * 电费可逾期天数
     */
    private Integer electricityLimitDay;
    /**
     * 是否自动断水 1是 0否
     */
    private String waterType;
    /**
     * 1是已电控水 2直接断水
     */
    private String waterCutType;
    /**
     * 水费可逾期天数
     */
    private Integer waterLimitDay;

}