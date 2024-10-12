package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 机构流水账单中间表【匹配】 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrgBillStreamMiddleRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9041")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "账单id", example = "18631")
    @ExcelProperty("账单id")
    private Long billId;

    @Schema(description = "账单类型", example = "2")
    @ExcelProperty("账单类型")
    private Long billType;

    @Schema(description = "流水id", example = "30855")
    @ExcelProperty("流水id")
    private Long streamId;

    @Schema(description = "公司id", example = "20316")
    @ExcelProperty("公司id")
    private Long companyId;

    @Schema(description = "公司名称(收款/付款地方单位名称)", example = "王五")
    @ExcelProperty("公司名称(收款/付款地方单位名称)")
    private String companyName;

    @Schema(description = "费用类型", example = "1")
    @ExcelProperty("费用类型")
    private String costType;

    @Schema(description = "入账日期")
    @ExcelProperty("入账日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date incomeDate;

    @Schema(description = "发生额")
    @ExcelProperty("发生额")
    private BigDecimal amount;

    @Schema(description = "已匹配金额", example = "9054")
    @ExcelProperty("已匹配金额")
    private BigDecimal matchPrice;

    @Schema(description = "未匹配金额", example = "11869")
    @ExcelProperty("未匹配金额")
    private BigDecimal nomatchPrice;

    @Schema(description = "匹配日期")
    @ExcelProperty("匹配日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date matchDate;

    @Schema(description = "取消匹配日期")
    @ExcelProperty("取消匹配日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date cancelMatchDate;


    @Schema(description = "匹配状态;1=完全匹配;2=未匹配;3=部分匹配;", example = "1")
    @ExcelProperty("匹配状态;1=完全匹配;2=未匹配;3=部分匹配;")
    private String matchStatus;

    @Schema(description = "是否确认;1=待确认;2=已确认;")
    @ExcelProperty("是否确认;1=待确认;2=已确认;")
    private String isConfirm;

    @Schema(description = "确认时间")
    @ExcelProperty("确认时间")
    private LocalDateTime confirmTime;

    @Schema(description = "确认人员uid", example = "4692")
    @ExcelProperty("确认人员uid")
    private Integer confirmUid;

    @Schema(description = "取消确认时间")
    @ExcelProperty("取消确认时间")
    private LocalDateTime unconfirmTime;

    @Schema(description = "取消确认人员uid", example = "24216")
    @ExcelProperty("取消确认人员uid")
    private Integer unconfirmUid;

    @Schema(description = "操作权限")
    @ExcelProperty("操作权限")
    private String operate;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}