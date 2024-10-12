package cn.sdqingyun.smartpark.module.bus.dal.dataobject.category;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工单子类费用设置 DO
 *
 * @author 管理员
 */
@TableName("category_fee_set")
@KeySequence("category_fee_set_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryFeeSetDO extends TenantBaseDO {

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
     * 应用标识
     */
    private String application;
    /**
     * 工单大类id
     */
    private Long categoryId;
    /**
     * 工单子类id
     */
    private Long subcatId;

    /**
     * 启用时间段模式0=否1=是
     */
    private String isUse;
    /**
     * 费用设置
     */
    private String feeSet;
    /**
     * 耗材设置
     */
    private String stuffSet;
    /**
     * 服务项设置
     */
    private String serviceSet;


}