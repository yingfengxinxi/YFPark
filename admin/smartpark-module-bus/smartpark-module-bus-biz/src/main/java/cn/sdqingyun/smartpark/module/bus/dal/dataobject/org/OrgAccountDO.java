package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 收支账户配置 DO
 *
 * @author 智慧园区
 */
@TableName("org_account")
@KeySequence("org_account_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgAccountDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 条目名称
     */
    private String name;
    /**
     * 收款公司
     */
    private String company;
    /**
     * 开户银行
     */
    private String bank;
    /**
     * 银行账号
     */
    private String bankAccount;
    /**
     * 总分类账科目
     */
    private String subject;
    /**
     * 应用的楼宇
     */
    private String build;
    /**
     * 是否启用0=否1=是
     */
    private String status;

}