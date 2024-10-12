package cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 工单分类标签配置新增/修改 Request VO")
@Data
public class CategoryLabelSaveReqVO  extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7185")
    private Long id;

    @Schema(description = "机构ID", example = "16063")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "标签名称", example = "王五")
    private String name;

    @Schema(description = "工单子类id", example = "20282")
    private Long subcatId;

    @Schema(description = "排序值", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sort;

    @Schema(description = "最近操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date lastTime;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String status;

}