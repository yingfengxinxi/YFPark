package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * @Author lvzy
 * @Date 2024/9/11 18:54
 */

@Data
public class OrderRecordDetailVO {

    @Schema(description = "订单类型", example = "1")
    @ExcelProperty("订单类型")
    private String orderTypeName;

    @Schema(description = "订单编号")
    @ExcelProperty("订单编号")
    private String orderNumber;

    @Schema(description = "支付金额")
    @ExcelProperty("支付金额")
    private BigDecimal paymentAmount;

    @Schema(description = "支付时间")
    @ExcelProperty("支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date paymentTime;


    @Schema(description = "支付方式")
    @ExcelProperty("支付方式")
    private String payMethod;

    @Schema(description = "创建时间【下单时间】")
    @ExcelProperty("创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime createTime;

    @Schema(description = "下单用户")
    private String creator;

    @Schema(description = "订单状态;1=待支付;2=已支付;3=已退款;4=已取消;", example = "1")
    @ExcelProperty("订单状态;1=待支付;2=已支付;3=已退款;4=已取消;")
    private String orderStatusName;

    @Schema(description = "退款状态;1=部分;2=全额;", example = "1")
    @ExcelProperty("退款状态;1=部分;2=全额;")
    private String refundStatusName;

    @Schema(description = "工单状态", example = "1")
    @ExcelProperty("工单状态")
    private String workOrderStatusName;

    @Schema(description = "工单编号", example = "1")
    @ExcelProperty("工单编号")
    private String workOrderNumber;

    @Schema(description = "保修地址", example = "1")
    @ExcelProperty("保修地址")
    private String warrantyAddress;

    @Schema(description = "租客名称", example = "2")
    @ExcelProperty("租客名称")
    private String ownerName;

    @Schema(description = "报修类型", example = "2")
    @ExcelProperty("报修类型")
    private String repairType;

    @Schema(description = "工单分类", example = "10117")
    private String subcatName;

}
