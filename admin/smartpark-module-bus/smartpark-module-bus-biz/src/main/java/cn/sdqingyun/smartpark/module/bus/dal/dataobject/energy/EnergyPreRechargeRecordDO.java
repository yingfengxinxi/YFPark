package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 水电表预充值记录 DO
 *
 * @author 管理员
 */
@TableName("energy_pre_recharge_record")
@KeySequence("energy_pre_recharge_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyPreRechargeRecordDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构iD
     */
    private Long orgId;
    /**
     * 水电表id
     */
    private Long energyId;
    /**
     * 充值金额
     */
    private BigDecimal rechargePrice;
    /**
     * 充值度数
     */
    private BigDecimal rechargeDegree;
    /**
     * 账单id
     */
    private Long billId;
    /**
     * 流水id
     */
    private Long billStreamId;
    /**
     * 流水号
     */
    private String streamNumber;
    /**
     * 账单编号
     */
    private String billNumber;
    /**
     * 设备上报返回的数据
     */
    private String result;

}