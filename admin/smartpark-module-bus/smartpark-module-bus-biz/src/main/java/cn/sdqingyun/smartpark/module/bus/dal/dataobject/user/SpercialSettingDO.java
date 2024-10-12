package cn.sdqingyun.smartpark.module.bus.dal.dataobject.user;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 机构用户自定义操作配置 DO
 *
 * @author 智慧园区
 */
@TableName("user_spercial_setting")
@KeySequence("user_spercial_setting_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpercialSettingDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 自定义类型
     */
    private String type;
    /**
     * 用户自定义配置内容json，JSON串({"buildId": ["1011", "1021", "1031"], "villageId": ["101", "102", "103"]})
     */
    private String content;

}