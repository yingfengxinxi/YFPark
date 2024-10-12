package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 账单收据编号规则 DO
 *
 * @author 智慧园区
 */
@TableName("org_bill_receipt_rule")
@KeySequence("org_bill_receipt_rule_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgBillReceiptRuleDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 收据编号规则名称
     */
    private String name;
    /**
     * 收据编号前缀
     */
    private String prefix;
    /**
     * 开始编号
     */
    private String startNumber;
    /**
     * 结束编号
     */
    private String endNumber;
    /**
     * 应用楼宇id,多个楼宇使用逗号隔开(1,2,3)
     */
    private String buildBind;

}