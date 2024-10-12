package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目分区 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_zone")
@KeySequence("village_zone_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 分区编号
     */
    private Long zoneNumber;
    /**
     * 分区名称
     */
    private String zoneName;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 排序，越大越前
     */
    private Integer sort;
    /**
     * 数据状态，0隐藏，1展示
     */
    private Integer status;

}