package cn.sdqingyun.smartpark.module.bus.controller.admin.contract.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 合同中选中房源分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractSelectedPropertiePageReqVO extends PageParam {

    @Schema(description = "合同id", example = "27164")
    private Long contractId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "园区id", example = "8477")
    private Long parkId;

    @Schema(description = "楼宇id", example = "27591")
    private Long buildId;

    @Schema(description = "房间id", example = "24755")
    private Long villageRoomId;

    @Schema(description = "已选房源租赁面积")
    private BigDecimal rentalArea;

}