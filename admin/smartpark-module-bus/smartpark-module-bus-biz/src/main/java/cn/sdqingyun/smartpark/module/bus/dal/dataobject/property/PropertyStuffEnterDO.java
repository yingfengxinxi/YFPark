package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 耗材入库记录 DO
 *
 * @author 智慧园区
 */
@TableName("property_stuff_enter")
@KeySequence("property_stuff_enter_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyStuffEnterDO extends TenantBaseDO {

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
     * 流程编号
     */
    private String processCode;
    /**
     * 入库仓库id
     */
    private Long depositoryId;
    /**
     * 入库处理人uid
     */
    private Long enterUid;
    /**
     * 入库时间
     */
    private LocalDateTime enterTime;
    /**
     * 供应商
     */
    private String supplier;
    /**
     * 合计金额
     */
    private BigDecimal totalPrice;
    /**
     * 单据状态;1=进行中;2=已打回;3=已撤销;4=已完结
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 操作人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;

}