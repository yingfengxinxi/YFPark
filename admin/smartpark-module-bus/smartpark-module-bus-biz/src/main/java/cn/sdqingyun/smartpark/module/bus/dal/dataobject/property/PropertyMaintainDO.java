package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产保养记录 DO
 *
 * @author 智慧园区
 */
@TableName("property_maintain")
@KeySequence("property_maintain_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyMaintainDO extends TenantBaseDO {

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
     * 保养人uid
     */
    private Long maintainUid;
    /**
     * 保养人所在部门
     */
    private Long departmentId;
    /**
     * 保养人所在部门名称
     */
    private String departmentName;
    /**
     * 保养项目id
     */
    private String maintainVillageId;
    /**
     * 资产id集合json
     */
    private String propertyIds;
    /**
     * 资产集合json
     */
    private String propertyData;
    /**
     * 单据编号
     */
    private String number;
    /**
     * 流程编号
     */
    private String processCode;
    /**
     * 单据状态
     */
    private Integer status;
    /**
     * 保养总金额
     */
    private BigDecimal maintainTotalPrice;
    /**
     * 保养时间
     */
    private LocalDateTime maintainDate;
    /**
     * 下次保养时间
     */
    private LocalDateTime nextMaintainDate;
    /**
     * 处理人
     */
    private Long operateUid;
    /**
     * 处理时间
     */
    private LocalDateTime operateTime;
    /**
     * 保养备注
     */
    private String remark;
    /**
     * 操作人
     */
    private Long cuserUid;
    /**
     * 修改人
     */
    private Long muserUid;

}