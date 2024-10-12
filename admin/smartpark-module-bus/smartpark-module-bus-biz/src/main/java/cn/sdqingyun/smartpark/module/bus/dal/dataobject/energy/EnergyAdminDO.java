package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 自定义抄表管理员 DO
 *
 * @author 管理员
 */
@TableName("energy_admin")
@KeySequence("energy_admin_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyAdminDO extends TenantBaseDO {

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
     * 用户uid
     */
    private Long uid;
    /**
     * 管理员姓名
     */
    private String name;
    /**
     * 0=管理员,1=超级管理员;
     */
    private String role;
    /**
     * 状态;0=禁用,1=启用
     */
    private String status;

}