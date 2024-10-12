package cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 支付订单和账单表中间表	分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayOrderBillPageReqVO extends PageParam {

    @Schema(description = "账单明细id", example = "20386")
    private Long billId;

    @Schema(description = "商户订单编号", example = "16311")
    private String merchantOrderId;

    @Schema(description = "支付金额，单位：分", example = "16778")
    private Integer price;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}