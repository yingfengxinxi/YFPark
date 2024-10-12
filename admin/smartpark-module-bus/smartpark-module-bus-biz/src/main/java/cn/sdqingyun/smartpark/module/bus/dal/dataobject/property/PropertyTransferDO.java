package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产调拨 DO
 *
 * @author 智慧园区
 */
@TableName("property_transfer")
@KeySequence("property_transfer_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyTransferDO extends TenantBaseDO {

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
     * 资产id
     */
    private String propertyIds;
    /**
     * 单据编号
     */
    private String number;
    /**
     * 流程编号
     */
    private String processCode;
    /**
     * 单据状态
     */
    private Integer status;
    /**
     * 调出管理员uid
     */
    private Long outAdminUid;
    /**
     * 调入管理员uid
     */
    private Long inAdminUid;
    /**
     * 调入管理员姓名
     */
    private String inAdminUidName;
    /**
     * 调入位置id
     */
    private Long inLocationId;
    /**
     * 处理人
     */
    private Long operateUid;
    /**
     * 处理时间
     */
    private LocalDateTime operateTime;
    /**
     * 借出备注
     */
    private String remark;
    /**
     * 操作人uid
     */
    private Long cuserUid;
    /**
     * 资产清单ID
     */
    private Long muserUid;

}