package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 账单费用分类 DO
 *
 * @author 智慧园区
 */
@TableName("org_bill_cost_category")
@KeySequence("org_bill_cost_category_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgBillCostCategoryDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 费用分类名称
     */
    private String name;
    /**
     * 是否设置为保证金类型
     */
    private String isBond;
    /**
     * 是否系统内置
     */
    private String isRoot;
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 分类层级
     */
    private String level;
    /**
     * 排序
     */
    private Integer sort;

}