package cn.sdqingyun.smartpark.module.bus.controller.admin.resv.vo;

import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 预约场地分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResvPlacePageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "18970")
    private Long orgId;

    @Schema(description = "应用标识")
    private String appSign;

    @Schema(description = "项目ID", example = "10520")
    private Long villageId;

    @Schema(description = "分类ID", example = "27324")
    private Long categoryId;

    @Schema(description = "场地名称", example = "张三")
    private String name;

    @Schema(description = "场地图片")
    private String images;

    @Schema(description = "场地地址")
    private String address;

    @Schema(description = "地址的冗余字段 用于前端回显")
    private String addressRest;

    @Schema(description = "场地纬度")
    private String latitude;

    @Schema(description = "场地经度")
    private String longitude;

    @Schema(description = "场地设施")
    private String facilityArr;

    @Schema(description = "收费规则ID", example = "30728")
    private Long billRuleId;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "场地预约成功、通知的员工id")
    private String notifier;

    @Schema(description = "通知人json回显")
    private String notifierData;

    @Schema(description = "详细介绍", example = "随便")
    private String description;

    @Schema(description = "状态: 1为开启 0为关闭", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}