package cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 合同到期提醒规则 DO
 *
 * @author 智慧园区
 */
@TableName("contract_expire_rule")
@KeySequence("contract_expire_rule_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractExpireRuleDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 合同到期规则名称
     */
    private String ruleName;
    /**
     * 关联楼宇id，多个id用逗号隔开
     */
    private String relationBuilds;
    /**
     * 提醒方式0=站内1=公众号
     */
    private String reminderMethod;

    /**
     * 提前几天提醒(天)
     */
    private Integer reminderDay;

}