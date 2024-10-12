package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.util.Date;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
/**
 * @Author lvzy
 * @Date 2024/9/29 20:00
 */
@Data
public class EnergyListPageVO{

    private Long energyRecordId;

    private Long energyId;

    private Long type;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21680")
    private Long villageId;

    @Schema(description = "楼宇ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8264")
    private Long buildId;

    @Schema(description = "名称", example = "李四")
    private String name;

    @Schema(description = "表具编号")
    private String number;

    @Schema(description = "用途 1分表 2总表 3公摊表", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String purpose;

    @Schema(description = "房号id")
    private String roomIds;

    @Schema(description = "绑定位置", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bindingLocation;

    @Schema(description = "付费类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String paymentType;
    private String paymentTypeName;


    @Schema(description = "剩余电量/水量", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal currentRemaining;

    @Schema(description = "本期抄表时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date thisTime;

    @Schema(description = "本期读数", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal thisNumber;

    @Schema(description = "上期读数", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal lastNumber;

    @Schema(description = "本期用量", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal thisUse;

    @Schema(description = "本期负责人id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long leadUid;

    @Schema(description = "本期负责人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String leadName;

    @Schema(description = "绑定设备", requiredMode = Schema.RequiredMode.REQUIRED)
    private String propertyName;

    @Schema(description = "状态，1启动2作废", example = "2")
    private String statuss;
    private String statusName;
}
