package cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 应用巡检任务变更执行人日志 DO
 *
 * @author 管理员
 */
@TableName("task_user_log")
@KeySequence("task_user_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskUserLogDO extends TenantBaseDO {

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
     * 应用标识
     */
    private String application;
    /**
     * 任务id
     */
    private Long taskId;
    /**
     * 旧执勤人id
     */
    private Long oldUid;
    /**
     * 旧执勤人姓名
     */
    private String oldName;
    /**
     * 新执勤人id
     */
    private Long newUid;
    /**
     * 新执勤人姓名
     */
    private String newName;

}