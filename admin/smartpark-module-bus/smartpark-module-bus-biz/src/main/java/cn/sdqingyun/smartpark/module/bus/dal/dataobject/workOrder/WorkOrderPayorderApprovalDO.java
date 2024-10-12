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
 * 工单付款单审批记录 DO
 *
 * @author 管理员
 */
@TableName("workorder_payorder_approval")
@KeySequence("workorder_payorder_approval_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderPayorderApprovalDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构id
     */
    private Long orgId;
    /**
     * 应用标识
     */
    private String application;
    /**
     * 单据编号
     */
    private String approveNumber;
    /**
     * 1=待审批(审批中);2=审批通过(已完结);3=审批不通过;4=已撤回(关闭申请);
     */
    private String status;
    /**
     * 审批业务类型
     */
    private String processType;
    /**
     * 审批内容
     */
    private String content;
    /**
     * 审批应用流程id
     */
    private Long approvalId;
    /**
     * 审批结束时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date approveOvertime;

}