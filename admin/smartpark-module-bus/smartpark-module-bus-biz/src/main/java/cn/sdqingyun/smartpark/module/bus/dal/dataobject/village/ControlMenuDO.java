package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目租控管理菜单 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_control_menu")
@KeySequence("village_control_menu_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlMenuDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单图标（http开头视为图片）
     */
    private String icon;
    /**
     * 菜单选中图标（http开头视为图片）
     */
    private String activeIcon;
    /**
     * 按钮集合类型，暂定 village、build、room、villageUser
     */
    private String menuType;
    /**
     * 路由别名，http开头即认定为iframe
     */
    private String alias;
    /**
     * 菜单排序，越高越前
     */
    private Integer sort;
    /**
     * 微服务提供
     */
    private String micro;
    /**
     * 前端服务
     */
    private String app;

}