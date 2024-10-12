package cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 退租原因 DO
 *
 * @author 智慧园区
 */
@TableName("tag_termination")
@KeySequence("tag_termination_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagTerminationDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 标签描述
     */
    private String remarks;
    /**
     * 标签样式
     */
    private String color;
    /**
     * 状态，1启用，0禁用
     */
    private Integer status;

}