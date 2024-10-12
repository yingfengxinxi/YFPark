package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目房间锁定日志 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_room_lock_logs")
@KeySequence("village_room_lock_logs_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomLockLogsDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 房间ID
     */
    private Long roomId;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 状态(1锁定，0取消锁定)
     */
    private Integer status;
    /**
     * 原因
     */
    private String reason;
    /**
     * 操作时间
     */
    private LocalDateTime lockTime;
    /**
     * 操作人uid
     */
    private Long operationUid;

}