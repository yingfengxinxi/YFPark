package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 耗材业务处置 DO
 *
 * @author 智慧园区
 */
@TableName("property_stuff_handle")
@KeySequence("property_stuff_handle_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyStuffHandleDO extends TenantBaseDO {

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
     * 流程编号
     */
    private String processCode;
    /**
     * 单据编号
     */
    private String processNumber;
    /**
     * 发起部门id
     */
    private Long departmentId;
    /**
     * 发起部门名称
     */
    private String departmentName;
    /**
     * 处置仓库id
     */
    private Long depositoryId;
    /**
     * 发起时间
     */
    private LocalDateTime launchTime;
    /**
     * 合计金额
     */
    private BigDecimal totalPrice;
    /**
     * 单据状态;1=进行中;2=已打回;3=已撤销;4=已完结
     */
    private Integer status;
    /**
     * 处置原因
     */
    private String remark;
    /**
     * 发起人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;

}