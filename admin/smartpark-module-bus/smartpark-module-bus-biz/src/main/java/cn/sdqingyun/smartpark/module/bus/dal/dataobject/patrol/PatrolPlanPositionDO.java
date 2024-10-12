package cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 应用巡检计划绑定巡检点 DO
 *
 * @author 智慧园区
 */
@TableName("patrol_plan_position")
@KeySequence("patrol_plan_position_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatrolPlanPositionDO extends TenantBaseDO {

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
     * 巡检点id
     */
    private Long positionId;
    /**
     * 是否扫码打卡0=否1=是
     */
    private String isScanCodeCheck;
    /**
     * 是否NFC打卡0=否1=是
     */
    private String isNfcClock;
    /**
     * 排序
     */
    private Integer sort;

}