package cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 支付订单和工单订单记录中间 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PayOrderWorkOrderRespVO {

    @Schema(description = "支付订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13372")
    @ExcelProperty("支付订单编号")
    private Long id;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应用标识")
    private String application;

    @Schema(description = "付款记录id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31793")
    @ExcelProperty("付款记录id")
    private Long workorderProposeOrderId;

    @Schema(description = "商户订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18348")
    @ExcelProperty("商户订单编号")
    private String merchantOrderId;

    @Schema(description = "支付金额，单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "19403")
    @ExcelProperty("支付金额，单位：分")
    private Integer price;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "订单类型0=支付1=退款", example = "1")
    @ExcelProperty("订单类型0=支付1=退款")
    private String orderType;

}