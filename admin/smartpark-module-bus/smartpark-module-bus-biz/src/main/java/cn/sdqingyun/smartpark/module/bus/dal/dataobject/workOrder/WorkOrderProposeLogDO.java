package cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 工单操作日志 DO
 *
 * @author 管理员
 */
@TableName("workorder_propose_log")
@KeySequence("workorder_propose_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderProposeLogDO extends TenantBaseDO {

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
     * 工单id
     */
    private Long workorderId;
    /**
     * 项目id
     */
    private Long villageId;
    /**
     * 楼宇id
     */
    private Long buildId;
    /**
     * 应用标识
     */
    private String application;
    /**
     * 来源标识
     */
    @TableField(value = "`from`")
    private String from;
    /**
     * 业务来源;1=租客端;2=员工端;
     */
    @TableField(value = "`source`")
    private String source;
    /**
     * 操作类型1=评价2=撤回3=关闭4=指派5=直接回复6=跟进7=结单8=拒绝9=转单10=抢单11=指定收费项13=重新打开14=补充信息15=退款16=自动取消17=缴费预缴费用18=缴费附加费用19=缴费尾款费用20=缴费综合费用21=创建工单
     */
    private String operateType;
    /**
     * 操作状态1=处理中2=已处理;
     */
    private String status;
    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date operateTime;
    /**
     * 处理人uid
     */
    private Long uid;
    /**
     * 处理人名称
     */
    private String name;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 备注内容
     */
    private String remark;
    /**
     * 图例;支持多张
     */
    private String images;
    /**
     * 备用内容json
     */
    private String otherContent;
    /**
     * 资产单据信息json
     */
    private String stuffData;
    /**
     * 评价等级;1-5星
     */
    private Integer appraiseLevel;

}