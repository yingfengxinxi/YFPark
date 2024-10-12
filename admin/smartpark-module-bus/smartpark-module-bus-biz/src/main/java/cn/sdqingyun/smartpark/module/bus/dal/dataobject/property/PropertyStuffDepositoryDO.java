package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 耗材档案信息 DO
 *
 * @author 智慧园区
 */
@TableName("property_stuff_depository")
@KeySequence("property_stuff_depository_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyStuffDepositoryDO extends TenantBaseDO {

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
     * 仓库编号
     */
    private String number;
    /**
     * 仓库名称
     */
    private String name;
    /**
     * 上级仓库id
     */
    private Long parentId;
    /**
     * 耗材仓库状态;1=启用,0=禁用
     */
    private Short status;
    /**
     * 上下层级关系
     */
    private String level;
    /**
     * 备注
     */
    private String remark;
    /**
     * 序号
     */
    private Integer sort;
    /**
     * 操作人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;

}