package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目类型字典 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_type_dict")
@KeySequence("village_type_dict_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VillageTypeDictDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 类型别名
     */
    private String typeAlias;
    /**
     * 原字
     */
    private String words;
    /**
     * 中文
     */
    private String zhCn;
    /**
     * 繁体中文（中国香港）
     */
    private String zhHk;
    /**
     * 繁体中文（中国台湾）
     */
    private String zhTw;
    /**
     * 英文
     */
    private String en;
    /**
     * 日本语
     */
    private String ja;

}