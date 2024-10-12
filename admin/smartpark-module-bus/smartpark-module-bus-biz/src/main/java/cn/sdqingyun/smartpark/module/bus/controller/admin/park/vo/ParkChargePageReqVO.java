package cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 停车场收费标准分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ParkChargePageReqVO extends PageParam {

    @Schema(description = "收费标准名称", example = "赵六")
    private String chargeName;

    @Schema(description = "停车场ID", example = "4343")
    private Long parkId;

    @Schema(description = "项目ID", example = "31704")
    private Long villageId;

    @Schema(description = "适用车的类型（1蓝牌、黄牌等，参照D1）", example = "2")
    private String carType;

    @Schema(description = "月租费用（null不支持缴费）")
    private BigDecimal monthFee;

    @Schema(description = "月租车到期后类型（0临时车（可能不允许进入），1储值车）", example = "1")
    private String monthOverdueType;

    @Schema(description = "月租车自主缴费能输入月数（0不允许，1允许）")
    private String monthRechargeInput;

    @Schema(description = "月租车自主缴费套餐")
    private String monthRechargePackage;

    @Schema(description = "生效日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date effectiveDate;

    @Schema(description = "临时车免费时长（分钟）")
    private Integer freeMinute;

    @Schema(description = "临时车免费出场时长（分钟）")
    private Integer freeOutMinute;

    @Schema(description = "临时车0标准收费，1按次收费，2按白天夜间收费，3按设定时间收费", example = "2")
    private String chargeType;

    @Schema(description = "临时车每日最高费用（0）不限")
    private BigDecimal dayMaxFee;

    @Schema(description = "临时车收费细则")
    private String chargeDetail;

    @Schema(description = "是否默认,1是0否")
    private String isDefault;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}