package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 住户的IC卡 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_user_ic_card")
@KeySequence("village_user_ic_card_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserIcCardDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * village_user用户表ID，可能为空
     */
    private Long userId;
    /**
     * 归属租客ID
     */
    private Long ownerId;
    /**
     * IC卡号（128位加密）
     */
    private String icCard;
    /**
     * 数据状态(1使用，0禁用)
     */
    private Integer status;

}