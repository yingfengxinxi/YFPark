package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目类型 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_type")
@KeySequence("village_type_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VillageTypeDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 类型名称
     */
    private String name;
    /**
     * 类型别名
     */
    private String alias;
    /**
     * 类型背景图片
     */
    private String bgImg;
    /**
     * 类型icon图片
     */
    private String iconImg;
    /**
     * 服务上报的菜单及应用
     */
    private String menu;
    /**
     * 受限制过滤的菜单及应用
     */
    private String filterMenu;

}