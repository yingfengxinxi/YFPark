package cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

/**
 * 机构合同账单明细 DO
 *
 * @author 智慧园区管理员
 */
@TableName("contract_order_bill")
@KeySequence("contract_order_bill_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractOrderBillDO extends BaseDO {

    @TableField(exist = false)
    private String flag;
    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 合同id
     */
    private Long contractId;
    /**
     * 明细编号
     */
    private String orderNumber;
    /**
     * 还款期数
     */
    private Integer number;
    /**
     * 还款周期开始时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payStartDate;

    @TableField(exist = false)
    private String payStartDateStr;
    /**
     * 还款周期结束时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payEndDate;

    @TableField(exist = false)
    private String payEndDateStr;
    /**
     * 费用类型
     */
    private String feeType;

    /**
     * 费用类型名称
     */
    @TableField(exist = false)
    private String costTypeName;
    /**
     * 付款日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payDate;

    @TableField(exist = false)
    private String payDateStr;
    /**
     * 最终单价(含税)
     */
    private String finalUnitPrice;
    /**
     * 应收(含税)
     */
    private String receivable;
    /**
     * 应收/付金额
     */
    @TableField(exist = false)
    private String receivablePayableAmount;

    /**
     * 实收金额
     */
    private String receivablePayment;
    /**
     * 税额
     */
    private String taxAmount;
    /**
     * 状态0=未支付1=已支付2=逾期
     */
    private String billStatus;

    @TableField(exist = false)
    private String billStatusName;
    /**
     * 支付时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date payTime;

    /**
     * 第几次账单
     */
    private Integer count;

    @Schema(description = "账单类型;1=收款(机构收款);2=付款(机构付款);")
    private String billType;


    @Schema(description = "滞纳金(应收滞纳金)")
    private String lateFee;

    @Schema(description = "滞纳金(产生滞纳金【调整滞纳金，当前字段值不会改变】)")
    private String generateLateFee;

    @Schema(description = "滞纳金付款状态0=未产生滞纳金1=未支付2=已结清")
    private String lateFeePayStatus;

    @Schema(description = "逾期天数")
    private Integer overdueDay;

    @Schema(description = "账单来源0=系统生成1=手动添加2=导入")
    private String dataSource;

    @Schema(description = "是否催缴")
    @TableField(exist = false)
    private Integer isCs;

    @Schema(description = "滞纳金起算天数")
    private Integer startingLateFeeDay;

    @Schema(description = "滞纳金比例%")
    private String lateFeeRatio;

    @Schema(description = "滞纳金上限%")
    private String upperLimitLateFee;


    @TableField(exist = false)
    @Schema(description = "合同编号")
    private String contractNumber;

    @TableField(exist = false)
    @Schema(description = "付款人")
    private String payName;

    @TableField(exist = false)
    @Schema(description = "经办人")
    private String operatorName;

    @Schema(description = "账单条款类型1=租赁条款;2=物业条款")
    private String clauseType;

    @Schema(description = "账单来源;1=合同账单;2=物业管理账单;3=已关联合")
    private String billSource;

    @Schema(description = "税率")
    private String taxRate;

    @Schema(description = "账单备注")
    private String remark;

    @Schema(description = "账单关闭状态;0=关闭;1=开启;")
    private String closeStatus;
}