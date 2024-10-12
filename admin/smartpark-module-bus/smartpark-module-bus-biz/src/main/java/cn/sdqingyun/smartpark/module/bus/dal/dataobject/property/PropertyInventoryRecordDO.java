package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产盘点记录 DO
 *
 * @author 智慧园区
 */
@TableName("property_inventory_record")
@KeySequence("property_inventory_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyInventoryRecordDO extends TenantBaseDO {

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
     * 资产id
     */
    private Long propertyId;
    /**
     * 资产分类id
     */
    private Long typeId;
    /**
     * 0未盘 1已盘 2异常 3盘盈 4盘亏 5已结束
     */
    private Integer status;
    /**
     * 资产状态
     */
    private Integer propertyStatus;
    /**
     * 是否在盘点范围之内
     */
    private Integer isRange;
    /**
     * 是否修改资产信息 0否 1是
     */
    private Integer isUpdate;
    /**
     * 盘点备注
     */
    private String remark;
    /**
     * 盘点图片
     */
    private String image;
    /**
     * 盘点标签
     */
    private String tag;
    /**
     * 盘点时间
     */
    private LocalDateTime inventoryTime;
    /**
     * 盘点人
     */
    private String inventoryUid;
    /**
     * 当前位置
     */
    private Long positionId;
    /**
     * 处理人
     */
    private Long handleUid;
    /**
     * 资产所属部门id
     */
    private Long departmentId;
    /**
     * 资产盘点信息
     */
    private String propertyInfo;
    /**
     * 资产名称
     */
    private String name;
    /**
     * 资产编码
     */
    private String propertyNumber;

}