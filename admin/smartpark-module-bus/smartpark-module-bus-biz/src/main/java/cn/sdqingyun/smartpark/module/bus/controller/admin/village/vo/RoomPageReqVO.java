package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目房间分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RoomPageReqVO extends PageParam {

    @Schema(description = "房间编号")
    private Integer roomNumber;

    @Schema(description = "房间名称", example = "王五")
    private String roomName;

    @Schema(description = "房间管理编号", example = "10110")
    private String roomAliasId;

    @Schema(description = "父级房间编号", example = "13035")
    private Long parentRoomId;

    @Schema(description = "子房间总数", example = "10080")
    private Short subRoomCount;

    @Schema(description = "子房间已租总数", example = "17555")
    private Short subRoomRentCount;

    @Schema(description = "建筑面积")
    private BigDecimal buildArea;

    @Schema(description = "套内面积（实际使用面积）")
    private BigDecimal insideArea;

    @Schema(description = "计租面积")
    private BigDecimal rentalArea;

    @Schema(description = "计租面积（在租）")
    private BigDecimal rentalAreaIn;

    @Schema(description = "计费面积")
    private BigDecimal chargingArea;

    @Schema(description = "计费面积（在租）")
    private BigDecimal chargingAreaIn;

    @Schema(description = "租赁开始时间")
    private LocalDateTime leaseStart;

    @Schema(description = "租赁结束时间")
    private LocalDateTime leaseEnd;

    @Schema(description = "交付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deliverTime;

    @Schema(description = "1招商，0不招商（出租后默认设置不招商）", example = "2")
    private Integer invitationStatus;

    @Schema(description = "招商政策")
    private String investmentPolicy;

    @Schema(description = "招商条件")
    private String investmentConditions;

    @Schema(description = "图集数组")
    private String images;

    @Schema(description = "价格单位")
    private String priceUnit;

    @Schema(description = "底价单位")
    private String priceUnitMin;

    @Schema(description = "预租单价", example = "17991")
    private BigDecimal preUnitPrice;

    @Schema(description = "最低价格")
    private BigDecimal preUnitPriceMin;

    @Schema(description = "房源标签ID数组")
    private String tagIdArr;

    @Schema(description = "合同情况，记录时间、执行情况，用于判断是否逾期")
    private String contractInfo;

    @Schema(description = "在租合同数", example = "26264")
    private Integer contractCount;

    @Schema(description = "装修情况")
    private String decoration;

    @Schema(description = "产权性质")
    private String propertyRight;

    @Schema(description = "标准层高")
    private BigDecimal floorHeight;

    @Schema(description = "荷载值")
    private String loadMax;

    @Schema(description = "楼层ID", example = "32644")
    private Long layerId;

    @Schema(description = "单元ID", example = "1827")
    private Long unitId;

    @Schema(description = "楼栋ID", example = "31298")
    private Long buildId;

    @Schema(description = "分区ID", example = "21523")
    private Long zoneId;

    @Schema(description = "项目ID", example = "13804")
    private Long villageId;

    @Schema(description = "排序，越大越前")
    private Integer sort;

    @Schema(description = "数据状态(1使用，0隐藏)", example = "1")
    private Integer status;

    @Schema(description = "3D模型物体ID", example = "10845")
    private String threeDimensionalId;

    @Schema(description = "状态(<10空置状态，<20已预订，<30出租中)", example = "2")
    private Integer roomStatus;

    @Schema(description = "房源类型", example = "1")
    private String houseType;

    @Schema(description = "房源备案号")
    private String recordNo;

    @Schema(description = "推广佣金")
    private BigDecimal promoterMoney;

    @Schema(description = "推广佣金单位")
    private String promoterMoneyUnit;

    @Schema(description = "额外扩展配置")
    private String extraConfig;

    @Schema(description = "vr链接")
    private String vrLink;

    @Schema(description = "视频地址")
    private String video;

    @Schema(description = "VR视频导致的排序（VR和视频30，VR20，视频10，没有0）")
    private Integer vrVideoSort;

    @Schema(description = "月浏览数（计划任务统计）")
    private Integer monthHits;

    @Schema(description = "是否占用了父房间的面积")
    private Integer splitParentArea;

    @Schema(description = "是否锁定房源(1锁定，0正常(取消锁定))")
    private Integer isLock;

    @Schema(description = "0=真实房间;1=非真实房间")
    private Short isUnreal;

    @Schema(description = "锁定房源面积信息")
    private String extraLock;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}