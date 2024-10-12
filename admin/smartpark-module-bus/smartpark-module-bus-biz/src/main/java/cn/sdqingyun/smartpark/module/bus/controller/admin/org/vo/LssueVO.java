package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptSellerDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

/**
 * @Author lvzy
 * @Date 2024/7/19 14:38
 */
@Data
public class LssueVO {

    @Schema(description = "编号规则id")
    private Long receiptRuleId;

    @Schema(description = "编号规则名称")
    private String receiptRuleName;

    @Schema(description = "楼宇id")
    private Long build;

    @Schema(description = "账单id")
    private Long billId;

    @Schema(description = "收据编号")
    private String receiptNumber;

    @Schema(description = "开据日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date issuerTime;

    @Schema(description = "汇款方式")
    private String remitType;

    @Schema(description = "可开据金额")
    private BigDecimal invoicedAmount;

    @Schema(description = "申请可开据金额")
    private BigDecimal applicationInvoicedAmount;

    @Schema(description = "费用类型")
    private String costType;

    @Schema(description = "费用类型名称")
    private String costTypeName;

    @Schema(description = "费用名称")
    private String costName;

    @Schema(description = "收款单位")
    private Long collectionCompanyId;

    @Schema(description = "收款单位下拉")
    private List<OrgBillReceiptSellerDO> payeeUnitList;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "交款方-交款单位Id")
    private Long paymentCompanyId;

    @Schema(description = "交款方-交款单位名称")
    private String paymentCompany;


    @Schema(description = "交款方-交款人")
    private String paymentUname;

    @Schema(description = "交款方-交款地址")
    private String paymentAddress;


    @Schema(description = "交款方-电话")
    private String paymentPhone;

    @Schema(description = "收款方-收款人")
    private String collectionUname;

    @Schema(description = "收款方-地址")
    private String collectionAddress;

    @Schema(description = "收款方-电话")
    private String collectionPhone;

    /**
     * 生成编号方式;1=规则生成;2=自定义
     */
    private String numberType;
}
