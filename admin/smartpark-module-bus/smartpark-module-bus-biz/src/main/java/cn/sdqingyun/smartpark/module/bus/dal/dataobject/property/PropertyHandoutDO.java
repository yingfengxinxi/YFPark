package cn.sdqingyun.smartpark.module.bus.dal.dataobject.property;

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
 * 资产派发/退库 DO
 *
 * @author 智慧园区
 */
@TableName("property_handout")
@KeySequence("property_handout_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyHandoutDO extends TenantBaseDO {

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
     * 流程编号
     */
    private String processCode;
    /**
     * 数据类型;1=派发;2=退库
     */
    private Short type;
    /**
     * 单据编号
     */
    private String number;
    /**
     * 单据状态
     */
    private Integer status;
    /**
     * 所选部门id
     */
    private Long departmentId;
    /**
     * 领用人
     */
    private Long receiveUid;
    /**
     * 派发日期
     */
    private LocalDateTime handoutTime;
    /**
     * 退库日期
     */
    private LocalDateTime returnTime;
    /**
     * 处理人
     */
    private Long operateUid;
    /**
     * 处理时间
     */
    private LocalDateTime operateTime;
    /**
     * 处理备注
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