package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 账单开据记录 DO
 *
 * @author 智慧园区
 */
@TableName("org_bill_receipt_log")
@KeySequence("org_bill_receipt_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgBillReceiptLogDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 开票批次凭证
     */
    private String taskKey;
    /**
     * 收据模板id
     */
    private Long templateId;
    /**
     * 所选账单id集合
     */
    private String billIds;
    /**
     * 所选子账单id集合
     */
    private String subbillIds;
    /**
     * 收据账单信息(包含合并账单)
     */
    private String receiptJson;
    /**
     * 本次选择的收据账单组数
     */
    private Integer chooseNum;
    /**
     * 本次选择的账单开据金额
     */
    private BigDecimal chooseAmount;

}