package cn.sdqingyun.smartpark.module.bus.dal.dataobject.org;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 机构账单开票记录 DO
 *
 * @author 智慧园区
 */
@TableName("org_bill_invoice")
@KeySequence("org_bill_invoice_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgBillInvoiceDO extends TenantBaseDO {

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
    /**
     * 账单id
     */
    private Long billId;
    /**
     * 子账单ID
     */
    private Long subbillId;
    /**
     * 绑定楼宇信息json
     */
    private String buildBind;
    /**
     * 房号json
     */
    private String roomNumber;
    /**
     * 售方id
     */
    private Long sellerId;
    /**
     * 买方(租客)id
     */
    private Long ownerId;
    /**
     * 购方名称
     */
    private String buyerName;
    /**
     * 购方(租客)开户行账号
     */
    private String buyerAccount;
    /**
     * 货物类型;1=停车费;2=租金;
     */
    private String goodsType;
    /**
     * 账单费用类型
     */
    private String costType;
    /**
     * 开票时间
     */
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date invoiceTime;
    /**
     * 发票状态;1=待开票;2=已开票;3=已冲红;4=已作废;5=已开负数发票;6=作废审批中;
     */
    private String invoiceStatus;
    /**
     * 发票编号
     */
    private String invoiceNumber;
    /**
     * 开票记录金额
     */
    private BigDecimal recordPrice;
    /**
     * 已开票金额
     */
    private BigDecimal invoicePrice;
    /**
     * 可开票金额
     */
    private BigDecimal canInvoiceAmount;
    /**
     * 申请开票金额
     */
    private BigDecimal applyInvoiceAmount;
    /**
     * 税额
     */
    private BigDecimal taxAmount;
    /**
     * 税率
     */
    private Integer taxRate;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 数量
     */
    private Integer total;
    /**
     * 商品或服务名称
     */
    private String taxCostName;
    /**
     * 规格
     */
    private String specs;
    /**
     * 单位
     */
    private String invoiceUnit;
    /**
     * 购方开户行
     */
    private String buyerBank;
    /**
     * 发票代码
     */
    private String invoiceCode;
    /**
     * 购方税号
     */
    private String buyerTaxNumber;
    /**
     * 购方电话
     */
    private String buyerPhone;
    /**
     * 购方地址
     */
    private String buyerAddress;
    /**
     * 备注
     */
    private String remark;
    /**
     * 未交税金额
     */
    private BigDecimal nopayTaxPrice;
    /**
     * 价税合计
     */
    private BigDecimal taxPriceAmount;
    /**
     * 售方名称
     */
    private String salerName;
    /**
     * 售方开户行账号
     */
    private String salerAccount;
    /**
     * 售方银行
     */
    private String salerBank;
    /**
     * 售方地址
     */
    private String salerAddress;
    /**
     * 售方电话
     */
    private String salerPhone;
    /**
     * 售方税号(纳税人识别号)
     */
    private String salerTaxNumber;
    /**
     * 作废/红冲日期
     */
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date extraTime;
    /**
     * 开票类型;1=增值税普通发票;2=增值税专用发票;3=增值税电子普通发票;
     */
    private String invoiceType;
    /**
     * 开票批次凭证
     */
    private String taskKey;
    /**
     * 分组批次凭证
     */
    private String groupKey;
    /**
     * 操作开票类型;1=提前开票;2=实收开票
     */
    private String issueType;
    /**
     * 冲红发票号码
     */
    private String redInvoiceNumber;
    /**
     * 冲红发票代码
     */
    private String redInvoiceCode;
    /**
     * 冲红发票开据时间
     */
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date redInvoiceTime;
    /**
     * 是否需要开据电子发票 1需要 0不需要
     */
    private String canEInvoice;
    /**
     * 电子发票开据状态 0未开票 1开票成功 2开票中 3开票失败
     */
    private String eInvoiceStatus;
    /**
     * 电子发票订单ID
     */
    private String eInvoiceId;
    /**
     * 电子发票链接
     */
    private String eInvoiceLink;
    /**
     * 开据电子发票失败原因
     */
    private String eInvoiceError;
    /**
     * 冲红电子发票开据状态 0未开票 1开票成功 2开票中 3开票失败
     */
    private String redEInvoiceStatus;
    /**
     * 冲红电子发票订单ID
     */
    private String redEInvoiceId;
    /**
     * 冲红电子发票链接
     */
    private String redEInvoiceLink;
    /**
     * 冲红电子发票失败原因
     */
    private String redEInvoiceError;
    /**
     * 服务名称
     */
    private String service;

}