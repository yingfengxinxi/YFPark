package cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 巡检表单设置 DO
 *
 * @author 智慧园区
 */
@TableName("patrol_form")
@KeySequence("patrol_form_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatrolFormDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构Id
     */
    private Long orgId;
    /**
     * 应用标识
     */
    private String application;
    /**
     * 标题
     */
    private String title;
    /**
     * 表单内容
     */
    private String content;
    /**
     * 是否为默认配置0=否1=是
     */
    private String isDefault;

}