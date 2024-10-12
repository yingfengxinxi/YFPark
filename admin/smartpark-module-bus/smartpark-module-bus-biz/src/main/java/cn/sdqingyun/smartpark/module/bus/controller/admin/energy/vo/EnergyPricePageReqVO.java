package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 自定义价格标准分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyPricePageReqVO extends PageParam {

    @Schema(description = "设备表种类", example = "2")
    private String type;

    @Schema(description = "机构ID", example = "5190")
    private Long orgId;

    @Schema(description = "项目ID", example = "24193")
    private Long villageId;

    @Schema(description = "楼宇ID", example = "93")
    private Long buildId;

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "生效时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date effectDate;

    @Schema(description = "是否阶梯价 1是 0否", example = "32696")
    private String isStagePrice;

    @Schema(description = "倍率")
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

    @Schema(description = "状态，1启动，0审核中，4禁用", example = "2")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}