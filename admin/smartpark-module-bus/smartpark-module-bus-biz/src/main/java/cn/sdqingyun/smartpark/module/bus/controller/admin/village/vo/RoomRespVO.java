package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag.TagHouseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目房间 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RoomRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "8515")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "房间编号")
    @ExcelProperty("房间编号")
    private Integer roomNumber;

    @Schema(description = "房间名称", example = "王五")
    @ExcelProperty("房间名称")
    private String roomName;

    @Schema(description = "房间管理编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10110")
    @ExcelProperty("房间管理编号")
    private String roomAliasId;

    @Schema(description = "父级房间编号", example = "13035")
    @ExcelProperty("父级房间编号")
    private Long parentRoomId;

    @Schema(description = "子房间总数", requiredMode = Schema.RequiredMode.REQUIRED, example = "10080")
    @ExcelProperty("子房间总数")
    private Short subRoomCount;

    @Schema(description = "子房间已租总数", requiredMode = Schema.RequiredMode.REQUIRED, example = "17555")
    @ExcelProperty("子房间已租总数")
    private Short subRoomRentCount;

    @Schema(description = "建筑面积")
    @ExcelProperty("建筑面积")
    private BigDecimal buildArea;

    @Schema(description = "套内面积（实际使用面积）")
    @ExcelProperty("套内面积（实际使用面积）")
    private BigDecimal insideArea;

    @Schema(description = "计租面积")
    @ExcelProperty("计租面积")
    private BigDecimal rentalArea;

    @Schema(description = "计租面积（在租）")
    @ExcelProperty("计租面积（在租）")
    private BigDecimal rentalAreaIn;

    @Schema(description = "计费面积")
    @ExcelProperty("计费面积")
    private BigDecimal chargingArea;

    @Schema(description = "计费面积（在租）")
    @ExcelProperty("计费面积（在租）")
    private BigDecimal chargingAreaIn;

    @Schema(description = "租赁开始时间")
    @ExcelProperty("租赁开始时间")
    private LocalDateTime leaseStart;

    @Schema(description = "租赁结束时间")
    @ExcelProperty("租赁结束时间")
    private LocalDateTime leaseEnd;

    @Schema(description = "交付时间")
    @ExcelProperty("交付时间")
    private LocalDateTime deliverTime;

    @Schema(description = "1招商，0不招商（出租后默认设置不招商）", example = "2")
    @ExcelProperty("1招商，0不招商（出租后默认设置不招商）")
    private Integer invitationStatus;

    @Schema(description = "招商政策")
    @ExcelProperty("招商政策")
    private String investmentPolicy;

    @Schema(description = "招商条件")
    @ExcelProperty("招商条件")
    private String investmentConditions;

    @Schema(description = "图集数组")
    @ExcelProperty("图集数组")
    private String images;

    @Schema(description = "价格单位")
    @ExcelProperty("价格单位")
    private String priceUnit;

    @Schema(description = "底价单位")
    @ExcelProperty("底价单位")
    private String priceUnitMin;

    @Schema(description = "预租单价", example = "17991")
    @ExcelProperty("预租单价")
    private BigDecimal preUnitPrice;

    @Schema(description = "最低价格")
    @ExcelProperty("最低价格")
    private BigDecimal preUnitPriceMin;

    @Schema(description = "房源标签ID数组")
    @ExcelProperty("房源标签ID数组")
    private String tagIdArr;

    @Schema(description = "合同情况，记录时间、执行情况，用于判断是否逾期")
    @ExcelProperty("合同情况，记录时间、执行情况，用于判断是否逾期")
    private String contractInfo;

    @Schema(description = "在租合同数", requiredMode = Schema.RequiredMode.REQUIRED, example = "26264")
    @ExcelProperty("在租合同数")
    private Integer contractCount;

    @Schema(description = "装修情况")
    @ExcelProperty("装修情况")
    private String decoration;

    @Schema(description = "产权性质")
    @ExcelProperty("产权性质")
    private String propertyRight;

    @Schema(description = "标准层高")
    @ExcelProperty("标准层高")
    private BigDecimal floorHeight;

    @Schema(description = "荷载值")
    @ExcelProperty("荷载值")
    private String loadMax;

    @Schema(description = "楼层ID", example = "32644")
    @ExcelProperty("楼层ID")
    private Long layerId;

    @Schema(description = "单元ID", example = "1827")
    @ExcelProperty("单元ID")
    private Long unitId;

    @Schema(description = "楼栋ID", example = "31298")
    @ExcelProperty("楼栋ID")
    private Long buildId;

    @Schema(description = "分区ID", example = "21523")
    @ExcelProperty("分区ID")
    private Long zoneId;

    @Schema(description = "项目ID", example = "13804")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "排序，越大越前", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序，越大越前")
    private Integer sort;

    @Schema(description = "数据状态(1使用，0隐藏)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("数据状态(1使用，0隐藏)")
    private Integer status;

    @Schema(description = "3D模型物体ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10845")
    @ExcelProperty("3D模型物体ID")
    private String threeDimensionalId;

    @Schema(description = "状态(<10空置状态，<20已预订，<30出租中)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态(<10空置状态，<20已预订，<30出租中)")
    private Integer roomStatus;

    @Schema(description = "房源类型", example = "1")
    @ExcelProperty("房源类型")
    private String houseType;

    @Schema(description = "房源备案号")
    @ExcelProperty("房源备案号")
    private String recordNo;

    @Schema(description = "推广佣金")
    @ExcelProperty("推广佣金")
    private BigDecimal promoterMoney;

    @Schema(description = "推广佣金单位")
    @ExcelProperty("推广佣金单位")
    private String promoterMoneyUnit;

    @Schema(description = "额外扩展配置")
    @ExcelProperty("额外扩展配置")
    private String extraConfig;

    @Schema(description = "vr链接")
    @ExcelProperty("vr链接")
    private String vrLink;

    @Schema(description = "视频地址")
    @ExcelProperty("视频地址")
    private String video;

    @Schema(description = "VR视频导致的排序（VR和视频30，VR20，视频10，没有0）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("VR视频导致的排序（VR和视频30，VR20，视频10，没有0）")
    private Integer vrVideoSort;

    @Schema(description = "月浏览数（计划任务统计）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("月浏览数（计划任务统计）")
    private Integer monthHits;

    @Schema(description = "是否占用了父房间的面积", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否占用了父房间的面积")
    private Integer splitParentArea;

    @Schema(description = "是否锁定房源(1锁定，0正常(取消锁定))", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否锁定房源(1锁定，0正常(取消锁定))")
    private Integer isLock;

    @Schema(description = "0=真实房间;1=非真实房间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("0=真实房间;1=非真实房间")
    private Short isUnreal;

    @Schema(description = "锁定房源面积信息")
    @ExcelProperty("锁定房源面积信息")
    private String extraLock;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "楼层名称")
    private String layerName;

    @Schema(description = "楼栋名称", example = "31298")
    private String buildName;

    @Schema(description = "项目名称", example = "13804")
    private String villageName;

    @Schema(description = "房源标签列表")
    private List<TagHouseDO> tagHouseList;

    @Schema(description = "房间展示状态")
    private String showStatus;

    @Schema(description = "租客姓名")
    private String ownerName;
}