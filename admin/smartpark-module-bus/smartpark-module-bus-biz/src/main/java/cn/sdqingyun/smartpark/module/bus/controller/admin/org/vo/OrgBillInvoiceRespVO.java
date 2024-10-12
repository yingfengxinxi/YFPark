package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 机构账单开票记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgBillInvoiceRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12242")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "项目id", example = "5528")
    @ExcelProperty("项目id")
    private Long villageId;

    @Schema(description = "楼宇id", example = "2181")
    @ExcelProperty("楼宇id")
    private Long buildId;

    @Schema(description = "账单id", example = "5616")
    @ExcelProperty("账单id")
    private Long billId;

    @Schema(description = "子账单ID", example = "690")
    @ExcelProperty("子账单ID")
    private Long subbillId;

    @Schema(description = "绑定楼宇信息json")
    @ExcelProperty("绑定楼宇信息json")
    private String buildBind;

    @Schema(description = "房号json")
    @ExcelProperty("房号json")
    private String roomNumber;

    @Schema(description = "售方id", example = "21411")
    @ExcelProperty("售方id")
    private Long sellerId;

    @Schema(description = "买方(租客)id", example = "6654")
    @ExcelProperty("买方(租客)id")
    private Long ownerId;

    @Schema(description = "购方名称", example = "张三")
    @ExcelProperty("购方名称")
    private String buyerName;

    @Schema(description = "购方(租客)开户行账号", example = "4390")
    @ExcelProperty("购方(租客)开户行账号")
    private String buyerAccount;

    @Schema(description = "货物类型;1=停车费;2=租金;", example = "1")
    @ExcelProperty("货物类型;1=停车费;2=租金;")
    private String goodsType;

    @Schema(description = "账单费用类型", example = "1")
    @ExcelProperty("账单费用类型")
    private String costType;

    @Schema(description = "开票时间")
    @ExcelProperty("开票时间")
    private LocalDateTime invoiceTime;

    @Schema(description = "发票状态;1=待开票;2=已开票;3=已冲红;4=已作废;5=已开负数发票;6=作废审批中;", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("发票状态;1=待开票;2=已开票;3=已冲红;4=已作废;5=已开负数发票;6=作废审批中;")
    private String invoiceStatus;

    @Schema(description = "发票编号")
    @ExcelProperty("发票编号")
    private String invoiceNumber;

    @Schema(description = "开票记录金额", example = "5386")
    @ExcelProperty("开票记录金额")
    private BigDecimal recordPrice;

    @Schema(description = "已开票金额", example = "23819")
    @ExcelProperty("已开票金额")
    private BigDecimal invoicePrice;

    @Schema(description = "可开票金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("可开票金额")
    private BigDecimal canInvoiceAmount;

    @Schema(description = "申请开票金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("申请开票金额")
    private BigDecimal applyInvoiceAmount;

    @Schema(description = "税额")
    @ExcelProperty("税额")
    private BigDecimal taxAmount;

    @Schema(description = "税率")
    @ExcelProperty("税率")
    private Integer taxRate;

    @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "19957")
    @ExcelProperty("单价")
    private BigDecimal unitPrice;

    @Schema(description = "数量")
    @ExcelProperty("数量")
    private Integer total;

    @Schema(description = "商品或服务名称", example = "王五")
    @ExcelProperty("商品或服务名称")
    private String taxCostName;

    @Schema(description = "规格")
    @ExcelProperty("规格")
    private String specs;

    @Schema(description = "单位")
    @ExcelProperty("单位")
    private String invoiceUnit;

    @Schema(description = "购方开户行")
    @ExcelProperty("购方开户行")
    private String buyerBank;

    @Schema(description = "发票代码")
    @ExcelProperty("发票代码")
    private String invoiceCode;

    @Schema(description = "购方税号")
    @ExcelProperty("购方税号")
    private String buyerTaxNumber;

    @Schema(description = "购方电话")
    @ExcelProperty("购方电话")
    private String buyerPhone;

    @Schema(description = "购方地址")
    @ExcelProperty("购方地址")
    private String buyerAddress;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "未交税金额", example = "20878")
    @ExcelProperty("未交税金额")
    private BigDecimal nopayTaxPrice;

    @Schema(description = "价税合计")
    @ExcelProperty("价税合计")
    private BigDecimal taxPriceAmount;

    @Schema(description = "售方名称", example = "王五")
    @ExcelProperty("售方名称")
    private String salerName;

    @Schema(description = "售方开户行账号", example = "21307")
    @ExcelProperty("售方开户行账号")
    private String salerAccount;

    @Schema(description = "售方银行")
    @ExcelProperty("售方银行")
    private String salerBank;

    @Schema(description = "售方地址")
    @ExcelProperty("售方地址")
    private String salerAddress;

    @Schema(description = "售方电话")
    @ExcelProperty("售方电话")
    private String salerPhone;

    @Schema(description = "售方税号(纳税人识别号)")
    @ExcelProperty("售方税号(纳税人识别号)")
    private String salerTaxNumber;

    @Schema(description = "作废/红冲日期")
    @ExcelProperty("作废/红冲日期")
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date extraTime;

    @Schema(description = "开票类型;1=增值税普通发票;2=增值税专用发票;3=增值税电子普通发票;", example = "2")
    @ExcelProperty("开票类型;1=增值税普通发票;2=增值税专用发票;3=增值税电子普通发票;")
    private String invoiceType;

    @Schema(description = "开票批次凭证")
    @ExcelProperty("开票批次凭证")
    private String taskKey;

    @Schema(description = "分组批次凭证")
    @ExcelProperty("分组批次凭证")
    private String groupKey;

    @Schema(description = "操作开票类型;1=提前开票;2=实收开票", example = "2")
    @ExcelProperty("操作开票类型;1=提前开票;2=实收开票")
    private String issueType;

    @Schema(description = "冲红发票号码")
    @ExcelProperty("冲红发票号码")
    private String redInvoiceNumber;

    @Schema(description = "冲红发票代码")
    @ExcelProperty("冲红发票代码")
    private String redInvoiceCode;

    @Schema(description = "冲红发票开据时间")
    @ExcelProperty("冲红发票开据时间")
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date redInvoiceTime;

    @Schema(description = "是否需要开据电子发票 1需要 0不需要", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否需要开据电子发票 1需要 0不需要")
    private String canEInvoice;

    @Schema(description = "电子发票开据状态 0未开票 1开票成功 2开票中 3开票失败", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("电子发票开据状态 0未开票 1开票成功 2开票中 3开票失败")
    private String eInvoiceStatus;

    @Schema(description = "电子发票订单ID", example = "9326")
    @ExcelProperty("电子发票订单ID")
    private String eInvoiceId;

    @Schema(description = "电子发票链接")
    @ExcelProperty("电子发票链接")
    private String eInvoiceLink;

    @Schema(description = "开据电子发票失败原因")
    @ExcelProperty("开据电子发票失败原因")
    private String eInvoiceError;

    @Schema(description = "冲红电子发票开据状态 0未开票 1开票成功 2开票中 3开票失败", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("冲红电子发票开据状态 0未开票 1开票成功 2开票中 3开票失败")
    private String redEInvoiceStatus;

    @Schema(description = "冲红电子发票订单ID", example = "7366")
    @ExcelProperty("冲红电子发票订单ID")
    private String redEInvoiceId;

    @Schema(description = "冲红电子发票链接")
    @ExcelProperty("冲红电子发票链接")
    private String redEInvoiceLink;

    @Schema(description = "冲红电子发票失败原因")
    @ExcelProperty("冲红电子发票失败原因")
    private String redEInvoiceError;

    @Schema(description = "服务名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("服务名称")
    private String service;

    @Schema(description = "操作时间")
    @ExcelProperty("操作时间")
    private LocalDateTime createTime;

}