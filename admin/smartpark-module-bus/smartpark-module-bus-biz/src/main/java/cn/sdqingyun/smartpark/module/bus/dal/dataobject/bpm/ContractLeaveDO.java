package cn.sdqingyun.smartpark.module.bus.dal.dataobject.bpm;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 合同审批 DO
 *
 * @author 智慧园区
 */
@TableName("bpm_contract_leave")
@KeySequence("bpm_contract_leave_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractLeaveDO extends TenantBaseDO {

    /**
     * 合同审批单主键
     */
    @TableId
    private Long id;
    /**
     * 申请人的用户编号
     */
    private Long userId;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 审批结果 0:审批中，1：审批通过，2：审批不通过，3：审批驳回
     */
    private Integer status;
    /**
     * 合同id
     */
    private String contractId;
    /**
     * 合同编号
     */
    private String contractNumber;
    /**
     * 流程实例的编号
     */
    private String processInstanceId;

}