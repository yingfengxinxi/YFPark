package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lvzy
 * @Date 2024/7/4 11:22
 */
@Data
public class OwnerIdStreamTotalMoneyVO {

    private Long id;
    private BigDecimal amount;
    private String matchStatus;
}
