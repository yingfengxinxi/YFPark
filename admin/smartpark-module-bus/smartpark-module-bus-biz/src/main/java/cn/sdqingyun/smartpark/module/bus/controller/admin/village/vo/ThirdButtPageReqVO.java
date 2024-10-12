package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 第三方数据对接（目前用于智慧社区系统，全功能版）分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ThirdButtPageReqVO extends PageParam {

    @Schema(description = "第三方业务类别", example = "2")
    private String businessType;

    @Schema(description = "第三方业务ID", example = "424")
    private String businessId;

    @Schema(description = "自有数据ID", example = "17673")
    private String selfId;

    @Schema(description = "数据原有返回格式")
    private String originalData;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}