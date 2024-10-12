package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产耗材业务记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyStuffLogPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "28132")
    private Long orgId;

    @Schema(description = "耗材id", example = "26088")
    private Long stuffId;

    @Schema(description = "仓库id", example = "4563")
    private Long depositoryId;

    @Schema(description = "用户uid", example = "8490")
    private Long userId;

    @Schema(description = "业务")
    private String business;

    @Schema(description = "类型", example = "2")
    private String type;

    @Schema(description = "使用数量")
    private BigDecimal num;

    @Schema(description = "附加信息")
    private String extraData;

    @Schema(description = "创建人uid", example = "22713")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "9085")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}