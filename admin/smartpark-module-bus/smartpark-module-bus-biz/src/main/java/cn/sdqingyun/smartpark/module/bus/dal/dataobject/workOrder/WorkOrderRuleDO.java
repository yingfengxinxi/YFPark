package cn.sdqingyun.smartpark.module.bus.dal.dataobject.workOrder;

import lombok.*;

import java.util.*;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工单规则设置 DO
 *
 * @author 管理员
 */
@TableName("workorder_rule")
@KeySequence("workorder_rule_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderRuleDO extends TenantBaseDO {

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
     * 规则名称
     */
    private String name;
    /**
     * 抢单数上限;/个
     */
    private Integer snatchLimit;
    /**
     * 抢单前置时长;/分钟
     */
    private Integer preposeTime;
    /**
     * 抢单限制时长;单位/分钟
     */
    private Integer robTime;
    /**
     * 可退款时长;/分钟
     */
    private Integer refundTime;
    /**
     * 取消订单时长;/分钟
     */
    private Integer cancelTime;
    /**
     * 重新打开时长;/分钟
     */
    private Integer restartTime;
    /**
     * 绑定的楼宇信息json
     */
    private String buildBind;
    /**
     * 是否为默认配置
     */
    private String isDefault;
    //是否可以删除数据
    private String isDelete;

}