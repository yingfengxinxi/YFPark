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
 * 资产仓库信息 DO
 *
 * @author 智慧园区
 */
@TableName("property_depository")
@KeySequence("property_depository_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDepositoryDO extends TenantBaseDO {

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
     * 单据编号
     */
    private String number;
    /**
     * 单据状态
     */
    private Integer status;
    /**
     * 入库处理人
     */
    private Long operateUid;
    /**
     * 入库处理时间
     */
    private LocalDateTime operateTime;
    /**
     * 入库备注
     */
    private String remark;
    /**
     * 流程编号
     */
    private String processCode;
    /**
     * 操作人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;

}