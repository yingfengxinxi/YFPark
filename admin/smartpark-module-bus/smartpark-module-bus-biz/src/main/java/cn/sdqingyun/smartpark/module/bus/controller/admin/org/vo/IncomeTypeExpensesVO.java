package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lvzy
 * @Date 2024/7/25 16:06
 */
@Data
public class IncomeTypeExpensesVO {

    private String costType;
    private String costTypeName;
    private String matchDate;
    private BigDecimal tradeAmount;
}
