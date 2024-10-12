package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 耗材业务耗材退还 DO
 *
 * @author 智慧园区
 */
@TableName("property_stuff_return")
@KeySequence("property_stuff_return_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyStuffReturnDO extends TenantBaseDO {

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
     * 关联单据类型
     */
    private String relationType;
    /**
     * 关联单据编号
     */
    private String relationNumber;
    /**
     * 申请部门id
     */
    private Long departmentId;
    /**
     * 申请部门
     */
    private String departmentName;
    /**
     * 归还仓库id
     */
    private Long depositoryId;
    /**
     * 单据状态;1=进行中;2=已打回;3=已撤销;4=已完结
     */
    private Integer status;
    /**
     * 归还原因
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