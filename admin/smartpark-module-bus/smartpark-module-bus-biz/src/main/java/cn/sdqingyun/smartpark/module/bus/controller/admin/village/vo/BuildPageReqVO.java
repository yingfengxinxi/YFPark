package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目楼栋分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BuildPageReqVO extends PageParam {

    @Schema(description = "楼栋编号")
    private String buildNumber;

    @Schema(description = "楼栋名称", example = "芋艿")
    private String buildName;

    @Schema(description = "楼宇lOGO")
    private String logo;

    @Schema(description = "分区ID", example = "1058")
    private Long zoneId;

    @Schema(description = "项目ID", example = "2374")
    private Long villageId;

    @Schema(description = "有没有单元，1有，0没有")
    private Long haveUnit;

    @Schema(description = "项目省市区地址")
    private String districtTxt;

    @Schema(description = "国家ID", example = "30969")
    private Long countryId;

    @Schema(description = "省份ID", example = "29280")
    private Long provinceId;

    @Schema(description = "城市ID", example = "16594")
    private Long cityId;

    @Schema(description = "区县ID", example = "9912")
    private Long districtId;

    @Schema(description = "街道/乡镇ID", example = "12928")
    private Long streetId;

    @Schema(description = "社区/村ID", example = "30878")
    private Long communityId;

    @Schema(description = "落座经度（gcj02）")
    private BigDecimal lng;

    @Schema(description = "落座纬度（gcj02）")
    private BigDecimal lat;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "标准层高")
    private BigDecimal floorHeight;

    @Schema(description = "产权性质")
    private String propertyRight;

    @Schema(description = "建筑面积")
    private BigDecimal buildArea;

    @Schema(description = "产权面积")
    private BigDecimal propertyArea;

    @Schema(description = "可租面积")
    private BigDecimal rentableArea;

    @Schema(description = "自用面积")
    private BigDecimal selfUseArea;

    @Schema(description = "配套面积")
    private BigDecimal supportingArea;

    @Schema(description = "产权编号")
    private String propertyNumber;

    @Schema(description = "土地编号")
    private String landNumber;

    @Schema(description = "不动产证编号")
    private String estateNumber;

    @Schema(description = "车位面积")
    private BigDecimal parkingArea;

    @Schema(description = "车位数量", example = "10965")
    private Integer parkingCount;

    @Schema(description = "管理面积")
    private BigDecimal managementArea;

    @Schema(description = "房间总数", example = "28951")
    private Integer roomCount;

    @Schema(description = "在租面积")
    private BigDecimal rentInArea;

    @Schema(description = "在租房间数")
    private Integer rentInRoom;

    @Schema(description = "在租合同数")
    private Integer rentInContract;

    @Schema(description = "招商房间总数", example = "5335")
    private Integer invitationRoomCount;

    @Schema(description = "营收目标(数组存储)")
    private String revenueTarget;

    @Schema(description = "默认收支账户")
    private Long accountDefault;

    @Schema(description = "默认配置")
    private String extraConfig;

    @Schema(description = "楼宇标签")
    private String tagInfo;

    @Schema(description = "是否热门")
    private Integer isHot;

    @Schema(description = "排序，越大越前")
    private Integer sort;

    @Schema(description = "数据状态(1使用，0隐藏)", example = "1")
    private Integer status;

    @Schema(description = "3D模型")
    private String threeDimensionalFile;

    @Schema(description = "3D模型物体ID", example = "5191")
    private String threeDimensionalId;

    @Schema(description = "实景图")
    private String dimensionalBgImg;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "当前管理的机构ID")
    private Long orgId;
}