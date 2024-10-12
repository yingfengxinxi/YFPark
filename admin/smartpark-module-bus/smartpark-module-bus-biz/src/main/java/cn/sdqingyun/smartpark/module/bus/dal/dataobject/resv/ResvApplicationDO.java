package cn.sdqingyun.smartpark.module.bus.dal.dataobject.resv;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 预约应用 DO
 *
 * @author 智慧园区
 */
@TableName("resv_application")
@KeySequence("resv_application_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResvApplicationDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用简称
     */
    private String shortName;
    /**
     * 应用标志
     */
    private String sign;
    /**
     * 图标
     */
    private String icon;
    /**
     * 取消订单规则
     */
    private String cancelOrderRule;
    /**
     * 是否支持退款
     */
    private Integer refundSupported;
    /**
     * 退款规则
     */
    private String refundRule;

}