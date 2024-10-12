package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 预约场地分类 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ResvPlaceCategoryRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "8475")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11652")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应用标识")
    private String appSign;

    @Schema(description = "所属项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4008")
    @ExcelProperty("所属项目ID")
    private Long villageId;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("分类名称")
    private String name;

    @Schema(description = "分类图片", example = "https://xxx")
    @ExcelProperty("分类图片")
    private String imageUrl;

    @Schema(description = "预约限制")
    @ExcelProperty("预约限制")
    private String reservationRule;

    @Schema(description = "场地可容纳人数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("场地可容纳人数")
    private Integer capacity;

    @Schema(description = "联系人电话")
    @ExcelProperty("联系人电话")
    private String contactMobile;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "详细介绍", example = "你说的对")
    @ExcelProperty("详细介绍")
    private String description;

    @Schema(description = "冗余字段")
    @ExcelProperty("冗余字段")
    private String cache;

    @Schema(description = "状态: 1为开启 0为关闭", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态: 1为开启 0为关闭")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "二维码路径")
    private String qrCode;
}