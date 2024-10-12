package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 预约场地分类分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResvPlaceCategoryPageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "11652")
    private Long orgId;

    @Schema(description = "应用标识")
    private String appSign;

    @Schema(description = "所属项目ID", example = "4008")
    private Long villageId;

    @Schema(description = "分类名称", example = "李四")
    private String name;

    @Schema(description = "分类图片", example = "https://xxx")
    private String imageUrl;

    @Schema(description = "预约限制")
    private String reservationRule;

    @Schema(description = "场地可容纳人数")
    private Integer capacity;

    @Schema(description = "联系人电话")
    private String contactMobile;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "详细介绍", example = "你说的对")
    private String description;

    @Schema(description = "冗余字段")
    private String cache;

    @Schema(description = "状态: 1为开启 0为关闭", example = "2")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}