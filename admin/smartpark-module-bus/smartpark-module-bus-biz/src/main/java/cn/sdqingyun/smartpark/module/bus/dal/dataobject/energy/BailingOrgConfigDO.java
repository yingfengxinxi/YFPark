package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import lombok.*;

import java.util.*;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 智能表参数配置 DO
 *
 * @author 管理员
 */
@TableName("bailing_org_config")
@KeySequence("bailing_org_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BailingOrgConfigDO extends TenantBaseDO {

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
     * 类型【数据字典值BAILING_ORG_CONFIG】
     */
    private String type;
    /**
     * 名称
     */
    @TableField("`key`")
    private String key;
    /**
     * 值
     */
    @TableField("`value`")
    private String value;

}