package cn.sdqingyun.smartpark.module.bus.dal.dataobject.energy;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 自定义关联分总 DO
 *
 * @author 管理员
 */
@TableName("energy_bind")
@KeySequence("energy_bind_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyBindDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 类型 1分表 2总表
     */
    private String type;
    /**
     * 设备表种类
     */
    private Long meterType;
    /**
     * 用途类型 1分表 2总表 3公摊表
     */
    private String purposeType;
    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 项目ID
     */
    private Long villageId;
    /**
     * 楼宇ID
     */
    private Long buildId;
    /**
     * 自身自定义表ID
     */
    private Long energyId;
    /**
     * 关联自定义表ID
     */
    private Long parentEnergyId;
    /**
     * 表名称
     */
    private String name;
    /**
     * 绑定表数据集合，json存储
     */
    private String binds;
    /**
     * 扩展字段 类型为1-分表-分摊比例,类型为2-总表-电表用量
     */
    private String extendContent;
    /**
     * 最近抄录时间
     */
    private String latelyMeterTime;
    /**
     * 状态，1启动，0审核中，4禁用
     */
    private String status;

}