package cn.sdqingyun.smartpark.module.bus.dal.dataobject.village;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目电话 DO
 *
 * @author 智慧园区管理员
 */
@TableName("village_phone")
@KeySequence("village_phone_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VillagePhoneDO extends TenantBaseDO {

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
     * 项目ID
     */
    private Long villageId;
    /**
     * 分类ID
     */
    private Long catId;
    /**
     * 号码名称
     */
    private String phoneName;
    /**
     * 号码
     */
    private String phone;
    /**
     * 排序值
     */
    private Integer sort;
    /**
     * 状态（1正常，0隐藏）
     */
    private Integer status;
    /**
     * 紧急电话（1是，0否）
     */
    private Integer urgent;

}