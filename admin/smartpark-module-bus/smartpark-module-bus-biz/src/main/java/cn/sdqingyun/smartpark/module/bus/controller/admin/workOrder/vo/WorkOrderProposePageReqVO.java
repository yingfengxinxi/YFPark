package cn.sdqingyun.smartpark.module.bus.controller.admin.workOrder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.sdqingyun.smartpark.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "管理后台 - 机构工单数据分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkOrderProposePageReqVO extends PageParam {

    @Schema(description = "机构ID", example = "9612")
    private Long orgId;

    @Schema(description = "应用标识")
    private String application;

    @Schema(description = "报修类型;1=室内报修;2=公共区域报修;", example = "2")
    private String repairType;

    @Schema(description = "工单类型;1=普通工单;2=付费工单;", example = "2")
    private String orderType;

    @Schema(description = "收费方式;1=免费服务;2=下单即收费;3=完成后收费;", example = "1")
    private String paidType;

    @Schema(description = "工单订单状态;2=已支付;3=已退款;", example = "2")
    private String paidStatus;

    @Schema(description = "费用支付方;1=租客承担;2=机构承担;")
    private String paidPayer;

    @Schema(description = "工单编号")
    private String number;

    @Schema(description = "工单大类id", example = "25505")
    private Long categoryId;

    @Schema(description = "工单子类id", example = "10117")
    private Long subcatId;

    @Schema(description = "上报人名称", example = "张三")
    private String name;

    @Schema(description = "上报人手机")
    private String phone;

    @Schema(description = "上报时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date appearTime;

    @Schema(description = "上门时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date visitTime;

    @Schema(description = "预约起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date reserveStartTime;

    @Schema(description = "预约结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date reserveEndTime;

    @Schema(description = "等待起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date waitTime;

    @Schema(description = "指定位置信息")
    private String position;

    @Schema(description = "描述标签json")
    private String labelJson;

    @Schema(description = "补充内容", example = "你猜")
    private String remark;

    @Schema(description = "上报图片json;支持多张")
    private String images;

    @Schema(description = "项目id", example = "31530")
    private Long villageId;

    @Schema(description = "楼宇id", example = "30763")
    private Long buildId;

    @Schema(description = "租客id", example = "19911")
    private Long ownerId;

    @Schema(description = "当前工单所属岗位处理人json")
    private String postUids;

    @Schema(description = "工单来源")
    private String from;

    @Schema(description = "操作来源")
    private String source;

    @Schema(description = "工单来源应用标识")
    private String workApp;

    @Schema(description = "巡检任务id", example = "25462")
    private Long taskId;

    @Schema(description = "第三方发起工单的id（例如巡检整改记录id、资产维修单id）", example = "23551")
    private Long recordId;

    @Schema(description = "第三方发起工单的第二个id（例如资产维修单的资产id）")
    private Long recordId2;

    @Schema(description = "是否超时;0=否;1=是;")
    private String isTimeout;

    @Schema(description = "是否可抢单;0=否;1=是;")
    private String isChange;

    @Schema(description = "可抢单是否转未指派;0=否;1=是;")
    private String isAssign;

    @Schema(description = "超时起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date timeoutTime;

    @Schema(description = "可抢单起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date changeTime;

    @Schema(description = "可抢单结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date robTime;

    @Schema(description = "工单状态;1=未指派,2=已指派,3=处理中,4=已办结", example = "1")
    private String status;

    @Schema(description = "评价等级;1-5星")
    private Integer appraiseLevel;

    @Schema(description = "是否通知")
    private String hasNotice;

    @Schema(description = "是否支持退款;0=否;1=是;")
    private String hasRefund;

    @Schema(description = "是否能重新打开;0=否;1=是;")
    private String hasOpen;

    @Schema(description = "跟进人uid", example = "7297")
    private Long followupUid;

    @Schema(description = "抢单人uid", example = "14622")
    private Long robUid;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;


    private String startTime;
    private String endTime;
    private String departmentId;
    private String subcatName;
    private String villageIds;
}