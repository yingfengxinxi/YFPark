package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 自定义分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnergyPageReqVO extends PageParam {

    @Schema(description = "设备表种类", example = "1")
    private Long type;

    @Schema(description = "机构ID", example = "3190")
    private Long orgId;

    @Schema(description = "项目ID", example = "27447")
    private Long villageId;

    @Schema(description = "楼宇ID", example = "7880")
    private Long buildId;

    @Schema(description = "用途 1-分表 2-总表 3-公摊表")
    private String purpose;

    @Schema(description = "名称", example = "李四")
    private String name;

    @Schema(description = "表具编号")
    private String number;

    @Schema(description = "倍率")
    private BigDecimal magnification;

    @Schema(description = "原始读数")
    private BigDecimal originalReading;

    @Schema(description = "最大读数")
    private BigDecimal maxReading;

    @Schema(description = "抄录时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date meterTime;

    @Schema(description = "公摊方式 1-不公摊 2-按分表用量比例公摊 3-按租客租赁面积比例公摊 4-按房源数量平均公摊", example = "2")
    private String publicType;

    @Schema(description = "授予用户管理的项目和楼宇，json存储")
    private String builds;

    @Schema(description = "楼层id,1,2,3")
    private String layerIds;

    @Schema(description = "绑定房间信息，1,2,3")
    private String roomIds;


    @Schema(description = "合同ids")
    private String contractIds;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "状态，1启动，0审核中，4禁用", example = "2")
    private String status;

    @Schema(description = "厂商id", example = "2")
    private String deviceId;

    @Schema(description = "设备类型", example = "2")
    private String deviceType;

    @Schema(description = "设备序列号", example = "2")
    private String deviceSerial;

    @Schema(description = "资产的ID", example = "25197")
    private Long propertyId;

    @Schema(description = "设备通断状态 0通，1断")
    private String deviceOff;

    @Schema(description = "设备状态 0无 1离线 2断电 3电量过低", example = "1")
    private String deviceStatus;

    @Schema(description = "上次心跳时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date lastHeartTime;

    @Schema(description = "是否开启远程阀控/1是0否")
    private String remoteValveControl;

    @Schema(description = "是否提醒过 1是0否、send_remind_value(低于提醒值)，send_zero_value（小于0）")
    private String isRemind;

    @Schema(description = "当前余量")
    private BigDecimal currentRemaining;

    @Schema(description = "付费类型 0是后付费 1是预付费", example = "1")
    private String paymentType;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}