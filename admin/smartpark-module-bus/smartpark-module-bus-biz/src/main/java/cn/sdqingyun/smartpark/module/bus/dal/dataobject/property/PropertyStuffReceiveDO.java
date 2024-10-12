package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 耗材业务领用 DO
 *
 * @author 智慧园区
 */
@TableName("property_stuff_receive")
@KeySequence("property_stuff_receive_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyStuffReceiveDO extends TenantBaseDO {

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
     * 申领仓库id
     */
    private Long depositoryId;
    /**
     * 申请部门id
     */
    private Long departmentId;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 申请总数量
     */
    private BigDecimal totalNum;
    /**
     * 关联单据类型
     */
    private String relationType;
    /**
     * 关联单据编号;派发单据,支持多个
     */
    private String relationNumber;
    /**
     * 已派发的总数量
     */
    private BigDecimal handoutNum;
    /**
     * 申请时间
     */
    private LocalDateTime applyTime;
    /**
     * 单据状态;1=进行中;2=已打回;3=已撤销;4=已完结
     */
    private Short status;
    /**
     * 申请原因
     */
    private String remark;
    /**
     * 申请人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;

}