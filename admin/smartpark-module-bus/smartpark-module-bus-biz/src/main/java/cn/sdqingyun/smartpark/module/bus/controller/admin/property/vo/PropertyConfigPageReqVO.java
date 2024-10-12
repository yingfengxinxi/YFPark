package cn.sdqingyun.smartpark.module.bus.controller.admin.property.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产配置信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyConfigPageReqVO extends PageParam {

    @Schema(description = "机构id", example = "922")
    private Long orgId;

    @Schema(description = "绑定的项目楼宇信息json")
    private String buildBind;

    @Schema(description = "资产编码规则json;")
    private String numberRule;

    @Schema(description = "附属信息json;")
    private String extra;

    @Schema(description = "创建人uid", example = "23747")
    private Long cuserUid;

    @Schema(description = "修改人uid", example = "20854")
    private Long muserUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}