package cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 流水删除历史 DO
 *
 * @author 智慧园区
 */
@TableName("org_bill_stream_delete_history")
@KeySequence("org_bill_stream_delete_history_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillStreamDeleteHistoryDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 流水id
     */
    private Integer streamId;
    /**
     * 审批id
     */
    private Integer approvalId;
    /**
     * 审批状态
     */
    private Integer approvalStatus;
    /**
     * 0新建1审批回调
     */
    private Integer type;
    /**
     * 审批结束时间
     */
    private LocalDateTime approvalTime;

}