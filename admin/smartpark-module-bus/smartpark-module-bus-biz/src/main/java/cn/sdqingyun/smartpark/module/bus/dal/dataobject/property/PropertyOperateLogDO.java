package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 资产操作日志 DO
 *
 * @author 智慧园区
 */
@TableName("property_operate_log")
@KeySequence("property_operate_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyOperateLogDO extends TenantBaseDO {

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
     * 操作人uid
     */
    private Long operateUid;
    /**
     * 资产id信息json,保存资产id，逗号隔开
     */
    private String propertyJson;
    /**
     * 流程编号
     */
    private String processCode;
    /**
     * 操作类型-业务编号
     */
    private String operateType;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 操作人
     */
    private String operateUser;
    /**
     * 操作时间
     */
    private LocalDateTime operateTime;
    /**
     * 操作内容
     */
    private String operateContent;
    /**
     * 其他
     */
    private String otherContent;
    /**
     * 操作权限
     */
    private String operate;

}