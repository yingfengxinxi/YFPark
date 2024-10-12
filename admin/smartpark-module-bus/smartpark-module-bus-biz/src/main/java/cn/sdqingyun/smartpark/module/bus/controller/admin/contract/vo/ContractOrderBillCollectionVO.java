package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

/**
 * 机构合同账单明细 DO
 *
 * @author 智慧园区管理员
 */
@Data
public class ContractOrderBillCollectionVO {
    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 合同id
     */
    @Schema(description = "合同id")
    private Long contractId;
    /**
     * 费用类型0=保证金1=租金3=物业费保证金4=物业费
     */
    @Schema(description = " 费用类型")
    private String feeType;

    /**
     * 状态0=未支付1=已支付2=逾期
     */
    @Schema(description = "状态")
    private String billStatus;

    /**
     * 应收/付金额
     */
    @Schema(description = "应收/付金额")
    private String receivablePayableAmount;


    @Schema(description = "付款日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payDate;

    /**
     * 还款周期开始时间
     */
    @Schema(description = "还款周期开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payStartDate;

    /**
     * 还款周期结束时间
     */
    @Schema(description = "还款周期结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payEndDate;

    /**
     * 实收金额
     */
    @Schema(description = "实收金额")
    private String receivablePayment;

    @Schema(description = "本次收款金额")
    private String thisPayment;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    @Schema(description = "收款时间")
    private Date incomeDate;
}