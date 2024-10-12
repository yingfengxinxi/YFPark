package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产标签模板 DO
 *
 * @author 智慧园区
 */
@TableName("property_tag")
@KeySequence("property_tag_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyTagDO extends TenantBaseDO {

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
     * 0=未应用;1=应用
     */
    private Short isApply;
    /**
     * 0=自定义模板;1=系统默认模板
     */
    private Short isDefault;
    /**
     * 模板链接
     */
    private String templatePath;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 字段上限数量
     */
    private Integer fieldLimit;
    /**
     * 有无logo;0=无;1=有
     */
    private Short hasLogo;
    /**
     * 应用数据json
     */
    private String applyJson;
    /**
     * 操作人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;

}