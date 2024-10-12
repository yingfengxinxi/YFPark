package cn.sdqingyun.smartpark.module.bus.controller.admin.energy.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;
import java.util.*;
import java.math.BigDecimal;

import org.apache.ibatis.type.JdbcType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.*;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 自定义 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2806")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "设备表种类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("设备表种类")
    private Long type;

    @Schema(description = "设备表种类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String typeName;

    @Schema(description = "机构ID", example = "3190")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27447")
    @ExcelProperty("项目ID")
    private Long villageId;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "27447")
    private String villageName;

    @Schema(description = "楼宇ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7880")
    @ExcelProperty("楼宇ID")
    private Long buildId;

    @Schema(description = "楼宇名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "27447")
    private String buildName;

    @Schema(description = "用途 1-分表 2-总表 3-公摊表")
    @ExcelProperty("用途 1-分表 2-总表 3-公摊表")
    private String purpose;
    private String purposeName;

    @Schema(description = "名称", example = "李四")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "表具编号")
    @ExcelProperty("表具编号")
    private String number;

    @Schema(description = "倍率", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("倍率")
    private BigDecimal magnification;

    @Schema(description = "原始读数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("原始读数")
    private BigDecimal originalReading;

    @Schema(description = "最大读数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("最大读数")
    private BigDecimal maxReading;

    @Schema(description = "抄录时间")
    @ExcelProperty("抄录时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date meterTime;

    @Schema(description = "公摊方式 1-不公摊 2-按分表用量比例公摊 3-按租客租赁面积比例公摊 4-按房源数量平均公摊", example = "2")
    @ExcelProperty("公摊方式 1-不公摊 2-按分表用量比例公摊 3-按租客租赁面积比例公摊 4-按房源数量平均公摊")
    private String publicType;

    @Schema(description = "授予用户管理的项目和楼宇，json存储")
    @ExcelProperty("授予用户管理的项目和楼宇，json存储")
    private String builds;

    @Schema(description = "楼层id,1,2,3")
    private String layerIds;

    @Schema(description = "绑定房间信息，1,2,3")
    @ExcelProperty("绑定房间信息，1,2,3")
    private String roomIds;

    @ExcelProperty("绑定房间名称")
    private String roomName;

    @Schema(description = "合同ids")
    @ExcelProperty("合同ids")
    private String contractIds;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "状态，1启动，0审核中，4禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态，1启动，0审核中，4禁用")
    private String status;
    private String statusName;

    @Schema(description = "厂商id", example = "2")
    private String deviceId;

    @Schema(description = "设备类型", example = "2")
    private String deviceType;

    @Schema(description = "设备序列号", example = "2")
    private String deviceSerial;

    @Schema(description = "资产的ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25197")
    @ExcelProperty("资产的ID")
    private Long propertyId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "25197")
    private String deviceName;

    @Schema(description = "设备通断状态 0通，1断", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备通断状态 0通，1断")
    private String deviceOff;
    private String deviceOffName;

    @Schema(description = "设备状态 0无 1离线 2断电 3电量过低", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("设备状态 0无 1离线 2断电 3电量过低")
    private String deviceStatus;
    private String deviceStatusName;

    @Schema(description = "上次心跳时间")
    @ExcelProperty("上次心跳时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date lastHeartTime;

    @Schema(description = "是否开启远程阀控/1是0否")
    @ExcelProperty("是否开启远程阀控/1是0否")
    private String remoteValveControl;

    @Schema(description = "是否提醒过 1是0否、send_remind_value(低于提醒值)，send_zero_value（小于0）")
    @ExcelProperty("是否提醒过 1是0否、send_remind_value(低于提醒值)，send_zero_value（小于0）")
    private String isRemind;

    @Schema(description = "当前余量")
    @ExcelProperty("当前余量")
    private BigDecimal currentRemaining;

    @Schema(description = "付费类型 0是后付费 1是预付费", example = "1")
    @ExcelProperty("付费类型 0是后付费 1是预付费")
    private String paymentType;
    private String paymentTypeName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "绑定位置", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bindingLocation;

    @Schema(description = "公摊房源数量", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer bindRoomCount;

    @Schema(description = "公摊在租房源数量", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer rentRoomCount;

    @Schema(description = "是否存在抄表记录", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isRecord;

    @Schema(description = "绑定分表子表id", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> energyBindEnergyIdList;


    @Schema(description = "本期抄表时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private Date thisTime;

    @Schema(description = "本期读数", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal thisNumber;

    @Schema(description = "上期读数", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal lastNumber;

    @Schema(description = "本期用量", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal thisUse;

    @Schema(description = "本期负责人id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long leadUid;

    @Schema(description = "本期负责人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String leadName;

    @Schema(description = "绑定设备", requiredMode = Schema.RequiredMode.REQUIRED)
    private String propertyName;
}