package cn.sdqingyun.smartpark.module.bus.dal.dataobject.ownerRemarks;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 租客备注信息 DO
 *
 * @author 智慧园区
 */
@TableName("owner_remarks")
@KeySequence("owner_remarks_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerRemarksDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 租客id
     */
    private Long ownId;
    /**
     * 机构id
     */
    private Long orgId;
    /**
     * 备注内容
     */
    private String remark;
    /**
     * 操作人uid
     */
    private Long operationUid;
    /**
     * 操作人姓名
     */
    private String operationName;
    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

}