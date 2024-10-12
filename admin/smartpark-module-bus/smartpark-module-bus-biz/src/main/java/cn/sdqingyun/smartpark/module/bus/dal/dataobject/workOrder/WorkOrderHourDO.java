package cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder;

import lombok.*;

import java.util.*;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工单工时配置 DO
 *
 * @author 管理员
 */
@TableName("workorder_workhour")
@KeySequence("workorder_workhour_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderHourDO extends TenantBaseDO {

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
     * 工时名称
     */
    private String name;
    /**
     * 工时费用
     */
    private BigDecimal hourFee;
    /**
     * 工时计算规则;1=保留两位小数;2=去除小数部分;3=四舍五入去除小数;
     */
    private String computeRule;
    /**
     * 部门id
     */
    private Long departmentId;
    /**
     * 岗位信息json
     */
    private String stationId;
    /**
     * 岗位员工信息json
     */
    private String postUids;
    /**
     * 启用状态
     */
    private String status;
    /**
     * 是否为默认配置
     */
    private String isDefault;
    //是否可以删除
    private String isDelete;

}