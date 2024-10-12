package cn.sdqingyun.smartpark.module.bus.controller.admin.park.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 月租车白名单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ParkCarsPageReqVO extends PageParam {

    @Schema(description = "项目ID", example = "23246")
    private Long villageId;

    @Schema(description = "停车场ID", example = "19900")
    private Long parkId;

    @Schema(description = "住户ID", example = "7294")
    private Long userId;

    @Schema(description = "用户姓名", example = "李四")
    private String userName;

    @Schema(description = "用户手机号(128加密)")
    private String userPhone;

    @Schema(description = "车牌号")
    private String carNumber;

    @Schema(description = "车的类型（1蓝牌、黄牌等）", example = "1")
    private String carType;

    @Schema(description = "管理类型（1月租车，2储值车，3登记/预约临时车）", example = "1")
    private String chargeType;

    @Schema(description = "余额（单位元）")
    private BigDecimal balance;

    @Schema(description = "车的备注", example = "你说的对")
    private String carRemark;

    @Schema(description = "绑定的车位ID（允许多车多位所以用json）")
    private String parkSpace;

    @Schema(description = "车的数据字段（例如发动机编号、车品牌等）")
    private String carData;

    @Schema(description = "数据录入来源（1后台，0租客）")
    private String addFrom;

    @Schema(description = "当前名单是否有效（0：无效，1：有效）")
    private String enable;

    @Schema(description = "当前名单是否为黑名单（0：否，1：黑名单）")
    private String needAlarm;

    @Schema(description = "生效时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date enableTime;

    @Schema(description = "过期时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date overdueTime;

    @Schema(description = "是否已经推送给停车主机（0没有，1推送成功，2推送失败），修改后重置成0")
    private String isPush;

    @Schema(description = "推送主机的结果（主要展示失败原因）")
    private String pushResult;

    @Schema(description = "收费规则id，0是默认", example = "22158")
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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}