package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 税率规则配置 DO
 *
 * @author 智慧园区
 */
@TableName("org_tax_rule")
@KeySequence("org_tax_rule_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgTaxRuleDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 费用类型
     */
    private String costType;
    /**
     * 税收分类编码ID
     */
    private Long taxCodeId;

    @TableField(exist = false)
    private String taxCodeName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态;1=开启;0=关闭
     */
    private String status;

}