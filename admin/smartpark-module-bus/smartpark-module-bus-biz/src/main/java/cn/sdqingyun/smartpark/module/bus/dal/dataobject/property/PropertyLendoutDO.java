package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import cn.sdqingyun.smartpark.framework.tenant.core.db.TenantBaseDO;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.sdqingyun.smartpark.framework.mybatis.core.dataobject.BaseDO;

/**
 * 资产借出 DO
 *
 * @author 智慧园区
 */
@TableName("property_lendout")
@KeySequence("property_lendout_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyLendoutDO extends TenantBaseDO {

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
        * 数据类型;1=借出;2=归还
        */
    private Short type;
    /**
     * 单据状态
     */
    private Integer status;
    /**
     * 借用人uid
     */
    private Long lendUid;
    /**
     * 借用部门id
     */
    private Long departmentId;
    /**
     * 借出时间
     */
    private LocalDateTime lendTime;
    /**
     * 预计归还时间
     */
    private LocalDateTime expectRevertTime;
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
     * 修改人uid
     */
    private Long muserUid;

}