package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 机构工单数据 Response VO")
@Data
@ExcelIgnoreUnannotated
public class WorkOrderProposeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "5554")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "机构ID", example = "9612")
    @ExcelProperty("机构ID")
    private Long orgId;

    @Schema(description = "应用标识")
    @ExcelProperty("应用标识")
    private String application;

    @Schema(description = "报修类型;【数据字典WORK_ORDER_REPAIR_TYPE】1=室内报修;2=公共区域报修;", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("报修类型;1=室内报修;2=公共区域报修;")
    private String repairType;

    @Schema(description = "工单类型;1=普通工单;2=付费工单;", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("工单类型;1=普通工单;2=付费工单;")
    private String orderType;

    @Schema(description = "收费方式;1=免费服务;2=下单即收费;3=完成后收费;", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("收费方式;1=免费服务;2=下单即收费;3=完成后收费;")
    private String paidType;

    @Schema(description = "工单订单状态;2=已支付;3=已退款;", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("工单订单状态;2=已支付;3=已退款;")
    private String paidStatus;

    @Schema(description = "费用支付方;1=租客承担;2=机构承担;", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("费用支付方;1=租客承担;2=机构承担;")
    private String paidPayer;

    @Schema(description = "工单编号")
    @ExcelProperty("工单编号")
    private String number;

    @Schema(description = "工单大类id", requiredMode = Schema.RequiredMode.REQUIRED, example = "25505")
    @ExcelProperty("工单大类id")
    private Long categoryId;

    @Schema(description = "工单子类id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10117")
    @ExcelProperty("工单子类id")
    private Long subcatId;

    @Schema(description = "上报人名称", example = "张三")
    @ExcelProperty("上报人名称")
    private String name;

    @Schema(description = "上报人手机")
    @ExcelProperty("上报人手机")
    private String phone;

    @Schema(description = "上报时间")
    @ExcelProperty("上报时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date appearTime;

    @Schema(description = "上门时间")
    @ExcelProperty("上门时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date visitTime;

    @Schema(description = "预约起始时间")
    @ExcelProperty("预约起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date reserveStartTime;

    @Schema(description = "预约结束时间")
    @ExcelProperty("预约结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date reserveEndTime;

    @Schema(description = "等待起始时间")
    @ExcelProperty("等待起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date waitTime;

    @Schema(description = "指定位置信息")
    @ExcelProperty("指定位置信息")
    private String position;

    @Schema(description = "描述标签json")
    @ExcelProperty("描述标签json")
    private String labelJson;

    @Schema(description = "补充内容", example = "你猜")
    @ExcelProperty("补充内容")
    private String remark;

    @Schema(description = "上报图片json;支持多张")
    @ExcelProperty("上报图片json;支持多张")
    private String images;

    @Schema(description = "项目id", example = "31530")
    @ExcelProperty("项目id")
    private Long villageId;

    @Schema(description = "楼宇id", example = "30763")
    @ExcelProperty("楼宇id")
    private Long buildId;

    @Schema(description = "租客id", example = "19911")
    @ExcelProperty("租客id")
    private Long ownerId;

    @Schema(description = "当前工单所属岗位处理人json")
    @ExcelProperty("当前工单所属岗位处理人json")
    private String postUids;

    @Schema(description = "工单来源")
    @ExcelProperty("工单来源")
    private String from;

    @Schema(description = "操作来源")
    @ExcelProperty("操作来源")
    private String source;

    @Schema(description = "工单来源应用标识")
    @ExcelProperty("工单来源应用标识")
    private String workApp;

    @Schema(description = "巡检任务id", example = "25462")
    @ExcelProperty("巡检任务id")
    private Long taskId;

    @Schema(description = "第三方发起工单的id（例如巡检整改记录id、资产维修单id）", example = "23551")
    @ExcelProperty("第三方发起工单的id（例如巡检整改记录id、资产维修单id）")
    private Long recordId;

    @Schema(description = "第三方发起工单的第二个id（例如资产维修单的资产id）")
    @ExcelProperty("第三方发起工单的第二个id（例如资产维修单的资产id）")
    private Long recordId2;

    @Schema(description = "是否超时【数据字典WORK_ORDER_IS_TIMEOUT】;0=否;1=是;", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否超时;0=否;1=是;")
    private String isTimeout;

    @Schema(description = "是否可抢单;0=否;1=是;", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否可抢单;0=否;1=是;")
    private String isChange;

    @Schema(description = "可抢单是否转未指派;0=否;1=是;", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("可抢单是否转未指派;0=否;1=是;")
    private String isAssign;

    @Schema(description = "超时起始时间")
    @ExcelProperty("超时起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date timeoutTime;

    @Schema(description = "可抢单起始时间")
    @ExcelProperty("可抢单起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date changeTime;

    @Schema(description = "可抢单结束时间")
    @ExcelProperty("可抢单结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date robTime;

    @Schema(description = "工单状态【数据字典WORK_ORDER_STATUS】;1=未指派,2=已指派,3=处理中,4=已办结", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("工单状态;1=未指派,2=已指派,3=处理中,4=已办结")
    private String status;

    @Schema(description = "评价等级;1-5星")
    @ExcelProperty("评价等级;1-5星")
    private Integer appraiseLevel;

    @Schema(description = "是否通知", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否通知")
    private String hasNotice;

    @Schema(description = "是否支持退款;0=否;1=是;", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否支持退款;0=否;1=是;")
    private String hasRefund;

    @Schema(description = "是否能重新打开;0=否;1=是;", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否能重新打开;0=否;1=是;")
    private String hasOpen;

    @Schema(description = "跟进人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "7297")
    @ExcelProperty("跟进人uid")
    private Long followupUid;

    @Schema(description = "抢单人uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "14622")
    @ExcelProperty("抢单人uid")
    private Long robUid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "支付情况")
    private String payInfo;

}