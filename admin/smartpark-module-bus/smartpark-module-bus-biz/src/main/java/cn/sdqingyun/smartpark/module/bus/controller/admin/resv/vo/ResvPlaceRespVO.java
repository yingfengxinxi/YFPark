package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 预约场地 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ResvPlaceRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13103")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18970")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应用标识")
    private String appSign;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10520")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27324")
    @ExcelProperty("分类ID")
    private Long categoryId;

    @Schema(description = "场地名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("场地名称")
    private String name;

    @Schema(description = "场地图片")
    @ExcelProperty("场地图片")
    private String images;

    @Schema(description = "场地地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("场地地址")
    private String address;

    @Schema(description = "地址的冗余字段 用于前端回显")
    @ExcelProperty("地址的冗余字段 用于前端回显")
    private String addressRest;

    @Schema(description = "场地纬度")
    @ExcelProperty("场地纬度")
    private String latitude;

    @Schema(description = "场地经度")
    @ExcelProperty("场地经度")
    private String longitude;

    @Schema(description = "场地设施")
    @ExcelProperty("场地设施")
    private String facilityArr;

    @Schema(description = "收费规则ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30728")
    @ExcelProperty("收费规则ID")
    private Long billRuleId;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "场地预约成功、通知的员工id")
    @ExcelProperty("场地预约成功、通知的员工id")
    private String notifier;

    @Schema(description = "通知人json回显")
    @ExcelProperty("通知人json回显")
    private String notifierData;

    @Schema(description = "详细介绍", example = "随便")
    @ExcelProperty("详细介绍")
    private String description;

    @Schema(description = "状态: 1为开启 0为关闭", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态: 1为开启 0为关闭")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 规则名称
     */
    private String ruleName;
    /**
     * 收费标准
     */
    private String chargeStandard;
    /**
     * 是否启用多时间收费
     */
    private Integer isMultiTimeCharge;
    /**
     * 多时间收费收费标准
     */
    private String multiTimeChargeStandard;
    /**
     * 场地可容纳人数
     */
    private Integer capacity;
}