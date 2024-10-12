package cn.sdqingyun.smartpark.module.bus.dal.dataobject.patrol;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.sdqingyun.smartpark.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * 资产管理子应用管理员配置 DO
 *
 * @author 智慧园区
 */
@TableName("patrol_admin")
@KeySequence("patrol_admin_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatrolAdminDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 机构ID
     */
    private Long orgId;

    private String application;
    /**
     * 用户uid
     */
    private Long uid;
    /**
     * 部门id
     */
    private String departmentId;
    /**
     * 管理员级别,1=普通管理员;99=超级管理员
     */
    private String level;
    /**
     * 姓名
     */
    private String name;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 状态;0=禁用,1=启用
     */
    private String status;
    /**
     * 最后访问角色类型
     */
    private String lastRole;
    /**
     * 最后访问登录时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private Date lastTime;

}