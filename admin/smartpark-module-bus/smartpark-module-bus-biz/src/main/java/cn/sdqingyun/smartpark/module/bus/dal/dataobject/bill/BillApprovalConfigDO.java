package cn.sdqingyun.smartpark.module.bus.dal.dataobject.bill;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 账单业务审批配置 DO
 *
 * @author 管理员
 */
@TableName("bill_approval_config")
@KeySequence("bill_approval_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillApprovalConfigDO extends TenantBaseDO {

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
     * 业务类型adjust_approve调整金
     */
    private String type;
    /**
     * 是否开启审批;0=否;1=是
     */
    private String isUse;
    /**
     * 其他信息
     */
    private String extra;

}