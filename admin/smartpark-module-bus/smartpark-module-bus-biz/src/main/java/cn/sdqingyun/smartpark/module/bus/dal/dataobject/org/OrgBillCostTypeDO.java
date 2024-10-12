package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 账单费用类型 DO
 *
 * @author 智慧园区
 */
@TableName("org_bill_cost_type")
@KeySequence("org_bill_cost_type_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgBillCostTypeDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 费用分类id
     */
    private Long categoryId;
    /**
     * 是否为保证金类型0=否1-是
     */
    private String isBond;
    /**
     * 是否系统内置
     */
    private String isRoot;
    /**
     * 必须缴费的项目，否则影响业务使用
     */
    private String isImportant;
    /**
     * 费用类型名称
     */
    private String name;
    /**
     * 费用类型
     */
    private String costType;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 费用状态
     */
    private String status;

}