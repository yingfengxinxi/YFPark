package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产归还 DO
 *
 * @author 智慧园区
 */
@TableName("property_revert")
@KeySequence("property_revert_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRevertDO extends TenantBaseDO {

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
     * 项目id
     */
    private Long villageId;
    /**
     * 楼宇id
     */
    private Long buildId;
    /**
     * 房间id
     */
    private Long roomId;
    /**
     * 资产id集合json
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
     * 归还处理人
     */
    private Long revertUid;
    /**
     * 归还后使用部门
     */
    private Long departmentId;
    /**
     * 归还时间
     */
    private LocalDateTime revertTime;
    /**
     * 处理时间
     */
    private LocalDateTime operateTime;
    /**
     * 归还备注
     */
    private String remark;
    /**
     * 操作人uid
     */
    private Long cuserUid;
    /**
     * 修改人uid
     */
    private Long muserUid;

}