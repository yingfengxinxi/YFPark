package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产单据审批记录 DO
 *
 * @author 智慧园区
 */
@TableName("property_approve")
@KeySequence("property_approve_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyApproveDO extends TenantBaseDO {

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
     * 项目id
     */
    private Long villageId;
    /**
     * 楼宇id
     */
    private Long buildId;
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 审批单据编号
     */
    private String approveNumber;
    /**
     * 关联单据编号
     */
    private String relationNumber;
    /**
     * 关联单据类型
     */
    private String relationType;
    /**
     * 1=待审批(审批中);2=审批通过(已完结);3=审批不通过;4=已撤回(关闭申请);
     */
    private Integer status;
    /**
     * 审批业务类型
     */
    private String processType;
    /**
     * 审批内容
     */
    private String content;
    /**
     * 发起时间
     */
    private LocalDateTime launchTime;
    /**
     * 审批完结时间
     */
    private LocalDateTime approveOvertime;
    /**
     * 预计归还时间
     */
    private LocalDateTime expectRevertTime;
    /**
     * 原因备注
     */
    private String remark;
    /**
     * 申请部门id
     */
    private Long departmentId;
    /**
     * 交接后使用人uid
     */
    private Long handoverUid;
    /**
     * 交接后使用部门id
     */
    private Long handoverDepartmentId;
    /**
     * 审批应用流程id
     */
    private Long approvalId;
    /**
     * 提交人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;
    /**
     * 结束盘点之后的操作1已盘资产更新内容、2盘盈资产自动入库、3未盘资产自动盘亏
     */
    private String endInventoryAfter;

}