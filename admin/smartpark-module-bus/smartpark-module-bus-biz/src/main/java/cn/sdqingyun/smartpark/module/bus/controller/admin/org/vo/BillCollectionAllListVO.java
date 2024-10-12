package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * @Author lvzy
 * @Date 2024/7/16 10:49
 */
@Data
@ExcelIgnoreUnannotated
public class BillCollectionAllListVO extends PageParam {


    @Schema(description = "账单id")
    private Long billId;

    @Schema(description = "对方名称")
    @ExcelProperty(value = "对方名称")
    private String ownerName;
    //租客iD
    private Long ownerId;

    @Schema(description = "合同id")
    private Long contractId;


    @Schema(description = "是否个人，1个人，0公司")
    private Integer isPersonal;


    @Schema(description = "楼宇名称")
    @ExcelProperty(value = "楼宇名称")
    private String buildName;

    @Schema(description = "楼宇Id")
    private Long buildId;

    @Schema(description = "合同编号")
    @ExcelProperty(value = "合同编号")
    private String contractNumber;


    @Schema(description = "账单状态集合")
    private List<String> billStatusList;
    private String billStatus;

    @ExcelProperty(value = "账单状态")
    @Schema(description = "账单状态")
    private String billStatusName;


    @Schema(description = "费用类型")
    private String feeType;

    @ExcelProperty(value = "费用类型")
    private String feeTypeName;

    @Schema(description = "租赁数")
    @ExcelProperty(value = "租赁数")
    private String leaseArea;

    @Schema(description = "缴费通知单")
    @ExcelProperty(value = "缴费通知单")
    private String noticeCount;

    @Schema(description = "账单来源")
    private String billSource;

    @ExcelProperty(value = "账单来源")
    private String billSourceName;


    @Schema(description = "账单类型")
    private String billType;

    @ExcelProperty(value = "账单类型")
    private String billTypeName;


    @Schema(description = "应收金额")
    @ExcelProperty(value = "应收金额")
    private BigDecimal receivable;

    /**
     * 滞纳金
     */
    private BigDecimal lateFee;

    @Schema(description = "实收金额")
    @ExcelProperty(value = "实收金额")
    private BigDecimal receivablePayment;

    @Schema(description = "税额")
    @ExcelProperty(value = "税额")
    private BigDecimal taxAmount;

    @Schema(description = "开始缴费周期")
    @ExcelProperty(value = "开始缴费周期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payStartDate;


    @Schema(description = "结束缴费周期")
    @ExcelProperty(value = "结束缴费周期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payEndDate;

    @Schema(description = "应交时间")
    @ExcelProperty(value = "应交时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payDate;

    @Schema(description = "调增金额")
    @ExcelProperty(value = "调增金额")
    private BigDecimal jiaAdjustPrice;


    @Schema(description = "调减金额")
    @ExcelProperty(value = "调减金额")
    private BigDecimal jianAdjustPrice;

    @Schema(description = "开始应收/付时间")
    private String startPayDate;

    @Schema(description = "结束应收/付时间")
    private String endPayDate;

    @Schema(description = "开始创建时间")
    private String startCreateTime;

    @Schema(description = "结束创建时间")
    private String endCreateTime;

    @Schema(description = "合同状态集合")
    private List<String> contractStatusList;

    @Schema(description = "发票状态集合")
    private List<String> invoiceStatusList;

    @Schema(description = "园区id集合")
    private List<Long> parkIdList;

    @Schema(description = "楼宇id集合")
    private List<Long> buildIdList;

    @Schema(description = "已显示未到应收期账单")
    private Boolean isShow;

    //逾期天数
    private Long overdueDay;
}
