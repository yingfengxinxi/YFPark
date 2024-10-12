package cn.sdqingyun.smartpark.module.bus.dal.dataobject.park;

import lombok.*;

import java.util.*;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 车辆操作日志 DO
 *
 * @author 智慧园区
 */
@TableName("park_cars_operator_log")
@KeySequence("park_cars_operator_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkCarsOperatorLogDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 0添加、1修改、2删除、3缴费，关联订单、4导入
     */
    private String type;
    /**
     * 车辆id
     */
    private Long carsId;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 修改前
     */
    @TableField(value = "`before`") // 使用反引号来避免关键字问题
    private String before;
    /**
     * 修改后
     */
    @TableField(value = "`after`")
    private String after;

    /**
     * 内容
     */
    @TableField(exist = false)
    private String content;

    @TableField(exist = false)
    private String creatorName;

}