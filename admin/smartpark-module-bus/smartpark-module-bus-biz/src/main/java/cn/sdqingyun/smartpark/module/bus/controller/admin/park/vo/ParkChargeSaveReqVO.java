package cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 停车场收费标准新增/修改 Request VO")
@Data
public class ParkChargeSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21775")
    private Long id;

    @Schema(description = "收费标准名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "收费标准名称不能为空")
    private String chargeName;

    @Schema(description = "停车场ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4343")
    @NotNull(message = "停车场ID不能为空")
    private Long parkId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31704")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "适用车的类型（1蓝牌、黄牌等，参照D1）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "适用车的类型（1蓝牌、黄牌等，参照D1）不能为空")
    private String carType;

    @Schema(description = "月租费用（null不支持缴费）")
    private BigDecimal monthFee;

    @Schema(description = "月租车到期后类型（0临时车（可能不允许进入），1储值车）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String monthOverdueType;

    @Schema(description = "【支持用户输入租赁月数】月租车自主缴费能输入月数（0不允许，1允许）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String monthRechargeInput;

    @Schema(description = "月租车自主缴费套餐")
    private String monthRechargePackage;

    @Schema(description = "生效日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "生效日期不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date effectiveDate;

    @Schema(description = "临时车免费停车时长（分钟）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "临时车免费停车时长（分钟）不能为空")
    private Integer freeMinute;

    @Schema(description = "临时车免费出场时长（分钟）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "临时车免费出场时长（分钟）不能为空")
    private Integer freeOutMinute;

    @Schema(description = "临时车收费标准类型0标准收费，1按次收费，2按白天夜间收费，3按设定时间收费", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "临时车收费标准类型0标准收费，1按次收费，2按白天夜间收费，3按设定时间收费不能为空")
    private String chargeType;

    @Schema(description = "临时车每日最高费用（0）不限", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal dayMaxFee;

    @Schema(description = "临时车收费细则", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "临时车收费细则不能为空")
    private String chargeDetail;

    @Schema(description = "是否默认,1是0否", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "是否默认,1是0否不能为空")
    private String isDefault;

}