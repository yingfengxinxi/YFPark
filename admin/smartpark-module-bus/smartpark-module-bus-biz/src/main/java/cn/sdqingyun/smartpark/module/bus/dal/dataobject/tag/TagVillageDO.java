package cn.sdqingyun.smartpark.module.bus.dal.dataobject.tag;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 项目标签 DO
 *
 * @author 智慧园区管理员
 */
@TableName("tag_village")
@KeySequence("tag_village_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagVillageDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 标签描述
     */
    private String descVillage;
    /**
     * 标签样式
     */
    private String color;
    /**
     * 状态，1启用，0禁用
     */
    private Integer status;

}