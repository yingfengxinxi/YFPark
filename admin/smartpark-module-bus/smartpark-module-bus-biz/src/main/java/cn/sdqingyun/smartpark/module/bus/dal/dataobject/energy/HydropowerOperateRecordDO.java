package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 水电表开关记录 DO
 *
 * @author 管理员
 */
@TableName("hydropower_operate_record")
@KeySequence("hydropower_operate_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HydropowerOperateRecordDO extends TenantBaseDO {

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
     * 水电表id
     */
    private Long energyId;
    /**
     * 操作人、自动处理为空
     */
    private Long operateUid;
    /**
     * 操作状态、0关闸、1开闸 2、重置
     */
    private String status;
    /**
     * 操作原因
     */
    private String reason;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 如果是以电控水、对应的水表id
     */
    private Long deviceWaterId;

}