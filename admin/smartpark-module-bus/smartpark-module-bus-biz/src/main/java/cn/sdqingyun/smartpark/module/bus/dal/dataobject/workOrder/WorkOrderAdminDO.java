package cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工单应用管理员信息 DO
 *
 * @author 管理员
 */
@TableName("workorder_admin")
@KeySequence("workorder_admin_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderAdminDO extends TenantBaseDO {

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
     * 1=管理员;2=客服工作人员
     */
    private String role;
    /**
     * 用户uid
     */
    private Long uid;
    /**
     * 岗位id集合
     */
    private String postId;
    /**
     * 姓名
     */
    private String name;

}