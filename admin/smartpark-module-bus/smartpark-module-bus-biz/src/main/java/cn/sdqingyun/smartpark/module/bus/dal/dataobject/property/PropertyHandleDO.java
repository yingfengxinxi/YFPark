package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产处置单据记录 DO
 *
 * @author 智慧园区
 */
@TableName("property_handle")
@KeySequence("property_handle_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyHandleDO extends TenantBaseDO {

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
     * 发起部门id
     */
    private Long departmentId;
    /**
     * 单据编号
     */
    private String processNumber;
    /**
     * 单据状态;1=进行中
     */
    private Integer status;
    /**
     * 处置金额
     */
    private BigDecimal handleAmount;
    /**
     * 处置费用
     */
    private BigDecimal handleExpenses;
    /**
     * 处置类型 遗失、变卖、报废、
     */
    private Integer handleType;
    /**
     * 处置原因
     */
    private String remark;
    /**
     * 发起时间
     */
    private LocalDateTime applyTime;
    /**
     * 操作人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;

}