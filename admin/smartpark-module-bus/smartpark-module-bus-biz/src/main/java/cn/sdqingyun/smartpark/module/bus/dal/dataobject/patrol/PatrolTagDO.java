package cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 巡检标签模板 DO
 *
 * @author 智慧园区
 */
@TableName("patrol_tag")
@KeySequence("patrol_tag_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatrolTagDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构Id
     */
    private Long orgId;

    @Schema(description = "应用标识EQUIPMENT_INSPECTION=巡检;SECURITY_INSPECTION=安防", example = "292")
    private String application;
    /**
     * 模板id(数据字典)
     */
    private Long templateId;
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
    private String hasLogo;
    /**
     * 应用数据json
     */
    private String applyJson;

}