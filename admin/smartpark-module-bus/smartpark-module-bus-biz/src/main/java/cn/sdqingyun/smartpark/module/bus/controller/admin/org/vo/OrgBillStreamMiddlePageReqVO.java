package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 机构流水账单中间表【匹配】分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrgBillStreamMiddlePageReqVO extends PageParam {

    @Schema(description = "账单id", example = "18631")
    private Long billId;

    @Schema(description = "账单类型", example = "2")
    private Long billType;

    @Schema(description = "流水id", example = "30855")
    private Long streamId;

    @Schema(description = "公司id", example = "20316")
    private Long companyId;

    @Schema(description = "公司名称(收款/付款地方单位名称)", example = "王五")
    private String companyName;

    @Schema(description = "费用类型", example = "1")
    private String costType;

    @Schema(description = "入账日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date incomeDate;

    @Schema(description = "发生额")
    private BigDecimal amount;

    @Schema(description = "已匹配金额", example = "9054")
    private BigDecimal matchPrice;

    @Schema(description = "未匹配金额", example = "11869")
    private BigDecimal nomatchPrice;

    @Schema(description = "匹配日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date matchDate;

    @Schema(description = "取消匹配日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date cancelMatchDate;

    @Schema(description = "匹配状态;1=完全匹配;2=未匹配;3=部分匹配;", example = "1")
    private String matchStatus;

    @Schema(description = "是否确认;1=待确认;2=已确认;")
    private String isConfirm;

    @Schema(description = "确认时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date confirmTime;

    @Schema(description = "确认人员uid", example = "4692")
    private Integer confirmUid;

    @Schema(description = "取消确认时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date unconfirmTime;

    @Schema(description = "取消确认人员uid", example = "24216")
    private Integer unconfirmUid;

    @Schema(description = "操作权限")
    private String operate;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}