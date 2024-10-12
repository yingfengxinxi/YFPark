package cn.sdqingyun.smartpark.module.bus.controller.admin.pay.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 支付订单和账单表中间表	 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PayOrderBillRespVO {

    @Schema(description = "支付订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10065")
    @ExcelProperty("支付订单编号")
    private Long id;

    @Schema(description = "账单明细id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20386")
    @ExcelProperty("账单明细id")
    private Long billId;

    @Schema(description = "商户订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "16311")
    @ExcelProperty("商户订单编号")
    private String merchantOrderId;

    @Schema(description = "支付金额，单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "16778")
    @ExcelProperty("支付金额，单位：分")
    private Integer price;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}