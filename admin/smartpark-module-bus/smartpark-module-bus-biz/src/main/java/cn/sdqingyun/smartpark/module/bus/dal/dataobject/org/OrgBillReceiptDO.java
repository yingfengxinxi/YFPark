package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo.BillingStatementVO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.*;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 机构账单收据 DO
 *
 * @author 智慧园区
 */
@TableName("org_bill_receipt")
@KeySequence("org_bill_receipt_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgBillReceiptDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 项目id
     */
    private Long villageId;
    /**
     * 楼宇id
     */
    private Long buildId;
    @TableField(exist = false)
    private String buildName;
    /**
     * 账单id
     */
    private Long billId;
    /**
     * 子账单ID
     */
    private Long subbillId;
    /**
     * 账单收据编号规则id
     */
    private Long ruleId;
    /**
     * 房号
     */
    private String roomNumber;
    /**
     * 交款公司/单位id
     */
    private Long paymentCompanyId;
    /**
     * 交款单位
     */
    private String paymentCompany;
    /**
     * 交款人
     */
    private String paymentUname;
    /**
     * 交款方地址
     */
    private String paymentAddress;
    /**
     * 交款方电话
     */
    private String paymentPhone;
    /**
     * 收据编号
     */
    private String receiptNumber;
    /**
     * 编号数值
     */
    private Integer receiptInt;
    /**
     * 生成编号方式;1=规则生成;2=自定义
     */
    private String numberType;
    /**
     * 收据金额
     */
    private BigDecimal price;
    /**
     * 可开据金额
     */
    private BigDecimal canReceiptAmount;
    /**
     * 申请开据金额
     */
    private BigDecimal applyReceiptAmount;
    /**
     * 币种
     */
    private String currency;
    /**
     * 收据状态;1=已发出;2=已回收;3=新建待审批;4=作废待审批;5=已作废;6=未发出;
     */
    private String status;
    @TableField(exist = false)
    private String statusName;
    /**
     * 交款人uid
     */
    private String paymentUid;
    /**
     * 收款公司/单位id
     */
    private Long collectionCompanyId;
    /**
     * 收款单位
     */
    private String collectionCompany;
    /**
     * 收款人uid
     */
    private String collectionUid;
    /**
     * 收款方收款人
     */
    private String collectionUname;
    /**
     * 收款方地址
     */
    private String collectionAddress;
    /**
     * 收款方电话
     */
    private String collectionPhone;
    /**
     * 备注
     */
    private String remark;
    /**
     * 汇款方式
     */
    private String remitType;
    /**
     * 费用类型
     */
    private String costType;
    /**
     * 费用名称
     */
    private String costName;
    /**
     * 开据批次凭证
     */
    private String taskKey;
    /**
     * 开据分组凭证
     */
    private String groupKey;
    /**
     * 开据人
     */
    private String issuerUid;
    /**
     * 开据时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date issuerTime;
    /**
     * 收据当前应用模板id
     */
    private Long applyTemplateId;

    /**
     * 生成收据url
     */
    private String receoptFileUrl;
    /**
     * 收据当前应用模板生成数据
     */
    private String applyTemplate;
    /**
     * 收据生成状态;0=未生成 1=生成中 2=已生成
     */
    private String applyStatus;
    //开据账单
    @TableField(exist = false)
    private BillingStatementVO billingStatement;
}