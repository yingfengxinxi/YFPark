package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产盘点清单 DO
 *
 * @author 智慧园区
 */
@TableName("property_inventory_list")
@KeySequence("property_inventory_list_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyInventoryListDO extends TenantBaseDO {

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
     * 单据编号
     */
    private String number;
    /**
     * 盘点清单名称
     */
    private String inventoryName;
    /**
     * 盘点清单状态 0 盘点中 1盘点审批中 2已完结
     */
    private Integer inventoryStatus;
    /**
     * 流程编号
     */
    private String processCode;
    /**
     * 盘点人
     */
    private String inventoryUid;
    /**
     * 资产限制字段
     */
    private String restrictField;
    /**
     * 盘点范围（使用部门）
     */
    private String departmentIds;
    /**
     * 盘点范围（资产分类）
     */
    private String typeIds;
    /**
     * 盘点范围（资产位置）
     */
    private String positionIds;
    /**
     * 资产状态
     */
    private String propertyStatus;
    /**
     * 主付状态
     */
    private Integer initiativePayStatus;
    /**
     * 管理员id
     */
    private String adminId;
    /**
     * 所属公司
     */
    private Long company;
    /**
     * 购置方式
     */
    private String purchaseType;
    /**
     * 仅扫码盘点的分类
     */
    private String codeInventoryType;
    /**
     * 不可批量盘点的分类
     */
    private String notbreachInventoryType;
    /**
     * ntake_pic:仅上传拍照 ntask_pic_after:在原有基础在补充
     */
    private String uploadType;
    /**
     * 是否批量分配 1是0否
     */
    private Integer isBatch;
    /**
     * 单个分配 分配范围
     */
    private String distributionRange;
    /**
     * 额外数据、部门和分类的回显
     */
    private String exterData;

}