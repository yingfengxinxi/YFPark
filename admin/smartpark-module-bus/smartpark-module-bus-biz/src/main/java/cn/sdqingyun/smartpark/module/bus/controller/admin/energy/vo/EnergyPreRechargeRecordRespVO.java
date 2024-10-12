package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 水电表预充值记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyPreRechargeRecordRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15994")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构iD", requiredMode = Schema.RequiredMode.REQUIRED, example = "22388")
    @ExcelProperty("机构iD")
    private Long orgId;

    @Schema(description = "水电表id", example = "987")
    @ExcelProperty("水电表id")
    private Long energyId;

    @Schema(description = "充值金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "14820")
    @ExcelProperty("充值金额")
    private BigDecimal rechargePrice;

    @Schema(description = "充值度数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("充值度数")
    private BigDecimal rechargeDegree;

    @Schema(description = "账单id", example = "13315")
    @ExcelProperty("账单id")
    private Long billId;

    @Schema(description = "流水id", example = "8400")
    @ExcelProperty("流水id")
    private Long billStreamId;

    @Schema(description = "流水号")
    @ExcelProperty("流水号")
    private String streamNumber;

    @Schema(description = "账单编号")
    @ExcelProperty("账单编号")
    private String billNumber;

    @Schema(description = "设备上报返回的数据")
    @ExcelProperty("设备上报返回的数据")
    private String result;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}