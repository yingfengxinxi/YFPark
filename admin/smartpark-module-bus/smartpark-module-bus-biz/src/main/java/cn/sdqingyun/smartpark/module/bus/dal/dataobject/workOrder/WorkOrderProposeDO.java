package cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 机构工单数据 DO
 *
 * @author 管理员
 */
@TableName("workorder_propose")
@KeySequence("workorder_propose_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderProposeDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 应用标识
     */
    private String application;
    /**
     * 报修类型;1=室内报修;2=公共区域报修;
     */
    private String repairType;
    /**
     * 工单类型;1=普通工单;2=付费工单;
     */
    private String orderType;
    /**
     * 收费方式;1=免费服务;2=下单即收费;3=完成后收费;
     */
    private String paidType;
    /**
     * 工单订单状态;2=已支付;3=已退款;
     */
    private String paidStatus;
    /**
     * 费用支付方;1=租客承担;2=机构承担;
     */
    private String paidPayer;
    /**
     * 工单编号
     */
    private String number;
    /**
     * 工单大类id
     */
    private Long categoryId;
    /**
     * 工单子类id
     */
    private Long subcatId;
    /**
     * 上报人名称
     */
    private String name;
    /**
     * 上报人手机
     */
    private String phone;
    /**
     * 上报时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date appearTime;
    /**
     * 上门时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date visitTime;

    /**
     * 预约起始时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date reserveStartTime;
    /**
     * 预约结束时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date reserveEndTime;
    /**
     * 等待起始时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date waitTime;
    /**
     * 指定位置信息
     */
    private String position;
    /**
     * 描述标签json
     */
    private String labelJson;
    /**
     * 补充内容
     */
    private String remark;
    /**
     * 上报图片json;支持多张
     */
    private String images;
    /**
     * 项目id
     */
    private Long villageId;
    /**
     * 楼宇id
     */
    private Long buildId;


    /**
     * 房间id集合
     */
    private String roomIds;

    /**
     * 租客id
     */
    private Long ownerId;
    /**
     * 当前工单所属岗位处理人json
     */
    private String postUids;
    /**
     * 工单来源
     */
    @TableField(value = "`from`")
    private String from;
    /**
     * 操作来源
     */
    @TableField(value = "`source`")
    private String source;
    /**
     * 工单来源应用标识
     */
    private String workApp;
    /**
     * 巡检任务id
     */
    private Long taskId;
    /**
     * 第三方发起工单的id（例如巡检整改记录id、资产维修单id）
     */
    private Long recordId;
    /**
     * 第三方发起工单的第二个id（例如资产维修单的资产id）
     */
    private Long recordId2;
    /**
     * 是否超时;0=否;1=是;
     */
    private String isTimeout;
    /**
     * 是否可抢单;0=否;1=是;
     */
    private String isChange;
    /**
     * 可抢单是否转未指派;0=否;1=是;
     */
    private String isAssign;
    /**
     * 超时起始时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date timeoutTime;
    /**
     * 可抢单起始时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date changeTime;
    /**
     * 可抢单结束时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date robTime;
    /**
     * 工单状态;可抢单	9
     * 已回复	8
     * 已评价	7
     * 已取消	6
     * 已撤回	5
     * 已办结	4
     * 处理中	3
     * 已指派	2
     * 未指派	1
     */
    private String status;
    /**
     * 评价等级;1-5星
     */
    private Integer appraiseLevel;
    /**
     * 是否通知
     */
    private String hasNotice;
    /**
     * 是否支持退款;0=否;1=是;
     */
    private String hasRefund;
    /**
     * 是否能重新打开;0=否;1=是;
     */
    private String hasOpen;
    /**
     * 跟进人uid
     */
    private Long followupUid;
    /**
     * 抢单人uid
     */
    private Long robUid;

}