package cn.sdqingyun.smartpark.module.bus.dal.dataobject.category;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工单分类配置 DO
 *
 * @author 管理员
 */
@TableName("category")
@KeySequence("category_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDO extends TenantBaseDO {

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
     * 应用标识
     */
    private String application;
    /**
     * 大类名称
     */
    private String name;
    /**
     * 运作模式;1=派单+抢单2=派单
     */
    private String type;
    /**
     * 完成时限
     */
    private Integer overHour;
    /**
     * 是否允许转单;1=开启,0=关闭
     */
    private String hasChange;
    /**
     * 排序值
     */
    private Integer sort;
    /**
     * 应用楼宇json
     */
    private String buildBind;
    /**
     * 工单子类id集合
     */
    private String subcatIds;

}