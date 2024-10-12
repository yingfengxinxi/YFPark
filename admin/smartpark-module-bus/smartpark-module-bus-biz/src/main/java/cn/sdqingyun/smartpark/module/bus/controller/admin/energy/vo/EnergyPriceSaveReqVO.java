package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

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

@Schema(description = "管理后台 - 自定义价格标准新增/修改 Request VO")
@Data
public class EnergyPriceSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "16338")
    private Long id;

    @Schema(description = "设备表种类", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "设备表种类不能为空")
    private String type;

    @Schema(description = "机构ID", example = "5190")
    private Long orgId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24193")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "楼宇ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "93")
    @NotNull(message = "楼宇ID不能为空")
    private Long buildId;

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "生效时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date effectDate;

    @Schema(description = "是否阶梯价 1是 0否", requiredMode = Schema.RequiredMode.REQUIRED, example = "32696")
    @NotEmpty(message = "是否阶梯价 1是 0否不能为空")
    private String isStagePrice;

    @Schema(description = "税率", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "税率不能为空")
    private BigDecimal taxRate;

    @Schema(description = "单价标准，json存储", example = "2813")
    private String unitPrice;

    @Schema(description = "授予用户管理的项目和楼宇，json存储")
    private String builds;

    @Schema(description = "绑定房间信息，1,2,3")
    private String roomIds;

    @Schema(description = "滞纳金比例")
    private String ratio;

    @Schema(description = "起算天数")
    private String startDay;

    @Schema(description = "滞纳金上限")
    private String toplimit;

    @Schema(description = "状态，1启动，0审核中，4禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String status;

}