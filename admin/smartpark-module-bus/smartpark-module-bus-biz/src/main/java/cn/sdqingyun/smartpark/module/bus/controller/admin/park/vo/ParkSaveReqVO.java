package cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.util.*;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 停车场（一个项目可以有多个场）新增/修改 Request VO")
@Data
public class ParkSaveReqVO extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "243")
    private Long id;

    @Schema(description = "停车场名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String parkName;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27186")
    private Long villageId;

    @Schema(description = "上级停车场ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2516")
    private Long parentId;

    @Schema(description = "车场类型 0-默认 1-大华8900", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String parkType;

    @Schema(description = "ip端口")
    private String parkIpPort;

    @Schema(description = "停车场级别字符串，逗号拼接上级id（便于场中场中场，例如地下经过地上，地下某区域被承包）")
    private String level;

    @Schema(description = "临时车政策（0允许进入，1登记/预约进入，2禁止进入）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tempCar;

    @Schema(description = "免费车类型（数字数组）")
    private String freeCar;

    @Schema(description = "限制车主自主绑定车辆数量", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer bindCarNumber;

    @Schema(description = "月租车开放对象（1租客，2公众）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mustBindCompany;

    @Schema(description = "车主月租缴费（0不开启，1必须开启）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mobilePayMonthFee;

    @Schema(description = "车主余额储值（0不开启，1必须开启）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mobilePayStoreFee;

    @Schema(description = "在场车辆总数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer presenceCarTotal;

    @Schema(description = "停车位总数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer parkSpaceTotal;

    @Schema(description = "出口车辆匹配规则")
    private String outRule;

    @Schema(description = "落座经度（gcj02）")
    private BigDecimal lng;

    @Schema(description = "落座纬度（gcj02）")
    private BigDecimal lat;

    @Schema(description = "状态（1正常，0关闭）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String status;

    @Schema(description = "是否开启免费车申请1是0否", requiredMode = Schema.RequiredMode.REQUIRED)
    private String freeCarApply;

    @Schema(description = "无牌车是否开闸0不开闸，1开闸", requiredMode = Schema.RequiredMode.REQUIRED)
    private String unCarNumberOpen;

    @Schema(description = "额外信息")
    private String extra;

    @Schema(description = "负载占比", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer loadProp;

    @Schema(description = "开发票配置")
    private String invoice;

    @Schema(description = "一个车位多辆车配置,0临时收费1禁止驶入【一户多车配置】", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer moreCar;

}