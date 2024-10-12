package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 预约核销 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ResvBookingVerificationRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21367")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15107")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应用标识")
    private String appSign;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31308")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("订单编号")
    private String orderNo;

    @Schema(description = "预约ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21438")
    @ExcelProperty("预约ID")
    private Long bookingId;

    @Schema(description = "核销码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("核销码")
    private String code;

    @Schema(description = "核销状态 0未核销 1已核销", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("核销状态 0未核销 1已核销")
    private Integer status;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20842")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "处理人")
    @ExcelProperty("处理人")
    private Long handler;

    @Schema(description = "处理人名称", example = "赵六")
    @ExcelProperty("处理人名称")
    private String handlerName;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private LocalDateTime handleTime;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "订单总额")
    private BigDecimal orderTotal;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "场地名称")
    private String placeName;

    @Schema(description = "预约人姓名")
    private String userName;

    @Schema(description = "用户手机号")
    private String userMobile;

    @Schema(description = "租客名称")
    private String ownerName;
    /**
     * 冗余信息 比如 分类信息 场地信息
     */
    private String rest;
}