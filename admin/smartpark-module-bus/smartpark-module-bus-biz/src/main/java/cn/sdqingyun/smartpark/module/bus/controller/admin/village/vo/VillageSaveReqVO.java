package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 社区新增/修改 Request VO")
@Data
public class VillageSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3871")
    private Long id;

    @Schema(description = "项目名称", example = "张三")
    private String name;

    @Schema(description = "项目名称缩写", example = "芋艿")
    private String shortName;

    @Schema(description = "项目介绍")
    private String describe;

    @Schema(description = "项目LOGO")
    private String logo;

    @Schema(description = "项目省市区地址")
    private String districtTxt;

    @Schema(description = "项目详细地址")
    private String address;

    @Schema(description = "落座经度（gcj02）")
    private BigDecimal lng;

    @Schema(description = "落座纬度（gcj02）")
    private BigDecimal lat;

    @Schema(description = "管理面积")
    private BigDecimal managementArea;

    @Schema(description = "可招商面积")
    private BigDecimal rentableArea;

    @Schema(description = "可招商房源数量", example = "31322")
    private Integer roomRentableCount;

    @Schema(description = "总房源数量", example = "1455")
    private Integer roomCount;

    @Schema(description = "标签（数组存储）")
    private String tagIdArr;

    @Schema(description = "项目公众号的微信号")
    private String wechatNumber;

    @Schema(description = "数据状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "数据状态不能为空")
    private Integer status;

    @Schema(description = "3D模型")
    private String threeDimensionalFile;

    @Schema(description = "3D模型物体ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13831")
    private String threeDimensionalId;

    @Schema(description = "楼宇图片")
    private String dimensionalBgImg;

    @Schema(description = "房源到期的颜色值数组")
    private String roomStatusColor;

    @Schema(description = "当前管理的机构ID", example = "24170")
    private Long orgId;

    @Schema(description = "国家ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25152")
    @NotNull(message = "国家ID不能为空")
    private Long countryId;

    @Schema(description = "省份ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28184")
    @NotNull(message = "省份ID不能为空")
    private Long provinceId;

    @Schema(description = "城市ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20414")
    @NotNull(message = "城市ID不能为空")
    private Long cityId;

    @Schema(description = "区县ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7954")
    private Long districtId;

    @Schema(description = "街道/乡镇ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6934")
    private Long streetId;

    @Schema(description = "社区/村ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13638")
    private Long communityId;

    @Schema(description = "房间最低单价（月）", example = "15451")
    private BigDecimal roomMinPrice;

    @Schema(description = "房间平均单价（月）", example = "6046")
    private BigDecimal roomAveragePrice;

    @Schema(description = "附近公共交通相关信息")
    private String trafficInfo;

    @Schema(description = "业态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String type;

    @Schema(description = "额外扩展配置")
    private String extraConfig;

    @Schema(description = "vr链接")
    private String vrLink;

    @Schema(description = "视频地址")
    private String video;

    @Schema(description = "VR视频导致的排序（VR和视频30，VR20，视频10，没有0）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer vrVideoSort;

    @Schema(description = "月浏览数（计划任务统计）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer monthHits;

    @Schema(description = "排序值")
    private Integer sort;

    @Schema(description = "围绕项目服务的子项目配置信息，例如人脸识别设备")
    private String microServiceConfig;

}