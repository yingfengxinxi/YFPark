package cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 停车场（一个项目可以有多个场） Response VO")
@Data
@ExcelIgnoreUnannotated
public class ParkRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "243")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "停车场名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("停车场名称")
    private String parkName;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27186")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "上级停车场ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2516")
    @ExcelProperty("上级停车场ID")
    private Long parentId;

    @Schema(description = "车场类型 0-默认 1-大华8900", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("车场类型 0-默认 1-大华8900")
    private String parkType;

    @Schema(description = "ip端口")
    @ExcelProperty("ip端口")
    private String parkIpPort;

    @Schema(description = "停车场级别字符串，逗号拼接上级id（便于场中场中场，例如地下经过地上，地下某区域被承包）")
    @ExcelProperty("停车场级别字符串，逗号拼接上级id（便于场中场中场，例如地下经过地上，地下某区域被承包）")
    private String level;

    @Schema(description = "临时车政策（0允许进入，1登记/预约进入，2禁止进入）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("临时车政策（0允许进入，1登记/预约进入，2禁止进入）")
    private String tempCar;

    @Schema(description = "免费车类型（数字数组）")
    @ExcelProperty("免费车类型（数字数组）")
    private String freeCar;

    @Schema(description = "限制车主自主绑定车辆数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("限制车主自主绑定车辆数量")
    private Integer bindCarNumber;

    @Schema(description = "车主必须要绑定租客（0不绑定，1必须绑定）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("车主必须要绑定租客（0不绑定，1必须绑定）")
    private String mustBindCompany;

    @Schema(description = "车主月租缴费（0不开启，1必须开启）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("车主月租缴费（0不开启，1必须开启）")
    private String mobilePayMonthFee;

    @Schema(description = "车主余额储值（0不开启，1必须开启）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("车主余额储值（0不开启，1必须开启）")
    private String mobilePayStoreFee;

    @Schema(description = "在场车辆总数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("在场车辆总数")
    private Integer presenceCarTotal;

    @Schema(description = "停车位总数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("停车位总数")
    private Integer parkSpaceTotal;

    @Schema(description = "出口车辆匹配规则")
    @ExcelProperty("出口车辆匹配规则")
    private String outRule;

    @Schema(description = "落座经度（gcj02）")
    @ExcelProperty("落座经度（gcj02）")
    private BigDecimal lng;

    @Schema(description = "落座纬度（gcj02）")
    @ExcelProperty("落座纬度（gcj02）")
    private BigDecimal lat;

    @Schema(description = "状态（1正常，0关闭）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态（1正常，0关闭）")
    private String status;

    @Schema(description = "是否开启免费车申请1是0否", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否开启免费车申请1是0否")
    private String freeCarApply;

    @Schema(description = "无牌车是否开闸0不开闸，1开闸", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("无牌车是否开闸0不开闸，1开闸")
    private String unCarNumberOpen;

    @Schema(description = "额外信息")
    @ExcelProperty("额外信息")
    private String extra;

    @Schema(description = "负载占比", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("负载占比")
    private Integer loadProp;

    @Schema(description = "开发票配置")
    @ExcelProperty("开发票配置")
    private String invoice;

    @Schema(description = "一个车位多辆车配置,0临时收费1禁止驶入", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("一个车位多辆车配置,0临时收费1禁止驶入")
    private Integer moreCar;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}