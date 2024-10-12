package cn.sdqingyun.smartpark.module.bus.controller.admin.org.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 机构收入分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrgIncomePageReqVO extends PageParam {

    @Schema(description = "项目id", example = "15090")
    private Long villageId;

    @Schema(description = "合同id", example = "13133")
    private Long contractId;

    @Schema(description = "账单id", example = "331")
    private Long billId;

    @Schema(description = "账单类型;1=收款;2=付款;", example = "1")
    private String billType;

    @Schema(description = "子流水id", example = "23695")
    private Long streamMiddleId;

    @Schema(description = "合同编号")
    private String contractNumber;

    @Schema(description = "楼宇id", example = "20909")
    private Long buildId;

    @Schema(description = "租客所属行业ID", example = "13184")
    private Long industryId;

    @Schema(description = "楼层-房间号")
    private String roomNumber;

    @Schema(description = "租客id", example = "10216")
    private Long ownerId;

    @Schema(description = "收入类型;1=应收(含税);2=应收(不含税);3=实收(含税);4=实收(不含税);", example = "2")
    private String incomeType;

    @Schema(description = "是否确认;1=当月待确认;1=当月已确认;")
    private String isConfirm;

    @Schema(description = "确认时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date confirmTime;

    @Schema(description = "确认人员uid", example = "23981")
    private String confirmUid;

    @Schema(description = "取消确认时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private String unconfirmTime;

    @Schema(description = "取消确认人员uid", example = "27643")
    private String unconfirmUid;

    @Schema(description = "合同费用类型", example = "2")
    private String contractCostType;

    @Schema(description = "租客费用类型", example = "1")
    private String ownerCostType;

    @Schema(description = "账单费用类型", example = "1")
    private String costType;

    @Schema(description = "交易金额")
    private BigDecimal tradeAmount;

    @Schema(description = "含/不含税金额")
    private BigDecimal amount;

    @Schema(description = "税额")
    private BigDecimal taxAmount;

    @Schema(description = "交易时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date tradeTime;

    @Schema(description = "房屋虚拟标识.0=真实房屋;1=非真实房屋")
    private String isUnrealRoom;

    @Schema(description = "虚拟房屋信息")
    private String unrealRoom;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}