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

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

/**
 * @Author lvzy
 * @Date 2024/7/18 11:46
 */
@Data
@ExcelIgnoreUnannotated
public class BillTypeIncomeListPageVO extends PageParam {

    @Schema(description = "编号", example = "张三")
    private Long id;

    @Schema(description = "租客id", example = "1")
    private Long ownerId;

    @Schema(description = "是否个人，1个人，0公司")
    private Integer isPersonal;

    @Schema(description = "对方名称")
    @ExcelProperty("对方名称")
    private String ownerName;

    @Schema(description = "费用类型")
    @ExcelProperty("费用类型")
    private String costTypeName;

    @Schema(description = "应收金额")
    @ExcelProperty("应收金额")
    private BigDecimal receivable;

    @Schema(description = "匹配金额")
    @ExcelProperty("匹配金额")
    private BigDecimal matchPrice;

    @Schema(description = "楼宇名称")
    @ExcelProperty("楼宇名称")
    private String buildName;

    @Schema(description = "房间号")
    @ExcelProperty("房间号")
    private String roomNumber;


    @Schema(description = "账单编号")
    @ExcelProperty("账单编号")
    private String streamNumber;


    @Schema(description = "确认状态")
    @ExcelProperty("确认状态")
    private String isConfirm;

    @Schema(description = "账单状态")
    @ExcelProperty("账单状态")
    private String billStatus;

    @Schema(description = "账单Id")
    private Long billId;

    @Schema(description = "流水状态")
    @ExcelProperty("流水状态")
    private String matchStatus;

    @Schema(description = "开始费用周期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payStartDate;

    @Schema(description = "结束费用周期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date payEndDate;

    @Schema(description = "费用周期")
    @ExcelProperty("费用周期")
    private String costCycle;

    @Schema(description = "匹配日期")
    @ExcelProperty("匹配日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date matchDate;

    @Schema(description = "确认时间")
    @ExcelProperty("确认时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date confirmTime;

    @Schema(description = "确认人")
    @ExcelProperty("确认人")
    private String confirmUid;

    private String billType;
    private String startMonth;
    private String endMonth;
    private String contractStatus;
    private String matchMonth;
}
