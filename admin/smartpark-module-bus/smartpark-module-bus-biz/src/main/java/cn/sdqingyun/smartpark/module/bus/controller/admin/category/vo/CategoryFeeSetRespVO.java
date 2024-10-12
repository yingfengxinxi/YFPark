package cn.sdqingyun.smartpark.module.bus.controller.admin.category.vo;

import cn.hutool.json.JSONObject;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.category.CategoryFeeSetReservationDateDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 工单子类费用设置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CategoryFeeSetRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25196")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构id", example = "9721")
    @ExcelProperty("机构id")
    private Long orgId;

    @Schema(description = "应用标识")
    @ExcelProperty("应用标识")
    private String application;

    @Schema(description = "工单大类id", example = "866")
    @ExcelProperty("工单大类id")
    private Long categoryId;

    @Schema(description = "工单子类id", example = "17139")
    @ExcelProperty("工单子类id")
    private Long subcatId;


    /**
     * 启用时间段模式0=否1=是
     */
    @Schema(description = "启用时间段模式0=否1=是", example = "17139")
    @ExcelProperty("启用时间段模式0=否1=是")
    private String isUse;

    @Schema(description = "费用设置")
    @ExcelProperty("费用设置")
    private String feeSet;

    @Schema(description = "耗材设置")
    @ExcelProperty("耗材设置")
    private String stuffSet;

    @Schema(description = "服务项设置")
    @ExcelProperty("服务项设置")
    private String serviceSet;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    private List<CategoryFeeSetReservationDateDO> reservationDateList;

}