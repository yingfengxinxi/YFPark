package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目单元 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_unit")
@KeySequence("village_unit_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 单元编号
     */
    private Long unitNumber;
    /**
     * 单元名称
     */
    private String unitName;
    /**
     * 楼栋ID
     */
    private Long buildId;
    /**
     * 分区ID
     */
    private Long zoneId;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 排序，越大越前
     */
    private Integer sort;
    /**
     * 数据状态(1使用，0隐藏)
     */
    private Integer status;

}