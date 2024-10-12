package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产耗材业务记录 DO
 *
 * @author 智慧园区
 */
@TableName("property_stuff_log")
@KeySequence("property_stuff_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyStuffLogDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构id
     */
    private Long orgId;
    /**
     * 耗材id
     */
    private Long stuffId;
    /**
     * 仓库id
     */
    private Long depositoryId;
    /**
     * 用户uid
     */
    private Long userId;
    /**
     * 业务
     */
    private String business;
    /**
     * 类型
     */
    private String type;
    /**
     * 使用数量
     */
    private BigDecimal num;
    /**
     * 附加信息
     */
    private String extraData;
    /**
     * 创建人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;

}