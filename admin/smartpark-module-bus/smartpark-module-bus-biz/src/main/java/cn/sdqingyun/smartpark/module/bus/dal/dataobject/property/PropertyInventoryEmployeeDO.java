package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产盘点员工记录 DO
 *
 * @author 智慧园区
 */
@TableName("property_inventory_employee")
@KeySequence("property_inventory_employee_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyInventoryEmployeeDO extends TenantBaseDO {

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
     * 盘点清单id
     */
    private Long inventoryId;
    /**
     * 盘点员工id
     */
    private Long userId;
    /**
     * 资产分类id
     */
    private String typeIds;
    /**
     * 部门id
     */
    private String departmentIds;
    /**
     * 位置id
     */
    private String positionIds;
    /**
     * 支付状态
     */
    private String propertyStatus;
    /**
     * 管理员id
     */
    private String adminId;
    /**
     * 购置方式
     */
    private String purchaseType;
    /**
     * 完成状态 1是0否
     */
    private Integer status;
    /**
     * 是否是全部权限
     */
    private Integer isAll;
    /**
     * 每个用户的范围回显冗余字段
     */
    private String exterData;

}