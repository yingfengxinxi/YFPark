package cn.sdqingyun.smartpark.module.bus.dal.dataobject.category;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工单分类子类信息 DO
 *
 * @author 管理员
 */
@TableName("category_subcat")
@KeySequence("category_subcat_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorySubcatDO extends TenantBaseDO {

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
     * 子类名称
     */
    private String name;
    /**
     * 工单大类id
     */
    private Long categoryId;
    /**
     * 子类绑定的岗位信息json
     */
    private String stationJson;
    /**
     * 部门id
     */
    private Long departmentId;
    /**
     * 标签id集合
     */
    private String labelIds;
    /**
     * 排序值
     */
    private Integer sort;
    /**
     * 提交工单面向类型;1=所有人,2=租客,3=工作人员
     */
    private String submitType;
    /**
     * 启用状态;0=否;1=是
     */
    private String status;

}