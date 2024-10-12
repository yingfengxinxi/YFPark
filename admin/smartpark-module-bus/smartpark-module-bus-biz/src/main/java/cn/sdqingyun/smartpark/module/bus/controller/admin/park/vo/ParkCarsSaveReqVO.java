package cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.util.*;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 月租车白名单新增/修改 Request VO")
@Data
public class ParkCarsSaveReqVO extends TenantBaseDO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "5533")
    private Long id;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23246")
    @NotNull(message = "项目ID不能为空")
    private Long villageId;

    @Schema(description = "停车场ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19900")
    @NotNull(message = "停车场ID不能为空")
    private Long parkId;

    @Schema(description = "住户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7294")
    private Long userId;

    @Schema(description = "用户姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "用户姓名不能为空")
    private String userName;

    @Schema(description = "用户手机号(128加密)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "用户手机号(128加密)不能为空")
    private String userPhone;

    @Schema(description = "车牌号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "车牌号不能为空")
    private String carNumber;

    @Schema(description = "车的类型（1蓝牌、黄牌等）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "车的类型（1蓝牌、黄牌等）不能为空")
    private String carType;

    @Schema(description = "【车辆收费类别】管理类型（1月租车，2储值车，3登记/预约临时车）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "车辆收费类别（1月租车，2储值车，3登记/预约临时车）不能为空")
    private String chargeType;

    @Schema(description = "余额（单位元）", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal balance;

    @Schema(description = "车的备注", example = "你说的对")
    private String carRemark;

    @Schema(description = "绑定的车位ID（允许多车多位所以用json）")
    private String parkSpace;

    @Schema(description = "车的数据字段（例如发动机编号、车品牌等）")
    private String carData;

    @Schema(description = "数据录入来源（1后台，0租客）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String addFrom;

    @Schema(description = "当前名单是否有效（0：无效，1：有效）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "当前名单是否有效（0：无效，1：有效）不能为空")
    private String enable;

    @Schema(description = "当前名单是否为黑名单（0：否，1：黑名单）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "当前名单是否为黑名单（0：否，1：黑名单）不能为空")
    private String needAlarm;

    @Schema(description = "生效时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date enableTime;

    @Schema(description = "过期时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date overdueTime;

    @Schema(description = "是否已经推送给停车主机（0没有，1推送成功，2推送失败），修改后重置成0", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isPush;

    @Schema(description = "推送主机的结果（主要展示失败原因）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String pushResult;

    @Schema(description = "收费规则id，0是默认", requiredMode = Schema.RequiredMode.REQUIRED, example = "22158")
    private Long feeChargeId;

    @Schema(description = "车位数")
    private Integer spaceNum;

    @Schema(description = "主车辆id", example = "1014")
    private Long parentId;

    @Schema(description = "子车辆数量")
    private Integer subCarNum;


    @Schema(description = "车辆品牌")
    private String vehicleBrand;


    @Schema(description = "车辆设备号")
    private String vehicleEquipmentNumber;


    @Schema(description = "行驶证号")
    private String drivingLicenseNumber;


    @Schema(description = "颜色")
    private String vehicleColor;

}