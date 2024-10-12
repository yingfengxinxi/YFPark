package cn.sdqingyun.smartpark.module.bus.controller.admin.village.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目楼栋 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BuildRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15194")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "楼栋编号")
    @ExcelProperty("楼栋编号")
    private String buildNumber;

    @Schema(description = "楼栋名称", example = "芋艿")
    @ExcelProperty("楼栋名称")
    private String buildName;

    @Schema(description = "楼宇lOGO")
    @ExcelProperty("楼宇lOGO")
    private String logo;

    @Schema(description = "分区ID", example = "1058")
    @ExcelProperty("分区ID")
    private Long zoneId;

    @Schema(description = "项目ID", example = "2374")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "项目名称", example = "智慧园区")
    @ExcelProperty("项目名称")
    private String villageName;

    @Schema(description = "有没有单元，1有，0没有", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("有没有单元，1有，0没有")
    private Integer haveUnit;

    @Schema(description = "项目省市区地址")
    @ExcelProperty("项目省市区地址")
    private String districtTxt;

    @Schema(description = "国家ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30969")
    @ExcelProperty("国家ID")
    private Long countryId;

    @Schema(description = "省份ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29280")
    @ExcelProperty("省份ID")
    private Long provinceId;

    @Schema(description = "城市ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16594")
    @ExcelProperty("城市ID")
    private Long cityId;

    @Schema(description = "区县ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9912")
    @ExcelProperty("区县ID")
    private Long districtId;

    @Schema(description = "街道/乡镇ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12928")
    @ExcelProperty("街道/乡镇ID")
    private Long streetId;

    @Schema(description = "社区/村ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30878")
    @ExcelProperty("社区/村ID")
    private Long communityId;

    @Schema(description = "落座经度（gcj02）")
    @ExcelProperty("落座经度（gcj02）")
    private BigDecimal lng;

    @Schema(description = "落座纬度（gcj02）")
    @ExcelProperty("落座纬度（gcj02）")
    private BigDecimal lat;

    @Schema(description = "地址")
    @ExcelProperty("地址")
    private String address;

    @Schema(description = "标准层高")
    @ExcelProperty("标准层高")
    private BigDecimal floorHeight;

    @Schema(description = "产权性质")
    @ExcelProperty("产权性质")
    private String propertyRight;

    @Schema(description = "建筑面积")
    @ExcelProperty("建筑面积")
    private BigDecimal buildArea;

    @Schema(description = "产权面积")
    @ExcelProperty("产权面积")
    private BigDecimal propertyArea;

    @Schema(description = "可租面积")
    @ExcelProperty("可租面积")
    private BigDecimal rentableArea;

    @Schema(description = "自用面积")
    @ExcelProperty("自用面积")
    private BigDecimal selfUseArea;

    @Schema(description = "配套面积")
    @ExcelProperty("配套面积")
    private BigDecimal supportingArea;

    @Schema(description = "产权编号")
    @ExcelProperty("产权编号")
    private String propertyNumber;

    @Schema(description = "土地编号")
    @ExcelProperty("土地编号")
    private String landNumber;

    @Schema(description = "不动产证编号")
    @ExcelProperty("不动产证编号")
    private String estateNumber;

    @Schema(description = "车位面积")
    @ExcelProperty("车位面积")
    private BigDecimal parkingArea;

    @Schema(description = "车位数量", example = "10965")
    @ExcelProperty("车位数量")
    private Integer parkingCount;

    @Schema(description = "管理面积")
    @ExcelProperty("管理面积")
    private BigDecimal managementArea;

    @Schema(description = "房间总数", requiredMode = Schema.RequiredMode.REQUIRED, example = "28951")
    @ExcelProperty("房间总数")
    private Integer roomCount;

    @Schema(description = "在租面积")
    @ExcelProperty("在租面积")
    private BigDecimal rentInArea;

    @Schema(description = "在租房间数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("在租房间数")
    private Integer rentInRoom;

    @Schema(description = "在租合同数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("在租合同数")
    private Integer rentInContract;

    @Schema(description = "招商房间总数", requiredMode = Schema.RequiredMode.REQUIRED, example = "5335")
    @ExcelProperty("招商房间总数")
    private Integer invitationRoomCount;

    @Schema(description = "营收目标(数组存储)")
    @ExcelProperty("营收目标(数组存储)")
    private String revenueTarget;

    @Schema(description = "默认收支账户")
    @ExcelProperty("默认收支账户")
    private Long accountDefault;

    @Schema(description = "默认配置")
    @ExcelProperty("默认配置")
    private String extraConfig;

    @Schema(description = "楼宇标签")
    @ExcelProperty("楼宇标签")
    private String tagInfo;

    @Schema(description = "是否热门", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否热门")
    private Integer isHot;

    @Schema(description = "排序，越大越前", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序，越大越前")
    private Integer sort;

    @Schema(description = "数据状态(1使用，0隐藏)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("数据状态(1使用，0隐藏)")
    private Integer status;

    @Schema(description = "3D模型")
    @ExcelProperty("3D模型")
    private String threeDimensionalFile;

    @Schema(description = "3D模型物体ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5191")
    @ExcelProperty("3D模型物体ID")
    private String threeDimensionalId;

    @Schema(description = "实景图")
    @ExcelProperty("实景图")
    private String dimensionalBgImg;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "当前管理的机构ID")
    private Long orgId;
}