package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

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
 * 资产维修 DO
 *
 * @author 智慧园区
 */
@TableName("property_repair")
@KeySequence("property_repair_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRepairDO extends TenantBaseDO {

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
     * 资产id
     */
    private String propertyIds;
    /**
     * 单据编号
     */
    private String number;
    /**
     * 流程编号
     */
    private String processCode;
    /**
     * 单据状态
     */
    private Integer status;
    /**
     * 报修人uid
     */
    private Long repairUid;
    /**
     * 报修部门id
     */
    private Long repairDepartmentId;
    /**
     * 报修时间
     */
    private LocalDateTime repairTime;
    /**
     * 报修原因
     */
    private String repairReason;
    /**
     * 处理人
     */
    private Long operateUid;
    /**
     * 预计维修金额
     */
    private String expectRepairPrice;
    /**
     * 处理时间
     */
    private LocalDateTime operateTime;
    /**
     * 维修内容
     */
    private String repairContent;
    /**
     * 工单信息
     */
    private String workorderInfo;
    /**
     * 备注
     */
    private String remark;
    /**
     * 操作人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;

}